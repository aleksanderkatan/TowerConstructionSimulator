package model.projectile;

import model.mathematics.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ExplosionTest {
    @Test
    void basicTest() {
        Point point = new Point(3, 2);
        Explosion explosion = new Explosion(3, 10, point);

        assertEquals(10, explosion.getExplosionMaxRange());
        assertSame(point, explosion.getPoint());

        assertEquals(3, explosion.getTicksToEnd());
        assertFalse(explosion.tick());
        assertEquals(2, explosion.getTicksToEnd());
        assertFalse(explosion.tick());
        assertEquals(1, explosion.getTicksToEnd());
        assertTrue(explosion.tick());
        assertEquals(0, explosion.getTicksToEnd());

        assertEquals(10, explosion.getExplosionMaxRange());
        assertSame(point, explosion.getPoint());
    }
}