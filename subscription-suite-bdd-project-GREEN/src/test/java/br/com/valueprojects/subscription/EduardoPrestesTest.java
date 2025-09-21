package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EduardoPrestesTest {
  @Test
  void voucherShouldNotConsumeCredit() {
    Student s = new Student("Aluno");
    s.setCredits(2);
    EnrollmentService svc = new EnrollmentService();
    EnrollmentResult r = svc.enroll(s, "ML-101", true);
    assertTrue(r.isAccepted());
    assertEquals(2, s.getCredits(), "Com voucher, crédito NÃO deve reduzir");
  }
}
