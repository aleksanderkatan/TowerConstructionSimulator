package model.tower;

import controller.Config;
import model.grid.Enemies;
import model.grid.pathway.Pathway;
import model.grid.pathway.TileIndex;
import model.projectile.ProjectileFactory;
import model.projectile.ProjectileType;

public class TowerFactory {
    private final Pathway pathway;
    private final Enemies enemies;
    public TowerFactory(Pathway pathway, Enemies enemies) {
        this.pathway = pathway;
        this.enemies = enemies;
    }

    public static double getBaseTowerRange(TowerType type) {
        double result = 1;
        //noinspection EnhancedSwitchMigration
        switch(type) {
            case BASIC:
                result = 1.5;
                break;
            case CANNON:
                result = 2;
                break;
            case FREEZING:
                result = 2.5;
                break;
            case SNIPER:
                result = 4;
                break;
            case SHOTGUN:
                result = 2.2;
                break;
        }
        return result;
    }

    public Tower produceTower(TowerType type, TileIndex tileIndex) {
        Tower result = null;
        //noinspection EnhancedSwitchMigration
        switch (type) {
            case BASIC:
                result = new Tower(type,  2, getBaseTowerRange(type), 0.75, 1,
                        new ProjectileFactory(1.5, 1, ProjectileType.BULLET),
                        new TargetingManager(pathway, enemies), tileIndex);
                break;
            case CANNON:
                result = new Tower(type, 1, getBaseTowerRange(type), 1, 1,
                        new ProjectileFactory(2, 1, ProjectileType.EXPLOSIVE),
                        new TargetingManager(pathway, enemies), tileIndex);
                break;
            case FREEZING:
                result = new Tower(type, 2, getBaseTowerRange(type), 1, 1,
                        new ProjectileFactory(0.25, 2, ProjectileType.FREEZING),
                        new TargetingManager(pathway, enemies), tileIndex);
                break;
            case SNIPER:
                result = new Tower(type, 1, getBaseTowerRange(type), 1.7, 1,
                        new ProjectileFactory(5, 3, ProjectileType.BULLET),
                        new TargetingManager(pathway, enemies), tileIndex);
                break;
            case SHOTGUN:
                result = new Tower(type, 0.75, getBaseTowerRange(type), 2, 6,
                        new ProjectileFactory(1.5, 0.75, ProjectileType.PELLET),
                        new TargetingManager(pathway, enemies), tileIndex);
                break;
        }
        return result;
    }
}
