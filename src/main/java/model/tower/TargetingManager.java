package model.tower;

import controller.Config;
import model.enemy.Enemy;
import model.grid.Enemies;
import model.grid.pathway.Pathway;
import model.grid.pathway.TileIndex;
import model.mathematics.Geometry;
import model.mathematics.Point;

import java.util.ArrayList;
import java.util.List;

public class TargetingManager {
    private TargetingType type;
    private final Pathway pathway;
    private final Enemies enemies;

    private boolean inRange(Enemy enemy, Double range, TileIndex index) {
        Point targetPoint = pathway.getPosition(enemy.getDistance());
        return Geometry.distanceBetween(index.getPoint(), targetPoint) <= range + (Config.ENEMY_HEIGHT/2)/Config.TILE_SIZE;
    }

    private List<Enemy> getEnemiesInRange(Double range, TileIndex index) {
        List<Enemy> result = new ArrayList<>();
        for(Enemy enemy : enemies.getEnemies()) {
            if(inRange(enemy, range, index))
                result.add(enemy);
        }
        return result;
    }

    public TargetingManager(Pathway pathway, Enemies enemies) {
        this.type = TargetingType.FIRST;
        this.enemies = enemies;
        this.pathway = pathway;
    }

    public void nextTargeting() {
        type = type.next();
    }

    public void previousTargeting() {
        type = type.prev();
    }

    public Point findTarget(Double range, TileIndex index) {
        List<Enemy> enemies = getEnemiesInRange(range, index);
        Enemy enemy = TargetFinder.findTarget(enemies, type);
        if (enemy == null) {
            return null;
        }

        return pathway.getPosition(enemy.getDistance());
    }

    public TargetingType getTargetingType() {
        return type;
    }

}
