package br.com.valueprojects.subscription;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LucasTest {

  @Test
  void premiumSomenteAcimaDeDozeCursos() {
    Student s = new Student("Lucas");
    s.setCompletedCourses(11);
    s.setPlan(Plan.BASIC);
    ProgressService svc = new ProgressService();

    // Completa o 12º: ainda BASIC (regra é > 12)
    svc.finishCourse(s, 8.0);
    assertEquals(12, s.getCompletedCourses());
    assertEquals(Plan.BASIC, s.getPlan());

    // Completa o 13º: agora PREMIUM (condição true do if)
    svc.finishCourse(s, 8.0);
    assertEquals(13, s.getCompletedCourses());
    assertEquals(Plan.PREMIUM, s.getPlan());

    // *** Cobertura do curto-circuito do lado esquerdo do && ***
    // Já é PREMIUM: o lado esquerdo (plan == BASIC) é falso,
    // o lado direito nem é avaliado; não altera o plano.
    svc.finishCourse(s, 8.0);
    assertEquals(14, s.getCompletedCourses());
    assertEquals(Plan.PREMIUM, s.getPlan());
  }

  @Test
  void naoPromoveSeJaForPremiumMesmoComMuitosCursos() {
    Student s = new Student("Lucas");
    s.setPlan(Plan.PREMIUM);
    s.setCompletedCourses(50);
    ProgressService svc = new ProgressService();

    svc.finishCourse(s, 9.5); // mantém PREMIUM; cobre o ramo "A falso" do &&
    assertEquals(51, s.getCompletedCourses());
    assertEquals(Plan.PREMIUM, s.getPlan());
  }
}
