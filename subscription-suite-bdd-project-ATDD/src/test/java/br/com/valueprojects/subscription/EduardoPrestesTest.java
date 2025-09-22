package br.com.valueprojects.subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EduardoPrestesTest {
    @Test
    void voucherDoesNotConsumeCredit() {
        Student s = new Student("Aluno");
        s.setPlan(Plan.BASIC);
        s.setCredits(2);
        EnrollmentService svc = new EnrollmentService();
        EnrollmentService.Result r = svc.enroll(s, "ML-101", true);
        assertEquals(true, r.accepted);
        assertEquals(2, s.getCredits());
    }
}
