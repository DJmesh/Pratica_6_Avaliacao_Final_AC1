# PRÁTICA 6: Avaliação Final de AC1

**Integrantes**  
- **Nome:** Eduardo Augusto Prestes Júnior — **RA:** 252148  
- **Nome:** Eduardo Weber Maldaner — **RA:** 211948  
- **Nome:** Lucas Siqueira Gonçalves — **RA:** 212138  
- **Nome:** Tales Augusto Sartório Furlan — **RA:** 212170  

---

## Visão Geral do Repositório

Este repositório consolida a prática completa de **ATDD (Acceptance Test-Driven Development)** aplicada ao estudo de caso *“Gamificação de assinatura de cursos EAD”*.  
A solução foi construída em **Spring Boot 3 (Java 17)**, **Maven**, **JUnit 5**, **Cucumber (Gherkin)** e **JaCoCo**.

O repositório contém **três diretórios** que representam as fases do TDD:

```
subscription-suite-bdd-project-red/    # Fase RED  -> só testes (unitários/aceitação) falhando
subscription-suite-bdd-project-GREEN/  # Fase GREEN -> implementação mínima p/ passar
subscription-suite-bdd-project-BLUE/   # Fase BLUE -> refatoração + cobertura + relatórios (PROJETO FINAL)
```

> **Importante:** O **projeto completo** e pronto para avaliação é o **`subscription-suite-bdd-project-BLUE/`**.  
> É nele que estão os relatórios do **Cucumber** e **JaCoCo**, e onde a refatoração final foi aplicada.

---

## Stack e Ferramentas

- **Java:** 17  
- **Spring Boot:** 3.3.3 (starter básico)  
- **JUnit:** JUnit 5 (Jupiter)  
- **Cucumber:** 7.17.0 (cucumber-java e junit-platform-engine)  
- **Maven:** build, Surefire e perfil de relatório BDD  
- **JaCoCo:** medição de cobertura

---

## Estudo de Caso (Resumo)

Uma plataforma de cursos **EAD por assinatura** com regras de gamificação:

1. O aluno tem **créditos**. Para se matricular sem voucher, precisa ter **≥ 1** crédito (um crédito é consumido na matrícula).  
2. **Voucher** permite matrícula sem consumir crédito.  
3. Ao **concluir um curso**, o aluno ganha créditos segundo a **média** obtida:  
   - **≥ 9.0 → +5** créditos (bônus)  
   - **≥ 7.0 e < 9.0 → +3** créditos (aprovação)  
4. **Moedas (coins)** podem ser **convertidas** em créditos na razão **2:1**.  
5. **Top Contribuidor do mês** ganha **+1** crédito.  
6. Ao ultrapassar **12 cursos concluídos** (**> 12**), o plano do aluno muda **BASIC → PREMIUM**.

---

## User Stories

**US-01 (Eduardo Prestes)** — *Matrícula com voucher*  
> Eu, como aluno assinante, quero usar um **voucher** para me matricular **sem** consumir crédito, para aproveitar promoções.

**US-02 (Eduardo Weber)** — *Converter moedas em créditos*  
> Eu, como aluno engajado, quero converter **moedas** em **créditos** na proporção **2:1**, para liberar novas matrículas.

**US-03 (Tales)** — *Créditos por média*  
> Eu, como aluno, quero receber **créditos** ao **concluir um curso** de acordo com a **média**: **5** (≥9.0) ou **3** (≥7.0 e <9.0), para continuar estudando.

**US-04 (Lucas)** — *Promoção para PREMIUM*  
> Eu, como aluno BASIC, quero que meu plano mude para **PREMIUM** quando **ultrapassar 12 cursos** concluídos, para ter benefícios avançados.

---

## BDD (Cucumber / Gherkin)

