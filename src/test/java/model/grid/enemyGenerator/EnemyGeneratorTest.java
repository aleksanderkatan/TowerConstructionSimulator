package model.grid.enemyGenerator;

import javafx.util.Pair;
import model.enemy.Enemy;
import model.enemy.*;
import model.grid.Defaults;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyGeneratorTest {
    @Test
    void testOneEnemy() {
        EnemyGeneratorFactory enemyGeneratorFactory = new EnemyGeneratorFactory();
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();
        waveTypes.add(new Pair<>(1, Collections.singletonList(EnemyType.REGULAR)));

        EnemyGenerator enemyGenerator = enemyGeneratorFactory.produce(waveTypes, enemyFactory, 1, 0);
        Queue<Enemy> enemies = enemyGenerator.generateWave(0);
        Enemy enemy = enemies.remove();

        assertEquals(0, enemies.size());
        assertEquals(EnemyType.REGULAR, enemy.getEnemyType());
    }

    @Test
    void testManyEnemies() {
        EnemyGeneratorFactory enemyGeneratorFactory = new EnemyGeneratorFactory();
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();
        waveTypes.add(new Pair<>(2, Collections.singletonList(EnemyType.FAST)));

        EnemyGenerator enemyGenerator = enemyGeneratorFactory.produce(waveTypes, enemyFactory, 1, 0);
        Queue<Enemy> enemies = enemyGenerator.generateWave(0);
        Enemy enemy1 = enemies.remove();
        Enemy enemy2 = enemies.remove();

        assertEquals(0, enemies.size());
        assertEquals(EnemyType.FAST, enemy1.getEnemyType());
        assertEquals(EnemyType.FAST, enemy2.getEnemyType());

    }

    @Test
    void testManyWaves() {
        EnemyGeneratorFactory enemyGeneratorFactory = new EnemyGeneratorFactory();
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();
        waveTypes.add(new Pair<>(1, Collections.singletonList(EnemyType.TANK)));
        waveTypes.add(new Pair<>(1, Collections.singletonList(EnemyType.ICE)));

        EnemyGenerator enemyGenerator = enemyGeneratorFactory.produce(waveTypes, enemyFactory, 1, 0);
        Queue<Enemy> enemies1 = enemyGenerator.generateWave(0);
        Enemy enemy1 = enemies1.remove();
        Queue<Enemy> enemies2 = enemyGenerator.generateWave(1);
        Enemy enemy2 = enemies2.remove();


        assertEquals(0, enemies1.size());
        assertEquals(0, enemies2.size());
        assertEquals(EnemyType.TANK, enemy1.getEnemyType());
        assertEquals(EnemyType.ICE, enemy2.getEnemyType());

    }

    @Test
    void testMixedWave() {
        EnemyGeneratorFactory enemyGeneratorFactory = new EnemyGeneratorFactory();
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();
        waveTypes.add(new Pair<>(3, Arrays.asList(EnemyType.SWARMING, EnemyType.REGULAR)));

        EnemyGenerator enemyGenerator = enemyGeneratorFactory.produce(waveTypes, enemyFactory, 1, 0);
        Queue<Enemy> enemies = enemyGenerator.generateWave(0);
        Enemy enemy1 = enemies.remove();
        Enemy enemy2 = enemies.remove();
        Enemy enemy3 = enemies.remove();

        assertEquals(0, enemies.size());
        assertEquals(EnemyType.SWARMING, enemy1.getEnemyType());
        assertEquals(EnemyType.REGULAR, enemy2.getEnemyType());
        assertEquals(EnemyType.SWARMING, enemy3.getEnemyType());
    }

    @Test
    void testPastLastWave() {
        EnemyGeneratorFactory enemyGeneratorFactory = new EnemyGeneratorFactory();
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();
        waveTypes.add(new Pair<>(1, Collections.singletonList(EnemyType.REGULAR)));

        EnemyGenerator enemyGenerator = enemyGeneratorFactory.produce(waveTypes, enemyFactory, 1, 0);
        Queue<Enemy> enemies1 = enemyGenerator.generateWave(0);
        Queue<Enemy> enemies2 = enemyGenerator.generateWave(1);

        assertEquals(1, enemies1.size());
        assertEquals(0, enemies2.size());
    }

    @Test
    void testDefaultGenerator() {
        EnemyGenerator enemyGenerator = Defaults.getDefaultGenerator();
        Queue<Enemy> enemies = enemyGenerator.generateWave(0);
        Enemy enemy = enemies.remove();

        assertEquals(EnemyType.REGULAR, enemy.getEnemyType());
        assertEquals(9, enemies.size());
    }

}
