package br.com.valueprojects.subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class LucasTest {
    @Test
    void promoteOnlyWhenMoreThanTwelve() {
        Student s = new Student("Lucas");
        s.setPlan(Plan.BASIC);
        s.setCompletedCourses(12);
        ProgressService p = new ProgressService();
        p.finishCourse(s, 1, 8.0);
        assertEquals(Plan.PREMIUM, s.getPlan());
    }
}
