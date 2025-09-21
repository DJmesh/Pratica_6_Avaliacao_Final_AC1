# TDD – Passo 1 (RED) com 4 casos (um por integrante)

Rode:
```bash
mvn clean test
```
Veja os **FAILURES** em `target/surefire-reports` (ou JUnit no Eclipse).

## Mapeamento dos casos (todos propositalmente falhando)

1. **Eduardo Prestes** – `voucherShouldNotConsumeCredit_RED`  
   *Hipótese:* usar voucher **não** consome crédito.  
   *Estado atual (implementação):* crédito é consumido mesmo com voucher → **falha**.

2. **Eduardo Weber** – `convertCoinsTwoForOne_RED`  
   *Hipótese:* conversão moedas→créditos é **2:1**.  
   *Estado atual:* conversão é **1:1** → **falha**.

3. **Lucas** – `premiumOnlyAboveTwelveCourses_RED`  
   *Hipótese:* promoção para PREMIUM só **acima** de 12 (>=13).  
   *Estado atual:* promoção ocorre **com 12** → **falha**.

4. **Tales** – `highAverageShouldGrantFiveCredits_RED`  
   *Hipótese:* média >= 9.0 dá **5 créditos** (bônus).  
   *Estado atual:* dá **3 créditos** → **falha**.

> Estes REDs são base para a etapa GREEN (ajustar implementação) e BLUE (refatorar) mantendo cobertura alta (Jacoco).