### 1) `features/enrollment.feature`
```gherkin
Feature: Matrícula em curso usando créditos ou voucher

  Scenario: Matricular consumindo 1 crédito
    Given a BASIC student named "Ana" with 2 credits
    When the student enrolls in course "ML-101" without voucher
    Then the enrollment should be accepted with course code "ML-101"
    And the student should have 1 credit

  Scenario: Impedir matrícula sem créditos nem voucher
    Given a BASIC student named "Bruno" with 0 credits
    When the student tries to enroll in course "DS-201" without voucher
    Then the enrollment should be rejected with "INSUFFICIENT_CREDIT_OR_VOUCHER"

  Scenario: Voucher não consome crédito
    Given a BASIC student named "Aluno" with 2 credits
    When the student enrolls in course "ML-101" using voucher
    Then the enrollment should be accepted with course code "ML-101"
    And the student should still have 2 credits
```

### 2) `features/subscription_progress.feature`
```gherkin
Feature: Progressão de assinatura por desempenho e contribuição

  Scenario: Ganhar 5 créditos com média >= 9.0
    Given a BASIC student named "Tales" with 0 credits
    When the student finishes a course with average 9.1
    Then the student should have 1 completed course
    And the student should have 5 credits

  Scenario: Ganhar 3 créditos com 7.0 <= média < 9.0
    Given a BASIC student named "Tales" with 0 credits
    When the student finishes a course with average 7.3
    Then the student should have 3 credits

  Scenario: Converter moedas em créditos (2:1)
    Given a BASIC student named "Weber" with 4 coins and 0 credits
    When the student converts coins to credits
    Then the student should have 2 credits
    And the student should have 0 coins

  Scenario: Promoção para PREMIUM só quando > 12 cursos
    Given a BASIC student named "Lucas" with 11 completed courses
    When the student finishes a course with average 8.0   # passa a 12 (mantém BASIC)
    And the student finishes a course with average 8.0     # passa a 13 (vira PREMIUM)
    Then the student plan should be PREMIUM
```

---

## TDD e Estrutura do Código

### Fase RED
- Criação dos testes de unidade/aceitação com base nas user stories/BDD.
- Classes de teste por integrante:
  - `EduardoPrestesTest` → *voucherShouldNotConsumeCredit*  
  - `EduardoWeberTest` → *convertCoinsTwoForOne*
  - `TalesTest` → *highAverageShouldGrantFiveCredits*, *averageBetweenSevenAndNineGrantsThreeCredits*
  - `LucasTest` → *premiumOnlyAboveTwelveCourses*

### Fase GREEN
Implementação mínima para os testes passarem, com as classes de domínio/serviço:

- `Student` (agregado raiz: plano, créditos, moedas e cursos)
- `EnrollmentResult` (value object imutável, factories `accepted/rejected`)
- `EnrollmentService` (política de matrícula, `hasAccess`, `applySideEffects`)
- `ProgressService` (regras de créditos por média, conversão 2:1, bônus mensal, promoção)

### Fase BLUE (REFATORAÇÃO — projeto final)
- **Coesão e legibilidade:** extração de `creditsForAverage(double)` e `promoteIfEligible(Student)` em `ProgressService` e de `hasAccess(...)`/`applySideEffects(...)` em `EnrollmentService`.
- **Complexidade ciclomática:** reduzida por **guard clauses** (early return), eliminando `if` aninhados.
- **Imutabilidade e fábricas:** `EnrollmentResult` com construtor privado + fábricas `accepted`/`rejected`.
- **Nulidade segura:** `Objects.requireNonNull` em `Student#setPlan` e no construtor.
- **Nomes e constantes:** limiares e créditos como `static final`, expressando a regra de negócio.

> Resultado: todos os testes passam. A cobertura JaCoCo no BLUE é ≥ **95%** de linhas (serviços **100%**), refletindo a boa testabilidade após a refatoração.

---

## Como Rodar

### Linha de comando (Maven)
```bash
# Dentro de um dos diretórios (recomendado: subscription-suite-bdd-project-BLUE)
mvn clean verify

# Gerar também o relatório Cucumber bonito (perfil bdd-report)
mvn -P bdd-report clean verify
```
Relatórios gerados no BLUE:
- **Cucumber (padrão):** `target/cucumber/cucumber.html` e `target/cucumber/cucumber.json`
- **Cucumber (bonito):** `target/cucumber-report-html/` (perfil `bdd-report`)
- **JaCoCo:** `target/site/jacoco/index.html`

