package br.com.valueprojects.subscription;
public class ProgressService {

  private static final double APPROVAL_7 = 7.0;
  private static final double APPROVAL_9 = 9.0;
  private static final int CREDITS_3 = 3;
  private static final int CREDITS_5 = 5;
  private static final int PREMIUM_THRESHOLD_EXCLUSIVE = 12; // somente acima

  public void finishCourse(Student student, double average) {
    student.completeOneCourse();
    if (average >= APPROVAL_9) {
      student.addCredits(CREDITS_5);        // GREEN para Tales
    } else if (average >= APPROVAL_7) {
      student.addCredits(CREDITS_3);        // mantém a regra original
    }
    promoteIfEligible(student);
  }

  public void convertCoinsToCredits(Student student, int coins) {
    if (coins > 0 && student.getCoins() >= coins) {
      student.setCoins(student.getCoins() - coins);
      int creditsToAdd = coins / 2;         // GREEN para Weber (2:1)
      student.addCredits(creditsToAdd);
    }
  }

  public void grantMonthlyTopContributorCredit(Student student) {
    student.addCredits(1);
  }

  private void promoteIfEligible(Student student) {
    if (student.getCompletedCourses() > PREMIUM_THRESHOLD_EXCLUSIVE
        && student.getPlan() == Plan.BASIC) { // GREEN para Lucas (apenas acima de 12)
      student.setPlan(Plan.PREMIUM);
    }
  }
}
