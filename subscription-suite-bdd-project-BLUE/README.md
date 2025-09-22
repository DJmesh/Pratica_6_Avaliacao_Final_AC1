
# subscription-suite-bdd-project-BLUE

Projeto **final da Parte 1** (TDD): código **refatorado**, com **boa cobertura** e **relatórios**.

## Foco da Fase BLUE

- Remoção de duplicações.  
- Extração de funções puras para regras (ex.: `creditsForAverage`).  
- Promoção PREMIUM somente quando `completedCourses > 12`.  
- Relatórios de cobertura com **JaCoCo**.

## Executando

```bash
mvn clean verify
```

- **JaCoCo:** `target/site/jacoco/index.html`

## Estrutura típica

```
src/main/java/br/com/valueprojects/subscription
├─ EnrollmentService.java
├─ ProgressService.java
├─ Student.java
└─ Plan.java

src/test/java/br/com/valueprojects/subscription
├─ TalesTest.java
├─ LucasTest.java
├─ EduardoWeberTest.java
└─ EduardoPrestesTest.java
```

## Notas

- Mantenha os testes rápidos e determinísticos.  
- Mensagens de assert devem explicar a **regra** verificada.
s