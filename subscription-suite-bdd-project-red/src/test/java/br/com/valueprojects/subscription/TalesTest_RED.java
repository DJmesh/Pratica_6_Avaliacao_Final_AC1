package br.com.valueprojects.subscription;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/** Integrante: Tales
 *  Hipótese: média >= 9.0 concede bônus de 5 créditos (e não apenas 3).
 *  Implementação atual concede 3 → TESTE DEVE FALHAR (RED).
 */
class TalesTest_RED {
  @Test
  void highAverageShouldGrantFiveCredits_RED() {
    // Arrange
    Student s = new Student("Tales");
    ProgressService svc = new ProgressService();
    // Act
    svc.finishCourse(s, 9.1);
    // Assert (hipótese Tales)
    assertEquals(1, s.getCompletedCourses());
    assertEquals(5, s.getCredits(), "Média >= 9.0 deveria conceder 5 créditos"); // <-- falha (concede 3)
  }
}
