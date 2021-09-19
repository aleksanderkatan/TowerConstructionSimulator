package model.projectile;

import controller.Config;
import model.mathematics.Geometry;
import model.mathematics.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProjectileTest {
    Point simpleStart = new Point(0, 0);
    Point simpleEnd = new Point(1, 1);

    @Test
    void testFreezingPower() {
        FreezingProjectile projectile = new FreezingProjectile(1, 1, simpleStart, simpleEnd, 0.4);

        double freezing1 = projectile.getFreezingPower();
        projectile.setFreezingPower(0.6);
        double freezing2 = projectile.getFreezingPower();

        assertEquals(0.4, freezing1, Config.PRECISION);
        assertEquals(0.6, freezing2, Config.PRECISION);
    }

    @Test
    void testExplosionRange() {
        ExplosiveProjectile projectile = new ExplosiveProjectile(1, 1, simpleStart, simpleEnd, 1.2);

        double range1 = projectile.getExplosionRange();
        projectile.setExplosionRange(2.3);
        double range2 = projectile.getExplosionRange();

        assertEquals(1.2, range1, Config.PRECISION);
        assertEquals(2.3, range2, Config.PRECISION);
    }

    @Test
    void testPelletProjectile() {
        Point s = new Point(0.5, 1.3);
        Point t = new Point(3.4, 1.9);
        PelletProjectile projectile = new PelletProjectile(0, 10, s, t, 0, 0);
        assertEquals(0, projectile.getDamage());
        assertEquals(s, projectile.getPosition());
        assertEquals(ProjectileType.PELLET, projectile.getType());
        projectile.step();
        assertEquals(Geometry.getOnSegment(s, t, 10.0/Config.FRAME_RATE), projectile.getPosition());
    }
}
