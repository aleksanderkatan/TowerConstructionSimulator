package model.projectile;

import model.mathematics.Point;

public class ExplosiveProjectile extends Projectile {
    private double explosionRange;
    public ExplosiveProjectile(double damage, double velocity, Point start, Point target, double range) {
        super(damage, velocity, start, target, ProjectileType.EXPLOSIVE);
        setExplosionRange(range);
    }

    void setExplosionRange(double explosionRange) { this.explosionRange = explosionRange; }
    public double getExplosionRange() { return explosionRange; }
}
