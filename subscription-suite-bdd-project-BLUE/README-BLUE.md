# TDD – Passo 3 (BLUE) – Refatoração

## O que foi refatorado e por quê
- **Constantes sem números mágicos** (`AVG_BONUS`, `AVG_PASS`, etc.) para explicitar regras.
- **Função pura `creditsForAverage(double)`** → facilita leitura, isolável e reduz complexidade de `finishCourse`.
- **Guards/Early returns** em `convertCoinsToCredits(...)` → remove ramos mortos; Cxty baixa (<=2).
- **Promoção extraída para `promoteIfEligible(...)`** com regra *exclusive threshold* (somente > 12), deixando a intenção clara.
- **Módulo de matrícula** com métodos privados `hasAccess(...)` e `applySideEffects(...)` → separa *política* de *efeito colateral*.
- **`Student` sem efeitos colaterais de promoção** → responsabilidade no serviço de domínio.

## Como rodar
```
mvn clean verify
```
- **JUnit/Surefire**: `target/surefire-reports`
- **JaCoCo**: `target/site/jacoco/index.html`

Todos os testes (4, um por integrante) permanecem **verdes**. A complexidade ciclomática por método fica **≤ 3**.
