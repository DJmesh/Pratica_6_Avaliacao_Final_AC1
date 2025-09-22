# PRÁTICA 6 — Avaliação Final (AC1)

**Equipe**
- Eduardo Augusto Prestes Júnior — RA: 252148  
- Eduardo Weber Maldaner — RA: 211948  
- Lucas Siqueira Gonçalves — RA: 212138  
- Tales Augusto Sartório Furlan — RA: 212170

---

## Visão geral

Este repositório demonstra a evolução guiada por testes (**TDD**) e por testes de aceitação (**ATDD/BDD**) de uma suíte de assinatura educacional.  
O domínio cobre:

- **Matrícula em curso** consumindo **1 crédito** ou usando **voucher**.  
- **Gamificação**: moedas → créditos (**2:1**).  
- **Créditos por desempenho** ao concluir cursos (≥9.0 → +5; 7.0–<9.0 → +3; <7.0 → +0).  
- **Promoção de plano** para **PREMIUM** quando **cursos concluídos > 12**.

A evolução está organizada em quatro projetos-irmãos dentro do mesmo repo:

```
subscription-suite-bdd-project-red/    # TDD RED   — testes falhando
subscription-suite-bdd-project-GREEN/  # TDD GREEN — implementação mínima para passar
subscription-suite-bdd-project-BLUE/   # TDD BLUE  — refatoração + cobertura
subscription-suite-bdd-project-ATDD/   # ATDD/BDD  — Cucumber + Gherkin
```

> **Resumo:** BLUE é o final da Parte 1 (TDD). ATDD é a Parte 2 (Cucumber).

---

## User Stories (resumidas)

1. **Matrícula usando crédito ou voucher**  
   - Como aluno, quero me matricular **usando 1 crédito** ou **um voucher** para acessar o curso.  
   - Critérios: sem crédito **e** sem voucher → **erro** “INSUFFICIENT_CREDIT_OR_VOUCHER”; com voucher a matrícula **não consome** crédito.

2. **Conversão de moedas em créditos (gamificação)**  
   - Como aluno, quero converter minhas **moedas em créditos** na proporção **2:1** para poder me matricular em cursos.

3. **Créditos por desempenho acadêmico**  
   - Como aluno, desejo **ganhar créditos** ao finalizar um curso conforme a **média**:  
     - **≥ 9.0** → **+5 créditos**  
     - **≥ 7.0 e < 9.0** → **+3 créditos**  
     - **< 7.0** → **+0 crédito**

4. **Reconhecimento mensal de engajamento**  
   - Como top contributor do mês, quero receber **+1 crédito** de recompensa.

5. **Promoção de plano por progresso**  
   - Como aluno, desejo ser promovido para **PREMIUM** quando **meus cursos concluídos forem maiores que 12**.

---

## Arquitetura do domínio

**Entidades**

- `Plan` — enum (`BASIC`, `PREMIUM`).  
- `Student` — estado: `name`, `plan`, `credits`, `coins`, `completedCourses`.  
  - operações: `addCredits`, `consumeCredit`, `addCompletedCourses`, etc.

**Serviços**

- `EnrollmentService` — decide se aceita/rejeita matrícula:
  - Se **voucher = true** → matrícula **aceita** sem consumir crédito.  
  - Se **sem voucher** e **credits < 1** → **rejeita** com razão `INSUFFICIENT_CREDIT_OR_VOUCHER`.  
  - Caso contrário, **consome 1 crédito** e **aceita**.
- `ProgressService` — regras de progresso/gamificação:
  - `finishCourse(student, count, average)`:
    - Atualiza cursos concluídos (+`count`).
    - Concede créditos: ≥9.0 → +5; 7.0–<9.0 → +3; <7.0 → +0.
    - Promove a **PREMIUM** somente se `completedCourses > 12`.
  - `convertCoins(student, coinsToConvert)` — taxa **2:1**.

**Resultados/VO**

- `EnrollmentService.Result` — `accepted`, `code` (curso), `reason` (motivo da rejeição).

---

## Estrutura do projeto ATDD (Cucumber)

```
subscription-suite-bdd-project-ATDD/
 ├─ src/main/java/br/com/valueprojects/subscription/   # domínio (Plan, Student, Services)
 └─ src/test/
     ├─ java/br/com/valueprojects/subscription/bdd/    # step definitions + runner
     │   ├─ StudentSteps.java      # todos os Given
     │   ├─ EnrollmentSteps.java   # When/Then de matrícula
     │   ├─ ProgressSteps.java     # When de progresso (concluir curso)
     │   ├─ Steps.java             # Then genéricos (asserts de estado)
     │   ├─ StepContext.java       # contexto compartilhado entre steps
     │   └─ RunCucumberTest.java   # runner JUnit Platform
     └─ resources/features/
         ├─ enrollment.feature
         ├─ subscription_progress.feature
         └─ bonus_team.feature     # cenários por integrante (bônus)
```

**Contexto de steps**: em vez de usar `static` globais, o objeto `StepContext` mantém o `Student`, os serviços e o último resultado de matrícula. Isso simplifica a troca de dados entre os passos (Given/When/Then) e evita interferências entre cenários.

