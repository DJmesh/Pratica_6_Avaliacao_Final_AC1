package br.com.valueprojects.subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class TalesTest {
    @Test
    void gainFiveCreditsWhenAvgAtLeastNine() {
        Student s = new Student("Tales");
        ProgressService p = new ProgressService();
        p.finishCourse(s, 1, 9.1);
        assertEquals(1, s.getCompletedCourses());
        assertEquals(5, s.getCredits());
        assertEquals(Plan.BASIC, s.getPlan());
    }

    @Test
    void gainThreeCreditsWhenAvgBetweenSevenAndNine() {
        Student s = new Student("Tales");
        ProgressService p = new ProgressService();
        p.finishCourse(s, 1, 7.3);
        assertEquals(3, s.getCredits());
    }
}
