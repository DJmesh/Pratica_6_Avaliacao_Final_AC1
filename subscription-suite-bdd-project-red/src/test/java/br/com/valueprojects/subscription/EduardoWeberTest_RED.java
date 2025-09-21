package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Integrante: Eduardo Weber
 *  Hipótese: conversão de moedas para créditos é 2:1 (duas moedas viram um crédito).
 *  Implementação atual: 1:1 → TESTE DEVE FALHAR (RED).
 */
class EduardoWeberTest_RED {
  @Test
  void convertCoinsTwoForOne_RED() {
    // Arrange
    Student s = new Student("Weber");
    s.setCoins(4);
    s.setCredits(0);
    ProgressService svc = new ProgressService();
    // Act
    svc.convertCoinsToCredits(s, 4);
    // Assert (esperado pela hipótese 2:1)
    assertEquals(2, s.getCredits(), "4 moedas deveriam virar 2 créditos"); // <-- falha (virará 4)
    assertEquals(0, s.getCoins());
  }
}
