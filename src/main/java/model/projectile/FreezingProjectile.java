package model.projectile;

import model.mathematics.Point;

public class FreezingProjectile extends Projectile {
    private double freezingPower;

    public FreezingProjectile(double damage, double velocity, Point start, Point target, double freezingPower){
        super(damage, velocity, start, target, ProjectileType.FREEZING);
        setFreezingPower(freezingPower);
    }

    void setFreezingPower(double freezingPower) { this.freezingPower = freezingPower; }
    public double getFreezingPower() { return freezingPower; }
}