package model.grid;

import controller.Config;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import model.grid.moneyManager.InsufficientMoneyException;
import model.grid.moneyManager.MoneyManager;
import model.grid.pathway.TileIndex;
import model.grid.tiles.NotAPlaceableTileException;
import model.grid.tiles.Tile;
import model.projectile.Projectile;
import model.tower.Tower;
import model.tower.TowerType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GridTest {
    final double PRECISION = Config.PRECISION * 10;

    @Test
    void testWaveCoolDown() throws EndOfGameException {
        String gridString = "SE\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        double coolDown1 = grid.getCurrentCoolDown(); // initialised, should be 0
        grid.step();
        double coolDown2 = grid.getCurrentCoolDown(); // first wave's wait time is starting, still should be 0
        grid.step();
        double coolDown3 = grid.getCurrentCoolDown(); // 1 frame has passed

        assertEquals(0, coolDown1, PRECISION);
        assertEquals(0, coolDown2, PRECISION);
        assertEquals(1.0 / Config.WAVE_DELAY, coolDown3, PRECISION);
    }

    @Test
    void testEnemyCoolDown() throws EndOfGameException {
        String gridString = "SE\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        grid.step(); // first wave's wait time is starting
        for (int i = 0; i< Config.WAVE_DELAY; i++) {
            grid.step();
        }   // first enemy just deployed
        grid.step();
        double coolDown1 = grid.getCurrentCoolDown();   // should be zero
        grid.step();
        double coolDown2 = grid.getCurrentCoolDown();   // should be 1/enemy cooldown

        assertEquals(0, coolDown1, PRECISION);
        assertEquals(1.0 /Config.ENEMY_DELAY, coolDown2, PRECISION);
    }

    @Test
    void testPlaceTowerInsufficientMoney() {
        String gridString = "SE\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        int cost = MoneyManager.getTowerCost(TowerType.FREEZING);
        grid.getMoneyManager().setGold(cost-1);



        assertThrows(
                InsufficientMoneyException.class,
                () -> grid.placeTower(new TileIndex(0, 1), TowerType.FREEZING)
            );
    }

    @Test
    void testPlaceTowerWrongTile() {
        String gridString = "SE\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        int cost = MoneyManager.getTowerCost(TowerType.FREEZING);
        grid.getMoneyManager().setGold(cost);



        assertThrows(
                NotAPlaceableTileException.class,
                () -> grid.placeTower(new TileIndex(0, 0), TowerType.FREEZING)
        );
    }

    @Test
    void testPlaceTower() {
        String gridString = "SE\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        int cost = MoneyManager.getTowerCost(TowerType.CANNON);
        grid.getMoneyManager().setGold(cost);

        TileIndex index = new TileIndex(1, 1);


        grid.placeTower(index, TowerType.CANNON);
        Tower tower = grid.findTowerByTileIndex(index);


        assertEquals(TowerType.CANNON, tower.getTowerType());
    }

    @Test
    void testFindTowerFail() {
        String gridString = "SE\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());
        TileIndex index = new TileIndex(1, 1);

        Tower tower = grid.findTowerByTileIndex(index);

        assertNull(tower);
    }

    @Test
    void testUpgradeTower() {
        String gridString = "SE\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        Tower tower = mock(Tower.class);
        when(tower.getTowerType()).thenReturn(TowerType.SNIPER);
        when(tower.getLevel()).thenReturn(1);
        doNothing().when(tower).upgrade();

        int cost = MoneyManager.getUpgradeCost(tower);
        grid.getMoneyManager().setGold(cost);

        grid.upgradeTower(tower);

        verify(tower, times(1)).upgrade();
    }

    @Test
    void testUpgradeTowerInsufficientMoney() {
        String gridString = "SE\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        Tower tower = mock(Tower.class);
        when(tower.getTowerType()).thenReturn(TowerType.SNIPER);
        when(tower.getLevel()).thenReturn(1);
        doNothing().when(tower).upgrade();

        int cost = MoneyManager.getUpgradeCost(tower);
        grid.getMoneyManager().setGold(cost-1);


        assertThrows(InsufficientMoneyException.class, ()->grid.upgradeTower(tower));
    }

    @Test
    void testGetTile() {
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        Tile tile = grid.getTile(0, 1);

        assertEquals(Tile.TileType.PLACEABLE, tile.getType());
    }

    @Test
    void testEndGameNoEnemiesLeft() throws EndOfGameException {
        String generatorString = "1\n0\n1 REGULAR";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator generator = factory.produce(generatorString);
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, generator);


        grid.step();
        while (grid.getCurrentCoolDown() < 1) grid.step();      // wait for first enemy to be deployed
        grid.step();

        Enemy enemy = grid.getEnemiesList().get(0);
        int steps = (int)Math.ceil(grid.getPathway().getLength() / enemy.getSpeed() * Config.FRAME_RATE);
        for (int i = 0; i< steps; i++) grid.step();

        assertThrows(EndOfGameException.class, grid::step);
    }

    @Test
    void testEndGameNoHealthLeft() throws EndOfGameException {
        String generatorString = "1\n0\n15 REGULAR";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator generator = factory.produce(generatorString);
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, generator);
        grid.getPathway().getEndTile().setRemainingHP(1);


        grid.step();
        while (grid.getCurrentCoolDown() < 1) grid.step();      // wait for enemy to be deployed
        grid.step();

        Enemy enemy = grid.getEnemiesList().get(0);
        int steps = (int) Math.ceil(grid.getPathway().getLength() / enemy.getSpeed() * Config.FRAME_RATE);
        for (int i = 0; i < steps; i++) grid.step();

        assertThrows(EndOfGameException.class, grid::step);
    }

    @Test
    void testCreatingProjectiles() throws EndOfGameException {
        String generatorString = "1\n0\n15 REGULAR";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator generator = factory.produce(generatorString);
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, generator);

        grid.placeTower(new TileIndex(0, 1), TowerType.SNIPER);

        grid.step();
        while (grid.getCurrentCoolDown() < 1) grid.step();      // wait for enemy to be deployed


        grid.step();        // projectile should appear
        List<Projectile> list = grid.getProjectiles();


        assertEquals(1, list.size());
    }

    @Test
    void testGetTowers() {
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        grid.placeTower(new TileIndex(0, 1), TowerType.SNIPER);
        List<Tower> towers = grid.getTowers();

        assertEquals(1, towers.size());
        assertEquals(TowerType.SNIPER, towers.get(0).getTowerType());
    }

    @Test
    void testDeadEnemy() throws EndOfGameException {
        String generatorString = "1\n0\n15 REGULAR";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator generator = factory.produce(generatorString);
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, generator);
        Enemy enemy = new Enemy(EnemyType.REGULAR, 1, 1, 1, 1, 1);
        List<Enemy> enemies = Collections.singletonList(enemy);
        grid.getEnemies().setEnemies(enemies);

        int moneyBefore = grid.getMoneyManager().getGold();
        enemy.setHP(0);
        grid.step();
        int moneyAfter = grid.getMoneyManager().getGold();
        int difference = moneyAfter - moneyBefore;

        assertEquals(1, difference);
    }

    @Test
    void testInitialExplosions() {
        String gridString = "SE\nLP\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        int amount = grid.getExplosions().size();

        assertEquals(0, amount);
    }
}
