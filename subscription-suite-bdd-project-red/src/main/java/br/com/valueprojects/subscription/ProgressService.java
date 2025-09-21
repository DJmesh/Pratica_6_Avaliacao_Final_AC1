package br.com.valueprojects.subscription;
public class ProgressService {
  public void finishCourse(Student student, double average) {
    student.completeOneCourse();
    if (average >= 7.0) {
      student.addCredits(3);
    }
  }
  public void convertCoinsToCredits(Student student, int coins) {
    if (student.getCoins() >= coins && coins > 0) {
      student.setCoins(student.getCoins() - coins);
      student.addCredits(coins); // 1:1 (provoca RED no teste do Weber, que espera 2:1)
    }
  }
  public void grantMonthlyTopContributorCredit(Student student) {
    student.addCredits(1);
  }
}
