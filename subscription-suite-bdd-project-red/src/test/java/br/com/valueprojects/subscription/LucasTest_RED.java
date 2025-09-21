package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Integrante: Lucas
 *  Hipótese: promoção para PREMIUM somente com MAIS de 12 cursos (>=13).
 *  Implementação atual promove com 12 → TESTE DEVE FALHAR (RED).
 */
class LucasTest_RED {
  @Test
  void premiumOnlyAboveTwelveCourses_RED() {
    // Arrange
    Student s = new Student("Lucas");
    s.setCompletedCourses(11);
    s.setPlan(Plan.BASIC);
    ProgressService svc = new ProgressService();
    // Act
    svc.finishCourse(s, 8.0); // completa o 12º
    // Assert (hipótese Lucas)
    assertEquals(12, s.getCompletedCourses());
    assertEquals(Plan.BASIC, s.getPlan(), "Com 12 cursos ainda deveria ser BASIC"); // <-- falha (promove para PREMIUM)
  }
}
