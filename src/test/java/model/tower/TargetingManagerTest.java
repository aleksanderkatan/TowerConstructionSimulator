package model.tower;

import controller.Config;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.grid.Defaults;
import model.grid.Enemies;
import model.grid.pathway.Pathway;
import model.grid.pathway.PathwayFactory;
import model.grid.pathway.TileIndex;
import model.grid.tiles.*;
import model.mathematics.Point;
import model.projectile.Projectile;
import model.projectile.ProjectileFactory;
import model.projectile.ProjectileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TargetingManagerTest {
    List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new PathTile(), new EmptyTile()));
    List<Tile> row2 = new ArrayList<>(Arrays.asList(new PlaceableTile(), new PathTile(), new EndTile(15)));
    List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2));
    Pathway simplePathway = PathwayFactory.produce(tiles);
    ProjectileFactory simpleProjectileFactory = new ProjectileFactory(1, 1, ProjectileType.BULLET);
    Enemies simpleEnemies = new Enemies();
    TileIndex simpleTileIndex = new TileIndex(0, 1);

    @Test
    void testSetTargeting() {
        TargetingManager manager = new TargetingManager(simplePathway, simpleEnemies);

        TargetingType expected1 = TargetingType.FIRST;
        TargetingType actual1 = manager.getTargetingType();
        manager.nextTargeting();
        TargetingType expected2 = expected1.next();
        TargetingType actual2 = manager.getTargetingType();
        manager.previousTargeting();
        TargetingType expected3 = expected2.prev();
        TargetingType actual3 = manager.getTargetingType();

        assertEquals(expected1, actual1);
        assertEquals(expected2, actual2);
        assertEquals(expected3, actual3);
    }

    @Test
    void testShootOutOfRange() {
        Enemy enemy = new Enemy(EnemyType.REGULAR, 1, 1, 1, 1, 1);
        List<Enemy> enemiesList = new ArrayList<>();
        enemiesList.add(enemy);
        Enemies enemies = new Enemies();
        enemies.setEnemies(enemiesList);
        // enemy is standing at 0, 0
        TargetingManager manager = new TargetingManager(simplePathway, enemies);

        Point target = manager.findTarget(1 - (Config.ENEMY_HEIGHT/2)/Config.TILE_SIZE - 0.001, new TileIndex(1, 0));
        boolean shot = (target != null);

        assertFalse(shot);
    }

    @Test
    void testShootInRange() {
        Enemy enemy = new Enemy(EnemyType.REGULAR, 1, 1, 1, 1, 1);
        List<Enemy> enemiesList = new ArrayList<>();
        enemiesList.add(enemy);
        Enemies enemies = new Enemies();
        enemies.setEnemies(enemiesList);
        // enemy is standing at 0, 0
        TargetingManager manager = new TargetingManager(simplePathway, enemies);

        Point target = manager.findTarget(1 - (Config.ENEMY_HEIGHT/2)/Config.TILE_SIZE + 0.001, new TileIndex(1, 0));
        boolean shot = (target != null);

        assertTrue(shot);
    }

}
