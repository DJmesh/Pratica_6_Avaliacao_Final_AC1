package br.com.valueprojects.subscription;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EduardoPrestesTest {

  @Test
  void voucherNaoDeveConsumirCredito() {
    Student s = new Student("Aluno");
    s.setCredits(2);
    EnrollmentService svc = new EnrollmentService();

    EnrollmentResult r = svc.enroll(s, "ML-101", true);

    assertTrue(r.isAccepted());
    assertEquals("ML-101", r.getCourseCode());
    assertEquals(2, s.getCredits(), "Com voucher, o crédito NÃO deve reduzir");
  }

  @Test
  void deveRejeitarSemCreditoENemVoucher() {
    Student s = new Student("Aluno");
    s.setCredits(0);
    EnrollmentService svc = new EnrollmentService();

    EnrollmentResult r = svc.enroll(s, "IA-200", false);

    assertFalse(r.isAccepted());
    assertEquals(EnrollmentService.ERR_INSUFFICIENT, r.getReason());
    assertNull(r.getCourseCode());
  }

  @Test
  void consumirCreditoComSaldoZeroNaoAltera() {
    Student s = new Student("Aluno");
    s.setCredits(0);

    s.consumeCredit();

    assertEquals(0, s.getCredits());
  }

  @Test
  void fabricaRejectedDeveExporMotivo() {
    EnrollmentResult r = EnrollmentResult.rejected("MOTIVO_X");

    assertFalse(r.isAccepted());
    assertEquals("MOTIVO_X", r.getReason());
    assertNull(r.getCourseCode());
  }

  @Test
  void topContributorGanhaUmCredito() {
    Student s = new Student("Aluno");
    ProgressService p = new ProgressService();

    p.grantMonthlyTopContributorCredit(s);

    assertEquals(1, s.getCredits());
  }
}
