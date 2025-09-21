package br.com.valueprojects.subscription;
public class EnrollmentResult {
  private final boolean accepted;
  private final String courseCode;
  private final String reason;
  private EnrollmentResult(boolean accepted, String courseCode, String reason) {
    this.accepted = accepted; this.courseCode = courseCode; this.reason = reason;
  }
  public static EnrollmentResult accepted(String code) { return new EnrollmentResult(true, code, null); }
  public static EnrollmentResult rejected(String reason) { return new EnrollmentResult(false, null, reason); }
  public boolean isAccepted() { return accepted; }
  public String getCourseCode() { return courseCode; }
  public String getReason() { return reason; }
}
