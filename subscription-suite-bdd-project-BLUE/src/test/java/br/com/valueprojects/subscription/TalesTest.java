package br.com.valueprojects.subscription;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TalesTest {

  @Test
  void mediaAltaDeveConcederCincoCreditos() {
    Student s = new Student("Tales");
    ProgressService svc = new ProgressService();

    svc.finishCourse(s, 9.1);

    assertEquals(1, s.getCompletedCourses());
    assertEquals(5, s.getCredits(), "Média >= 9.0 deve conceder 5 créditos");
  }

  @Test
  void mediaEntreSeteENoveConcedeTresCreditos() {
    Student s = new Student("Tales");
    ProgressService svc = new ProgressService();

    svc.finishCourse(s, 7.3);

    assertEquals(3, s.getCredits(), "Média >= 7.0 e < 9.0 deve conceder 3 créditos");
  }

  @Test
  void matriculaComCreditoSemVoucherDeveConsumirUmCredito() {
    Student s = new Student("Tales");
    s.setCredits(1);
    EnrollmentService svc = new EnrollmentService();

    EnrollmentResult r = svc.enroll(s, "DEV-100", false);

    assertTrue(r.isAccepted());
    assertEquals(0, s.getCredits(), "Sem voucher deve consumir 1 crédito");
  }

  @Test
  void gettersBasicosENovaMoeda() {
    Student s = new Student("Tales");

    assertEquals("Tales", s.getName());
    assertEquals(0, s.getCoins());

    s.addCoin();

    assertEquals(1, s.getCoins());
  }
}
