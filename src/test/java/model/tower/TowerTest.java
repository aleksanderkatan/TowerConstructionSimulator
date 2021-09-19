package model.tower;

import controller.Config;
import model.grid.Defaults;
import model.projectile.Projectile;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.grid.Enemies;
import model.grid.pathway.Pathway;
import model.grid.pathway.PathwayFactory;
import model.grid.pathway.TileIndex;
import model.grid.tiles.*;
import model.projectile.ProjectileFactory;
import model.projectile.ProjectileType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TowerTest {
    List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new PathTile(), new EmptyTile()));
    List<Tile> row2 = new ArrayList<>(Arrays.asList(new PlaceableTile(), new PathTile(), new EndTile(15)));
    List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2));
    Pathway simplePathway = PathwayFactory.produce(tiles);
    ProjectileFactory simpleProjectileFactory = new ProjectileFactory(1, 1, ProjectileType.BULLET);
    Enemies simpleEnemies = new Enemies();
    TileIndex simpleTileIndex = new TileIndex(0, 1);

    @Test
    void testUpgrade() {
        Tower tower = new Tower(TowerType.SHOTGUN, 1, 1, 1, 1,
                simpleProjectileFactory,
                new TargetingManager(simplePathway, simpleEnemies), simpleTileIndex);

        int levelExpected = tower.getLevel() + 1;
        TowerStats statsExpected = tower.getUpgradedStats();
        tower.upgrade();
        int levelActual = tower.getLevel();
        TowerStats statsActual = tower.getCurrentStats();

        assertEquals(levelExpected, levelActual);
        assertEquals(statsExpected, statsActual);
    }

    @Test
    void testMaxLevel() {
        Tower tower = new Tower(TowerType.BASIC, 1, 1, 1, 1,
                simpleProjectileFactory,
                new TargetingManager(simplePathway, simpleEnemies), simpleTileIndex);

        boolean isMaxLevel1 = tower.isMaxLevel();
        for (int i = 1; i< Config.MAX_TOWER_LEVEL; i++) {
            tower.upgrade();
        }
        boolean isMaxLevel2 = tower.isMaxLevel();

        assertFalse(isMaxLevel1);
        assertTrue(isMaxLevel2);
        assertThrows(MaxLevelException.class, tower::upgrade);
    }

    @Test
    void testTileIndex() {
        TileIndex tileIndex = new TileIndex(0, 1);
        Tower tower = new Tower(TowerType.CANNON, 1, 1, 1, 1,
                simpleProjectileFactory,
                new TargetingManager(simplePathway, simpleEnemies), tileIndex);

        TileIndex actualIndex = tower.getTileIndex();

        assertEquals(tileIndex, actualIndex);
    }


    @Test
    void testShootCoolDown() {
        Enemy enemy = new Enemy(EnemyType.REGULAR, 1, 1, 1, 1, 1);
        List<Enemy> enemiesList = new ArrayList<>();
        enemiesList.add(enemy);
        Enemies enemies = new Enemies();
        enemies.setEnemies(enemiesList);
        Tower tower = new Tower(TowerType.BASIC, 1, 1, 1, 1,
                simpleProjectileFactory,
                new TargetingManager(simplePathway, enemies), simpleTileIndex);

        List<Projectile> projectiles1 = tower.step();
        boolean shot1 = (projectiles1.size() > 0);
        List<Projectile> projectiles2 = tower.step();
        boolean shot2 = (projectiles2.size() > 0);

        assertTrue(shot1);
        assertFalse(shot2);
    }

    @Test
    void testTowerType() {
        TowerType expectedType = TowerType.SNIPER;
        Tower tower = new Tower(expectedType, 1, 1, 1, 1,
                simpleProjectileFactory,
                new TargetingManager(simplePathway, simpleEnemies), simpleTileIndex);

        TowerType actualType = tower.getTowerType();

        assertEquals(expectedType, actualType);
    }

    @Test
    void testTargetingManager() {
        TargetingManager manager = new TargetingManager(simplePathway, simpleEnemies);
        TowerType expectedType = TowerType.SNIPER;
        Tower tower = new Tower(expectedType, 1, 1, 1, 1,
                simpleProjectileFactory,
                manager, simpleTileIndex);

        TargetingManager towerManager = tower.getTargetingManager();

        assertEquals(manager, towerManager);
    }

    @Test
    void testIdleCoolDown() {
        Enemy enemy = new Enemy(EnemyType.REGULAR, 1, 1, 1, 1, 1);
        List<Enemy> enemiesList = new ArrayList<>();
        Enemies enemies = new Enemies();
        enemies.setEnemies(enemiesList);
        Tower tower = new Tower(TowerType.BASIC, 1, 1, 1, 1,
                simpleProjectileFactory,
                new TargetingManager(simplePathway, enemies), simpleTileIndex);

        List<Projectile> projectiles1 = tower.step();
        boolean shot1 = (projectiles1.size() > 0);
        enemiesList.add(enemy);
        List<Projectile> projectiles2 = tower.step();
        boolean shot2 = (projectiles2.size() > 0);

        assertFalse(shot1);
        assertTrue(shot2);
    }

}
