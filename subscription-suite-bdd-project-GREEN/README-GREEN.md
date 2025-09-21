# TDD – Passo 2 (GREEN)
Todos os quatro casos por integrante passam:
- Eduardo Prestes – voucher não consome crédito.
- Eduardo Weber – conversão 2:1 (moedas→créditos).
- Lucas – promoção Premium somente acima de 12 cursos.
- Tales – média >=9 concede 5 créditos (7–<9 concede 3).

Rode:
```
mvn clean verify
```
Relatórios:
- Surefire (JUnit): `target/surefire-reports`
- JaCoCo: `target/site/jacoco/index.html`
