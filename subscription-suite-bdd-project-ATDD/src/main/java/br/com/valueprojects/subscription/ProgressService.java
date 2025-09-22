package br.com.valueprojects.subscription;

public class ProgressService {

    /**
     * Finish {count} courses with the given average.
     * >= 9.0 -> +5 credits
     * 7.0 <= avg < 9.0 -> +3 credits
     * < 7.0 -> +0 credits
     * Promotion to PREMIUM only when completedCourses > 12.
     */
    public void finishCourse(Student student, int count, double average) {
        // update courses
        student.addCompletedCourses(count);

        // award credits
        if (average >= 9.0) {
            student.addCredits(5);
        } else if (average >= 7.0) {
            student.addCredits(3);
        }

        // promotion rule: strictly greater than 12
        if (student.getCompletedCourses() > 12) {
            student.setPlan(Plan.PREMIUM);
        } else {
            student.setPlan(Plan.BASIC);
        }
    }

    /** Convert coins to credits at a 2:1 rate. */
    public void convertCoins(Student student, int coinsToConvert) {
        int available = Math.min(coinsToConvert, student.getCoins());
        int credits = available / 2;
        student.setCoins(available - credits * 2 + (student.getCoins() - available));
        student.addCredits(credits);
    }
}
