package model.projectile;

import model.mathematics.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileFactoryTest {
    @Test
    void produceProjectileTest() {
        ProjectileFactory bullet = new ProjectileFactory(0, 0, ProjectileType.BULLET);
        ProjectileFactory freezing = new ProjectileFactory(0, 0, ProjectileType.FREEZING);
        ProjectileFactory explosive = new ProjectileFactory(0, 0, ProjectileType.EXPLOSIVE);
        ProjectileFactory pellet = new ProjectileFactory(0, 0, ProjectileType.PELLET);

        Point a = new Point(0, 0);
        Point b = new Point(1, 1);

        assertEquals(ProjectileType.BULLET, bullet.produceProjectile(a, b).getType());
        assertEquals(ProjectileType.FREEZING, freezing.produceProjectile(a, b).getType());
        assertEquals(ProjectileType.EXPLOSIVE, explosive.produceProjectile(a, b).getType());
        assertEquals(ProjectileType.PELLET, pellet.produceProjectile(a, b).getType());
    }
}