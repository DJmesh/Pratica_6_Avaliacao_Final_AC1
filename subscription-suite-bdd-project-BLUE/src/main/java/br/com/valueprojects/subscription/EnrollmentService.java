package br.com.valueprojects.subscription;

/** Serviço de aplicação responsável pela regra de matrícula. */
public class EnrollmentService {
    public static final String ERR_INSUFFICIENT = "INSUFFICIENT_CREDIT_OR_VOUCHER";

    public EnrollmentResult enroll(Student student, String courseCode, boolean usingVoucher) {
        if (!hasAccess(student, usingVoucher)) {
            return EnrollmentResult.rejected(ERR_INSUFFICIENT);
        }
        applySideEffects(student, usingVoucher);
        return EnrollmentResult.accepted(courseCode);
    }

    /** Define se o aluno pode se matricular conforme a regra. */
    private boolean hasAccess(Student student, boolean usingVoucher) {
        return usingVoucher || student.getCredits() > 0;
    }

    /** Aplica os efeitos colaterais (consumo de crédito) de forma consistente. */
    private void applySideEffects(Student student, boolean usingVoucher) {
        // Por regra de negócio, voucher NÃO consome crédito
        if (!usingVoucher) {
            student.consumeCredit();
        }
    }
}
