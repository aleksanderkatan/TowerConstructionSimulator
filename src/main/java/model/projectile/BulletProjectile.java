package model.projectile;

import model.mathematics.Point;

public class BulletProjectile extends Projectile {
    public BulletProjectile(double damage, double velocity, Point start, Point target) {
        super(damage, velocity, start, target, ProjectileType.BULLET);
    }
}
