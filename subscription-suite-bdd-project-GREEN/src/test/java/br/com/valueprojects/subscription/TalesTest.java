package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TalesTest {
  @Test
  void highAverageShouldGrantFiveCredits() {
    Student s = new Student("Tales");
    ProgressService svc = new ProgressService();
    svc.finishCourse(s, 9.1);
    assertEquals(1, s.getCompletedCourses());
    assertEquals(5, s.getCredits(), "Média >= 9.0 deve conceder 5 créditos");
  }

  @Test
  void averageBetweenSevenAndNineGrantsThreeCredits() {
    Student s = new Student("Tales");
    ProgressService svc = new ProgressService();
    svc.finishCourse(s, 7.3);
    assertEquals(3, s.getCredits(), "Média >= 7.0 e < 9.0 deve conceder 3 créditos");
  }
}