---

## Scripts Gherkin (arquivos .feature)

### `enrollment.feature` (exemplo abreviado)
```gherkin
# language: pt
Funcionalidade: Matrícula em curso usando créditos ou voucher

Cenário: Matricular consumindo 1 crédito
  Dado um aluno BASIC chamado "Ana" com 0 cursos concluídos e 2 créditos
  Quando o aluno se matricula no curso "ML-101" sem voucher
  Então a matrícula deve ser aceita com o código "ML-101"
  E o aluno deve possuir 1 crédito

Cenário: Impedir matrícula sem créditos nem voucher
  Dado um aluno BASIC chamado "Bruno" com 0 cursos concluídos e 0 créditos
  Quando o aluno tenta se matricular no curso "DS-201" sem voucher
  Então a matrícula deve ser rejeitada com o motivo "INSUFFICIENT_CREDIT_OR_VOUCHER"

Cenário: Voucher não consome crédito
  Dado um aluno BASIC chamado "Aluno" com 0 cursos concluídos e 2 créditos
  Quando o aluno se matricula no curso "ML-101" usando voucher
  Então a matrícula deve ser aceita com o código "ML-101"
  E o aluno deve possuir 2 créditos
```

### `subscription_progress.feature` (exemplo abreviado)
```gherkin
# language: pt
Funcionalidade: Progressão de assinatura por desempenho e contribuição

Cenário: Ganhar 5 créditos com média >= 9.0
  Dado um aluno BASIC chamado "Tales" com 0 cursos concluídos e 0 créditos
  Quando o aluno conclui 1 curso(s) com média 9.1
  Então o aluno deve possuir 1 curso concluído
  E o aluno deve possuir 5 créditos
  E o plano deve ser BASIC

Cenário: Ganhar 3 créditos com 7.0 <= média < 9.0
  Dado um aluno BASIC chamado "Tales" com 0 cursos concluídos e 0 créditos
  Quando o aluno conclui 1 curso(s) com média 7.3
  Então o aluno deve possuir 3 créditos

Cenário: Converter moedas em créditos (2:1)
  Dado um aluno BASIC chamado "Weber" com 0 cursos concluídos, 0 créditos e 4 moedas
  Quando o aluno converte 4 moedas em créditos
  Então o aluno deve possuir 2 créditos
  E o aluno deve possuir 0 moedas

Cenário: Promoção para PREMIUM só quando > 12 cursos
  Dado um aluno BASIC chamado "Lucas" com 12 cursos concluídos e 0 créditos
  Quando o aluno conclui 1 curso(s) com média 8.0
  Então o plano deve ser PREMIUM
```

### `bonus_team.feature` (exemplo abreviado)
Cada integrante tem um cenário que valida uma regra-chave do domínio (voucher, conversão, promoção, desempenho).

---

## Runner JUnit 5 (Cucumber)

`RunCucumberTest.java` usa a Engine do Cucumber para JUnit Platform.  
Execute como **JUnit Test** ou via Maven (Surefire).

---

## POM do ATDD — dependências principais

```xml
<properties>
  <maven.compiler.release>17</maven.compiler.release>
  <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  <junit.jupiter.version>5.10.2</junit.jupiter.version>
  <junit.platform.version>1.10.2</junit.platform.version>
  <cucumber.version>7.17.0</cucumber.version>
</properties>

<dependencies>
  <!-- JUnit 5 -->
  <dependency>
    <groupId>org.junit.jupiter</groupId>
    <artifactId>junit-jupiter</artifactId>
    <version>${junit.jupiter.version}</version>
    <scope>test</scope>
  </dependency>

  <!-- Cucumber -->
  <dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-java</artifactId>
    <version>${cucumber.version}</version>
    <scope>test</scope>
  </dependency>
  <dependency>
    <groupId>io.cucumber</groupId>
    <artifactId>cucumber-junit-platform-engine</artifactId>
    <version>${cucumber.version}</version>
    <scope>test</scope>
  </dependency>

  <!-- AssertJ opcional -->
  <dependency>
    <groupId>org.assertj</groupId>
    <artifactId>assertj-core</artifactId>
    <version>3.26.3</version>
    <scope>test</scope>
  </dependency>
</dependencies>
```

### Surefire + plugins (Cucumber e JaCoCo)

```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-surefire-plugin</artifactId>
      <version>3.2.5</version>
      <configuration>
        <useSystemClassLoader>true</useSystemClassLoader>
        <properties>
          <configurationParameters>
            cucumber.plugin=pretty, html:target/cucumber/cucumber.html, json:target/cucumber/cucumber.json
            cucumber.glue=br.com.valueprojects.subscription.bdd
            cucumber.junit-platform.naming-strategy=long
          </configurationParameters>
        </properties>
      </configuration>
    </plugin>

    <plugin>
      <groupId>org.jacoco</groupId>
      <artifactId>jacoco-maven-plugin</artifactId>
      <version>0.8.12</version>
      <executions>
        <execution>
          <goals>
            <goal>prepare-agent</goal>
          </goals>
        </execution>
        <execution>
          <id>report</id>
          <phase>verify</phase>
          <goals>
            <goal>report</goal>
          </goals>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
```

