package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Integrante: Eduardo Prestes
 *  Hipótese de negócio: usar VOUCHER não deve consumir créditos.
 *  Situação atual: EnrollmentService consome crédito mesmo com voucher → TESTE DEVE FALHAR (RED).
 */
class EduardoPrestesTest_RED {
  @Test
  void voucherShouldNotConsumeCredit_RED() {
    // Arrange
    Student s = new Student("Aluno");
    s.setCredits(2);
    EnrollmentService svc = new EnrollmentService();
    // Act
    EnrollmentResult r = svc.enroll(s, "ML-101", true); // usando voucher
    // Assert (esperado pela hipótese de negócio)
    assertTrue(r.isAccepted());
    assertEquals(2, s.getCredits(), "Com voucher, crédito NÃO deveria reduzir"); // <-- falha
  }
}
