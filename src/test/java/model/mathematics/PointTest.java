package model.mathematics;

import controller.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {
    final double TEST_PRECISION = Config.PRECISION * 10;
    @Test
    void lengthTest() {
        Point a = new Point(0, 0);
        Point b = new Point(1.2, 7.3);
        assertEquals(0, a.length(), TEST_PRECISION);
        assertEquals(7.397972695272672, b.length(), TEST_PRECISION);
    }
    void equalsTest() {
        Point a = new Point(0, 0);
        Point b = new Point(1.2, 7.3);
        Point c = new Point(0, 0);

        @SuppressWarnings("EqualsBetweenInconvertibleTypes")
        boolean result = a.equals("Point");

        assertFalse(result);
        assertNotEquals(a, b);
        assertEquals(a, c);
    }

}