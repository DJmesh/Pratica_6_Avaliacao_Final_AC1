package br.com.valueprojects.subscription;
public class EnrollmentService {
  public static final String ERR_INSUFFICIENT = "INSUFFICIENT_CREDIT_OR_VOUCHER";
  public EnrollmentResult enroll(Student student, String courseCode, boolean usingVoucher) {
    if (usingVoucher) {
      // GREEN: voucher NÃO consome crédito
      return EnrollmentResult.accepted(courseCode);
    }
    if (student.getCredits() > 0) {
      student.consumeCredit();
      return EnrollmentResult.accepted(courseCode);
    }
    return EnrollmentResult.rejected(ERR_INSUFFICIENT);
  }
}
