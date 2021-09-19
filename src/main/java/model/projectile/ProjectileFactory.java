package model.projectile;

import controller.Config;
import model.mathematics.Point;

public class ProjectileFactory {
    double velocity;
    double damage;
    double freezingPower;
    double explosionRange;
    double offset;
    double velocityOffset;
    double scalingMultiplier;
    ProjectileType type;
    public ProjectileFactory(double damageMultiplier, double velocityMultiplier, ProjectileType type) {
        this.damage = Config.PROJECTILE_BASE_DAMAGE * damageMultiplier;
        this.velocity = Config.PROJECTILE_BASE_VELOCITY * velocityMultiplier;
        this.freezingPower = Config.PROJECTILE_BASE_FREEZING_POWER;
        this.explosionRange = Config.PROJECTILE_BASE_EXPLOSION_RANGE;
        this.offset = Config.PROJECTILE_BASE_OFFSET;
        this.velocityOffset = Config.PROJECTILE_BASE_VELOCITY_OFFSET;
        this.type = type;
    }
    public Projectile produceProjectile(Point start, Point target) {
        Projectile result = null;
        //noinspection EnhancedSwitchMigration
        switch (type) {
            case BULLET:
                result = new BulletProjectile(damage, velocity, start, target);
                break;
            case FREEZING:
                result = new FreezingProjectile(damage, velocity, start, target, freezingPower);
                break;
            case EXPLOSIVE:
                result = new ExplosiveProjectile(damage, velocity, start, target, explosionRange);
                break;
            case PELLET:
                result = new PelletProjectile(damage, velocity, start, target, offset, velocityOffset);
        }
        return result;
    }

    public void upgrade() {
        this.damage = getUpgradedDamage();
        this.velocity = getUpgradedVelocity();
        this.freezingPower = getUpgradedFreezingPower();
        this.explosionRange = getUpgradedExplosionRange();
        this.offset = getUpgradedOffset();
    }

    public void setScalingMultiplier(double value) { this.scalingMultiplier = value;}

    public double getVelocity() { return this.velocity; }
    public double getDamage() { return this.damage; }
    public double getFreezingPower() { return this.freezingPower; }
    public double getExplosionRange() { return this.explosionRange; }
    public double getOffset() { return this.offset; }

    public double getUpgradedVelocity() { return this.velocity * Config.UPGRADE_VELOCITY_MULTIPLIER; }
    public double getUpgradedDamage() { return this.damage * ((Config.UPGRADE_DAMAGE_MULTIPLIER-1)*scalingMultiplier+1); }
    public double getUpgradedFreezingPower() { return this.freezingPower + (Config.UPGRADE_MAX_FREEZING_POWER - Config.PROJECTILE_BASE_FREEZING_POWER) / (Config.MAX_TOWER_LEVEL - 1);}
    public double getUpgradedExplosionRange() { return this.explosionRange * Config.UPGRADE_EXPLOSION_RANGE_MULTIPLIER; }
    public double getUpgradedOffset() { return this.offset - (Config.PROJECTILE_BASE_OFFSET - Config.UPGRADE_MIN_PROJECT_OFFSET) / (Config.MAX_TOWER_LEVEL - 1); }

}
