package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EduardoWeberTest {
  @Test
  void convertCoinsTwoForOne() {
    Student s = new Student("Weber");
    s.setCoins(4);
    s.setCredits(0);
    ProgressService svc = new ProgressService();
    svc.convertCoinsToCredits(s, 4);
    assertEquals(2, s.getCredits(), "4 moedas devem virar 2 créditos");
    assertEquals(0, s.getCoins());
  }
}
