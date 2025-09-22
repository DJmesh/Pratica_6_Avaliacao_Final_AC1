package br.com.valueprojects.subscription;

public class EnrollmentService {

    public static final String REJECT_REASON_INSUFFICIENT = "INSUFFICIENT_CREDIT_OR_VOUCHER";

    public static class Result {
        public final boolean accepted;
        public final String code;    // course code when accepted
        public final String reason;  // reject reason when not accepted

        private Result(boolean accepted, String code, String reason) {
            this.accepted = accepted;
            this.code = code;
            this.reason = reason;
        }

        public static Result accepted(String code) { return new Result(true, code, null); }
        public static Result rejected(String reason) { return new Result(false, null, reason); }
    }

    public Result enroll(Student student, String courseCode, boolean usingVoucher) {
        if (usingVoucher) {
            // Voucher: does not consume credit
            return Result.accepted(courseCode);
        }
        if (student.getCredits() < 1) {
            return Result.rejected(REJECT_REASON_INSUFFICIENT);
        }
        student.consumeCredit();
        return Result.accepted(courseCode);
    }
}
