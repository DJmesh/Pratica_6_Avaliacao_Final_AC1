
# subscription-suite-bdd-project-ATDD

Este projeto contém os **cenários de aceitação** em **Gherkin** e os **steps do Cucumber** que exercitam o domínio da assinatura.

## Executando

```bash
mvn clean verify
mvn -P bdd-report clean verify   # relatório bonito (opcional)
```

- **Relatório Cucumber:** `target/cucumber/cucumber.html`  
- **Relatório BDD (profile):** `target/bdd-report/cucumber-html-reports/overview-features.html`

## Estrutura

```
src
├─ test
│  ├─ java/br/com/valueprojects/subscription/bdd
│  │  ├─ StudentSteps.java       # Givens de aluno
│  │  ├─ EnrollmentSteps.java    # When/Then de matrícula
│  │  ├─ ProgressSteps.java      # When de progresso
│  │  ├─ Steps.java              # Thens genéricos (créditos, moedas, plano, cursos)
│  │  ├─ StepContext.java        # Contexto compartilhado
│  │  └─ RunCucumberTest.java    # Runner JUnit Platform
│  └─ resources/features
│     ├─ enrollment.feature
│     └─ subscription_progress.feature
```

## Dicas

- Evite duplicidade de steps.  
- As classes recebem `StepContext` por construtor para compartilhar estado.  
- Os features estão em **pt-BR** e usam `{int}` e `{double}` conforme necessário.

## POM (trechos)

Verifique no POM os plugins **Surefire**, **JaCoCo** e o **profile `bdd-report`** (maven-cucumber-reporting).
