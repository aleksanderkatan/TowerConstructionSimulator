package model.grid.enemyGenerator;

import controller.Config;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import model.grid.enemyGenerator.UnsupportedEnemyGeneratorStringException;
import org.junit.jupiter.api.Test;

import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;

class EnemyGeneratorFactoryTest {
    @Test
    void testUnsupported() {
        String generatorString1 = "1\n1\n";  // no waves
        String generatorString2 = "1\nnotadouble\n2 REGULAR";
        String generatorString3 = "1\n0\n-2 REGULAR";
        String generatorString4 = "1\n0\nnotaninteger REGULAR";
        String generatorString5 = "1\n0\n10 WEIRD";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();

        assertThrows(UnsupportedEnemyGeneratorStringException.class, () -> factory.produce(generatorString1));
        assertThrows(UnsupportedEnemyGeneratorStringException.class, () -> factory.produce(generatorString2));
        assertThrows(UnsupportedEnemyGeneratorStringException.class, () -> factory.produce(generatorString3));
        assertThrows(UnsupportedEnemyGeneratorStringException.class, () -> factory.produce(generatorString4));
        assertThrows(UnsupportedEnemyGeneratorStringException.class, () -> factory.produce(generatorString5));
    }

    @Test
    void testThreeWaves() {
        String generatorString = "1\n0\n1 REGULAR\n1 ICE\n1 BOSS\n1 SWARMING";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();

        EnemyGenerator generator = factory.produce(generatorString);
        Queue<Enemy> wave0 = generator.generateWave(0);
        Queue<Enemy> wave1 = generator.generateWave(1);
        Queue<Enemy> wave2 = generator.generateWave(2);
        Queue<Enemy> wave3 = generator.generateWave(3);
        EnemyType type0 = wave0.remove().getEnemyType();
        EnemyType type1 = wave1.remove().getEnemyType();
        EnemyType type2 = wave2.remove().getEnemyType();
        EnemyType type3 = wave3.remove().getEnemyType();

        assertEquals(EnemyType.REGULAR, type0);
        assertEquals(EnemyType.ICE, type1);
        assertEquals(EnemyType.BOSS, type2);
        assertEquals(EnemyType.SWARMING, type3);
    }

    @Test
    void testNoEndLine() {
        String generatorString = "1\n0\n1 TANK";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();

        EnemyGenerator generator = factory.produce(generatorString);
        Queue<Enemy> wave = generator.generateWave(0);

        assertEquals(EnemyType.TANK, wave.remove().getEnemyType());
    }

    @Test
    void testScaling() {
        String generatorString = "1.5\n0\n1 FAST\n1 FAST\n";
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();

        EnemyGenerator generator = factory.produce(generatorString);
        Enemy enemy1 = generator.generateWave(0).remove();
        Enemy enemy2 = generator.generateWave(1).remove();
        double healthDifference = enemy2.getMaxHP() / enemy1.getMaxHP();

        assertEquals(1.5, healthDifference, Config.PRECISION);
    }

}
