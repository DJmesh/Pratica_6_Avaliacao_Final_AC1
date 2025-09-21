package br.com.valueprojects.subscription;
public class EnrollmentService {
  public static final String ERR_INSUFFICIENT = "INSUFFICIENT_CREDIT_OR_VOUCHER";
  public EnrollmentResult enroll(Student student, String courseCode, boolean usingVoucher) {
    if (student.getCredits() > 0 || usingVoucher) {
      student.consumeCredit(); // consumir crédito mesmo usando voucher (provoca RED no teste do Prestes)
      return EnrollmentResult.accepted(courseCode);
    }
    return EnrollmentResult.rejected(ERR_INSUFFICIENT);
  }
}