### Eclipse (Existing Maven Project)
1. *File → Import… → Existing Maven Projects* e selecione cada pasta.  
2. *Right click → Maven → Update Project*.  
3. Rodar: *Run As → Maven clean* e depois *Run As → Maven install*.  
4. Abrir os relatórios nos caminhos acima (dica: *Open With → Web Browser*).

---

## POM (Destaques)

- **Dependências:** `spring-boot-starter`, `spring-boot-starter-test` (scope `test`), `io.cucumber:cucumber-java` e `cucumber-junit-platform-engine` (scope `test`).  
- **Surefire:** configura `cucumber.plugin`, `glue` e `features` via `systemPropertyVariables`.  
- **JaCoCo:** `prepare-agent` + `report` na fase `verify`.  
- **Profile `bdd-report`:** usa `net.masterthought:maven-cucumber-reporting` para gerar o relatório HTML consolidado do Cucumber.

---

## Evidências e Relatórios

- **Relatórios do Cucumber (GREEN/BLUE):** mostram **100% de cenários passados** para as features de *matrícula* e *progressão*.
- **JaCoCo (BLUE):** cobertura alvo ≥ **95%** de linhas / **100%** dos métodos principais (serviços).

---

## Commits Sugeridos (Conventional Commits)

> Execute dentro do repositório (na raiz que contém as três pastas).

```bash
git init
git branch -M main
git remote add origin <URL-DO-SEU-REPO>

# 1) Fase RED
git add subscription-suite-bdd-project-red
git commit -m "feat(red): testes de unidade e aceitação (TDD passo 1) — cenários falhando"

# 2) Fase GREEN
git add subscription-suite-bdd-project-GREEN
git commit -m "feat(green): implementação mínima das regras (TDD passo 2) — testes passando"

# 3) Fase BLUE + documentação
git add subscription-suite-bdd-project-BLUE README.md
git commit -m "refactor(blue): refatoração final + cobertura JaCoCo + relatórios Cucumber e README"

git push -u origin main
```

---

## Onde está cada Feature/Teste (mapeamento por integrante)

- **Eduardo Prestes**  
  - Teste: `EduardoPrestesTest#voucherShouldNotConsumeCredit`  
  - Regra: *voucher não consome crédito* (`EnrollmentService#applySideEffects`)

- **Eduardo Weber**  
  - Teste: `EduardoWeberTest#convertCoinsTwoForOne`  
  - Regra: *conversão 2:1* (`ProgressService#convertCoinsToCredits`)

- **Tales**  
  - Testes: `TalesTest#highAverageShouldGrantFiveCredits` e `#averageBetweenSevenAndNineGrantsThreeCredits`  
  - Regra: *créditos por média* (`ProgressService#creditsForAverage` + `#finishCourse`)

- **Lucas**  
  - Teste: `LucasTest#premiumOnlyAboveTwelveCourses`  
  - Regra: *promoção BASIC → PREMIUM somente quando > 12* (`ProgressService#promoteIfEligible`)

---

## Conclusão — Atividade 8 (Análise da Feature)

As features implementadas **respondem a regras reais de cursos EAD gamificados** porque:
1. **Engajamento**: bonifica desempenho (média alta) e contribuição (top do mês).  
2. **Progressão**: conversão de moedas em créditos e promoção de plano por mérito.  
3. **Acesso**: matrícula depende de recursos (créditos) ou **voucher** (campanhas).  
4. **Medição objetiva**: regras expressas em código e cenários Gherkin → **ATDD completo** (User Story → BDD → TDD).

> **Feature que melhor representa a regra de EAD gamificada:** *Progressão por desempenho e contribuição*, implementada em `ProgressService` e exercitada pelos testes de **Tales** (créditos por média), **Weber** (conversão 2:1) e **Lucas** (promoção).

---

## Licença
Uso acadêmico/educacional.
