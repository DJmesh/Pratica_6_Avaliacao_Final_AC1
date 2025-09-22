package br.com.valueprojects.subscription;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class EduardoWeberTest {
    @Test
    void convertCoinsRateTwoToOne() {
        Student s = new Student("Weber");
        s.setCoins(4);
        ProgressService p = new ProgressService();
        p.convertCoins(s, 4);
        assertEquals(2, s.getCredits());
        assertEquals(0, s.getCoins());
    }
}