### Profile opcional para relatório “bonito” do Cucumber

```xml
<profiles>
  <profile>
    <id>bdd-report</id>
    <build>
      <plugins>
        <plugin>
          <groupId>net.masterthought</groupId>
          <artifactId>maven-cucumber-reporting</artifactId>
          <version>5.8.0</version>
          <executions>
            <execution>
              <id>generate-cucumber-report</id>
              <phase>verify</phase>
              <goals><goal>generate</goal></goals>
              <configuration>
                <jsonFiles>
                  <param>${project.build.directory}/cucumber/cucumber.json</param>
                </jsonFiles>
                <outputDirectory>${project.build.directory}/bdd-report</outputDirectory>
                <projectName>subscription-suite-bdd-project-ATDD</projectName>
                <buildNumber>${project.version}</buildNumber>
              </configuration>
            </execution>
          </executions>
        </plugin>
      </plugins>
    </build>
  </profile>
</profiles>
```

---

## Como executar

### Maven (linha de comando)
```bash
# dentro do projeto ATDD
mvn clean verify

# gerar o relatório bonito de BDD (precisa do profile no POM)
mvn -P bdd-report clean verify
```

### Eclipse/IntelliJ
1. **Import** → *Existing Maven Project*.  
2. **Maven → Update Project**.  
3. Executar `RunCucumberTest` como **JUnit Test**, ou rodar `mvn clean verify`.

### Onde abrir os relatórios
- **Cucumber HTML**: `target/cucumber/cucumber.html`  
- **Cucumber “bonito”**: `target/bdd-report/cucumber-html-reports/overview-features.html`  
- **JaCoCo**: `target/site/jacoco/index.html`

---

## Dicas de escrita de BDD
- Use **português** nas features quando a turma/professora exigir (`# language: pt`).  
- Centralize todos os **Given** em uma classe (`StudentSteps`) para evitar duplicação.  
- Deixe **asserts** genéricos (`Then`) numa classe (`Steps`) — reutilizável entre cenários.  
- Evite **estado estático**; prefira um **contexto** injetado ou criado por cenário.  
- Nome de cenário deve **explicar a regra** em linguagem de negócio.  
- Se houver *Undefined*, confira **glue** (package) e **frases** (acentos/ortografia importam).  

---

## Troubleshooting rápido

**1) “Undefined step” amarelo no HTML**  
- Verifique se a frase no `.feature` é exatamente igual à anotação (`@Dado`, `@Quando`, `@Entao`).  
- Confira se o package `br.com.valueprojects.subscription.bdd` está na opção `cucumber.glue`.  
- Salve tudo e rode `Maven → Update Project` antes de executar.

**2) `DuplicateStepDefinitionException`**  
- Ocorre quando duas classes implementam a **mesma frase**.  
- Solução: consolidar `Given`/`Then` em classes únicas (`StudentSteps`, `Steps`), e separar os *When* por assunto.

**3) `NullPointerException` no serviço**  
- Geralmente `ctx.student` não foi criado no `Given`.  
- Garanta um `Given` que **instancia** `Student` e inicializa os campos usados pelo cenário.

**4) Relatório “bonito” não aparece**  
- Verifique se rodou com **profile**: `mvn -P bdd-report clean verify`.  
- Confirme o caminho do JSON em `<jsonFiles>`.

---

## Fluxo TDD (resumo) — RED → GREEN → BLUE

1. **RED** (projeto *red*)  
   - Escreva o teste 1º; verifique a falha e a mensagem.  
2. **GREEN** (projeto *GREEN*)  
   - Implemente o **mínimo** para o teste passar.  
3. **BLUE** (projeto *BLUE*)  
   - Refatore **nomes**, **métodos puros**, **early-returns** e corte de duplicações.  
   - Geração de cobertura **JaCoCo** e, se desejar, relatório de BDD.

> ATDD (projeto *ATDD*) conecta as regras de negócio a cenários executáveis (Cucumber).

---

## Comandos Git úteis (com exemplos do seu repo)

```bash
# adicionar o projeto ATDD e os READMEs
git add subscription-suite-bdd-project-ATDD         subscription-suite-bdd-project-ATDD/README.md         subscription-suite-bdd-project-BLUE/README.md         subscription-suite-bdd-project-GREEN/README.md         subscription-suite-bdd-project-red/README.md         README.md

# criar commit
git commit -m "docs: README raiz consolidado + READMEs dos projetos (RED/GREEN/BLUE/ATDD) e instruções de execução/relatórios"

# criar branch opcional e enviar
git checkout -b chore/docs-atdd-readmes
git push -u origin chore/docs-atdd-readmes
```

---

## Rubrica de correção

- **Estrutura do repo** com os 4 projetos (✔).  
- **Execução do Cucumber** com HTML gerado (✔).  
- **Cenários passando** sem *undefined* (✔).  
- **Perfil `bdd-report`** opcional funcionando (✔).  
- **README** com **arquitetura, execução, troubleshooting e prints** (✔).

---

## Licença

Projeto acadêmico para fins de avaliação (uso livre para estudo).
