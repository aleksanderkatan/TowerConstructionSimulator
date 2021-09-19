package model.mathematics;

import controller.Config;
import model.grid.pathway.NotASegmentException;
import model.grid.pathway.TileIndex;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GeometryTest {
    final double TEST_PRECISION = Config.PRECISION * 10;

    @Test
    void closeTest() {
        double one = 1.0;
        double oneAndLittleMore = 1.0 + Config.PRECISION/2;
        double two = 2.0;
        assertTrue(Geometry.close(one, one));
        assertTrue(Geometry.close(one, oneAndLittleMore));
        assertFalse(Geometry.close(one, two));
    }

    @Test
    void getOnSegmentTest() {
        Point a = new Point(1, 2), b = new Point(3, 4);
        Point r1 = Geometry.getOnSegment(a, b, 0), r2 = Geometry.getOnSegment(a, b, 22.0/7.0);
        assertEquals(1, r1.getX(), TEST_PRECISION);
        assertEquals(2, r1.getY(), TEST_PRECISION);
        assertEquals(3.2223355980148, r2.getX(), TEST_PRECISION);
        assertEquals(4.2223355980148, r2.getY(), TEST_PRECISION);
    }

    @Test
    void getOnNotASegmentTest() {
        TileIndex tileIndex = new TileIndex(1, 1);
        Point point = tileIndex.getPoint();
        assertThrows(NotASegmentException.class, () -> Geometry.getOnSegment(point, point, 0));
        assertThrows(NotASegmentException.class, () -> Geometry.getOnSegment(point, point, 10));
    }

    @Test
    void distanceFromSegmentTest() {
        Point a = new Point(1.7, 2.3);
        Point b = new Point(8.7, 3.6);
        Point point1 = new Point(-1, -1);
        Point point2 = new Point(4.6, 2.7);

        assertEquals(0, Geometry.distanceFromSegment(a, a, b), TEST_PRECISION);
        assertEquals(4.263801121065568, Geometry.distanceFromSegment(point1, a, b), TEST_PRECISION);
        assertEquals(0.13624186771345587, Geometry.distanceFromSegment(point2, a, b), TEST_PRECISION);
        assertEquals( Geometry.distanceBetween(point1, a), Geometry.distanceFromSegment(point1, a, a), TEST_PRECISION);
    }

    @Test
    void getRandomInCircleTest() {
        Point a = new Point(4.5, 6.7);
        Random random = mock(Random.class);
        when(random.nextDouble()).thenReturn(1.0 / 4);

        Point p = Geometry.getRandomInCircle(a, 1, random);

        assertEquals(4.5, p.x, TEST_PRECISION);
        assertEquals(7.2, p.y, TEST_PRECISION);
    }


}