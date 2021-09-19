package model.tower;


import controller.Config;
import model.projectile.Projectile;
import model.enemy.Enemy;
import model.mathematics.Geometry;
import model.grid.Enemies;
import model.grid.pathway.Pathway;
import model.mathematics.Point;
import model.grid.pathway.TileIndex;
import model.projectile.ProjectileFactory;

import java.util.ArrayList;
import java.util.List;

public class Tower implements Config {
    private double fireRate;
    private double range;
    private final double scalingMultiplier;
    private int projectileAmount;
    private final ProjectileFactory factory;
    private final TowerType towerType;

    private final TargetingManager targetingManager;

    private final TileIndex tileIndex;
    private int level = 1;
    private double timeToNextShot = 0;


    public Tower(TowerType towerType, double fireRate, double range, double scalingMultiplier, int projectileAmount,
                 ProjectileFactory factory,
                 TargetingManager targetingManager, TileIndex tileIndex) {
        this.fireRate = fireRate;
        this.range = range;
        this.scalingMultiplier = scalingMultiplier;
        this.projectileAmount = projectileAmount;
        this.factory = factory;
        this.factory.setScalingMultiplier(scalingMultiplier);
        this.towerType = towerType;
        this.tileIndex = tileIndex;
        this.targetingManager = targetingManager;
    }

    public TileIndex getTileIndex() {
        return tileIndex;
    }

    public List<Projectile> step() {
        ArrayList<Projectile> result = new ArrayList<>();
        if(--timeToNextShot >= 0)
            return result;

        Point target = targetingManager.findTarget(range, tileIndex);

        if(target == null) {
            timeToNextShot = 0;
            return result;
        }

        for (int i = 0; i < projectileAmount; i++) {
            result.add(factory.produceProjectile(tileIndex.getPoint(), target));
        }
        timeToNextShot = FRAME_RATE/fireRate;
        return result;
    }

    public TargetingManager getTargetingManager() { return this.targetingManager; }

    public boolean isMaxLevel() {
        return level == Config.MAX_TOWER_LEVEL;
    }

    public TowerStats getCurrentStats() {
        TowerStats stats = new TowerStats();

        stats.type = getTowerType();
        stats.fireRate = getFireRate();
        stats.damage = factory.getDamage();
        stats.range = getRange();
        stats.projectileVelocity = factory.getVelocity();
        stats.freezingPower = factory.getFreezingPower();
        stats.explosionRange = factory.getExplosionRange();
        stats.projectileAmount = getProjectileAmount();
        stats.projectileOffset = factory.getOffset();

        return stats;
    }

    public TowerStats getUpgradedStats() {
        TowerStats stats = new TowerStats();

        stats.type = getTowerType();
        stats.fireRate = getUpgradedFireRate();
        stats.damage = factory.getUpgradedDamage();
        stats.range = getUpgradedRange();
        stats.projectileVelocity = factory.getUpgradedVelocity();
        stats.freezingPower = factory.getUpgradedFreezingPower();
        stats.explosionRange = factory.getUpgradedExplosionRange();
        stats.projectileAmount = getUpgradedProjectileAmount();
        stats.projectileOffset = factory.getUpgradedOffset();

        return stats;
    }

    public void upgrade() {
        if (this.level >= Config.MAX_TOWER_LEVEL) {
            throw new MaxLevelException();
        }

        this.fireRate = getUpgradedFireRate();
        this.range = getUpgradedRange();
        this.projectileAmount = getUpgradedProjectileAmount();
        this.factory.upgrade();

        this.level += 1;
    }

    private double getFireRate() {
        return this.fireRate;
    }

    // view needs it
    public double getRange() {
        return this.range;
    }

    private int getProjectileAmount() { return projectileAmount; }

    private int getUpgradedProjectileAmount() {
        if (towerType.equals(TowerType.SHOTGUN))
            return projectileAmount + 1;
        return projectileAmount;
    }

    private double getUpgradedFireRate() { return this.fireRate * ((Config.UPGRADE_FIRE_RATE_MULTIPLIER-1) * scalingMultiplier + 1); }

    private double getUpgradedRange() { return this.range * Config.UPGRADE_RANGE_MULTIPLIER; }

    public TowerType getTowerType(){ return towerType; }

    public int getLevel() { return level; }
}
