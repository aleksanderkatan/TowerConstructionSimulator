package model.projectile;

import model.mathematics.Geometry;
import model.mathematics.Point;

import java.util.Random;

public class PelletProjectile extends Projectile {

    private static double randomize(double value, double offset, Random random) {
        double multiplier = 1.0 + (random.nextDouble() * 2 - 1) * offset;
        return value * multiplier;
    }

    public PelletProjectile(double damage, double velocity, Point start, Point target, double offset, double velocityOffset) {
        super(damage, randomize(velocity, velocityOffset, new Random()), start,
                Geometry.getRandomInCircle(Geometry.getOnSegment(start, target, 1.0), offset, new Random()), ProjectileType.PELLET);
    }
}
