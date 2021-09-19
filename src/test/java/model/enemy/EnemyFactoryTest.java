package model.enemy;

import model.grid.Defaults;
import org.junit.jupiter.api.Test;

import java.util.Random;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnemyFactoryTest {
    @Test
    void testProduceRandom(){
        Random random = mock(Random.class);
        when(random.nextDouble()).thenReturn(0.25);
        EnemyFactory enemyFactoryRandom = new EnemyFactory(random);
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());

        Enemy e1 = enemyFactoryRandom.produceBossEnemy(1, 1);
        Enemy e2 = enemyFactory.produceBossEnemy(1, 0);

        assertEquals(0.5, e1.getHP()/e2.getHP());
        assertEquals(0.5, 1.0*e1.getGold()/e2.getGold());
    }
    @Test
    void testProduce(){
        EnemyFactory enemyFactory = new EnemyFactory(Defaults.getDefaultRandom());

        Enemy e1 = enemyFactory.produceSwarmingEnemy(1, 0);
        Enemy e2 = enemyFactory.produceRegularEnemy(1, 0);
        Enemy e3 = enemyFactory.produceIceEnemy(1, 0);
        Enemy e4 = enemyFactory.produceFastEnemy(1, 0);
        Enemy e5 = enemyFactory.produceTankEnemy(1, 0);

        assertEquals(EnemyType.SWARMING, e1.getEnemyType());
        assertEquals(EnemyType.REGULAR, e2.getEnemyType());
        assertEquals(EnemyType.ICE, e3.getEnemyType());
        assertEquals(EnemyType.FAST, e4.getEnemyType());
        assertEquals(EnemyType.TANK, e5.getEnemyType());
    }

}
