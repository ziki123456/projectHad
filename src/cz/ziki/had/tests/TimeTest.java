package cz.ziki.had.tests;

import cz.ziki.had.Time;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TimeTest {

    @Test
    public void testGetTimeElapsed() {

        Time.timeStarted = System.nanoTime();

        try {
            Thread.sleep(100);
        } catch (InterruptedException _) {
        }

        double elapsedTime = Time.getTime();
        assertTrue(elapsedTime >= 0.1);
        assertTrue(elapsedTime < 0.2);

    }
}