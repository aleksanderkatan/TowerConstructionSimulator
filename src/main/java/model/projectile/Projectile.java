package model.projectile;


import controller.Config;
import model.mathematics.Geometry;
import model.mathematics.Point;

public abstract class Projectile {
    private double damage;
    private double velocity;
    private final Point start;
    private final Point target;
    private double distance;
    private final ProjectileType type;

    public Projectile(double damage, double velocity, Point start, Point target, ProjectileType type) {
        setDamage(damage);
        setVelocity(velocity);
        this.start = start;
        this.target = target;
        this.type = type;
    }

    public void step() {
        distance += velocity/Config.FRAME_RATE;
    }
    public Point getPosition() {
        return Geometry.getOnSegment(start, target, distance);
    }
    public void setDamage(double damage) { this.damage = damage; }
    public double getDamage() { return damage; }
    public void setVelocity(double velocity) { this.velocity = velocity; }
    public ProjectileType getType() {
        return type;
    }
}
