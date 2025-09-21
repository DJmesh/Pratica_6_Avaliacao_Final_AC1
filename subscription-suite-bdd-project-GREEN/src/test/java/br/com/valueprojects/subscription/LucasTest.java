package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LucasTest {
  @Test
  void premiumOnlyAboveTwelveCourses() {
    Student s = new Student("Lucas");
    s.setCompletedCourses(11);
    s.setPlan(Plan.BASIC);
    ProgressService svc = new ProgressService();

    // Completa o 12º: ainda BASIC
    svc.finishCourse(s, 8.0);
    assertEquals(12, s.getCompletedCourses());
    assertEquals(Plan.BASIC, s.getPlan());

    // Completa o 13º: agora PREMIUM
    svc.finishCourse(s, 8.0);
    assertEquals(13, s.getCompletedCourses());
    assertEquals(Plan.PREMIUM, s.getPlan());
  }
}
