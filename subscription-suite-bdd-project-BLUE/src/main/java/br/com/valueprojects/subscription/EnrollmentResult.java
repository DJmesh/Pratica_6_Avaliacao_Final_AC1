package br.com.valueprojects.subscription;

/** VO que descreve o resultado da tentativa de matrícula. */
public class EnrollmentResult {
    private final boolean accepted;
    private final String courseCode;
    private final String reason;

    private EnrollmentResult(boolean accepted, String courseCode, String reason) {
        this.accepted = accepted;
        this.courseCode = courseCode;
        this.reason = reason;
    }

    /** Fábrica para resultado ACEITO. */
    public static EnrollmentResult accepted(String code) {
        return new EnrollmentResult(true, code, null);
    }

    /** Fábrica para resultado REJEITADO. */
    public static EnrollmentResult rejected(String reason) {
        return new EnrollmentResult(false, null, reason);
    }

    public boolean isAccepted() { return accepted; }
    public String getCourseCode() { return courseCode; }
    public String getReason() { return reason; }
}
