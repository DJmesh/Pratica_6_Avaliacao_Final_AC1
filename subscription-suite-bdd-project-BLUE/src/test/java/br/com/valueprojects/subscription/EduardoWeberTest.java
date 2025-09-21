package br.com.valueprojects.subscription;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EduardoWeberTest {

  @Test
  void converterQuatroMoedasGeraDoisCreditos() {
    Student s = new Student("Weber");
    s.setCoins(4);
    s.setCredits(0);
    ProgressService svc = new ProgressService();

    svc.convertCoinsToCredits(s, 4);

    assertEquals(2, s.getCredits(), "4 moedas devem virar 2 créditos");
    assertEquals(0, s.getCoins());
  }

  @Test
  void converterUmaMoedaNaoMudaNadaDivisaoInteira() {
    Student s = new Student("Weber");
    s.setCoins(1);
    s.setCredits(0);
    ProgressService svc = new ProgressService();

    svc.convertCoinsToCredits(s, 1);

    assertEquals(0, s.getCredits());
    assertEquals(1, s.getCoins());
  }

  @Test
  void converterComMoedasInsuficientesFazNoOp() {
    Student s = new Student("Weber");
    s.setCoins(1);
    ProgressService svc = new ProgressService();

    svc.convertCoinsToCredits(s, 2); // coins < request

    assertEquals(0, s.getCredits());
    assertEquals(1, s.getCoins());
  }

  @Test
  void converterZeroMoedasFazNoOp() {
    Student s = new Student("Weber");
    s.setCoins(0);
    ProgressService svc = new ProgressService();

    svc.convertCoinsToCredits(s, 0);

    assertEquals(0, s.getCredits());
    assertEquals(0, s.getCoins());
  }

  @Test
  void concluirCursoComMediaAbaixoDeSeteNaoConcedeCredito() {
    Student s = new Student("Weber");
    ProgressService svc = new ProgressService();

    svc.finishCourse(s, 6.5);

    assertEquals(1, s.getCompletedCourses());
    assertEquals(0, s.getCredits());
  }
}
