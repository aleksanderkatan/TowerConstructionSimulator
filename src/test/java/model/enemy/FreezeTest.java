package model.enemy;

import controller.Config;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FreezeTest {
    final double TEST_PRECISION = Config.PRECISION * 10;

    @Test
    void basicTest() {
        Freeze freeze = new Freeze(3, 10);
        assertEquals(10, freeze.getFreezingPower());
        assertFalse(freeze.tick());
        assertFalse(freeze.tick());
        assertTrue(freeze.tick());
    }
    @Test
    void enemyFreezeTest() {
        Enemy regular = new Enemy(EnemyType.REGULAR, 0, 0, 1,1, 1);

        regular.step();
        double regularStep = regular.getDistance();
        regular.freeze(0.5);
        regular.step();
        assertEquals(1.5*regularStep, regular.getDistance(), TEST_PRECISION);

        for(int i=0; i<Config.FRAME_RATE*Config.FREEZE_TIME; ++i) {
            regular.step();
        }
        double prev = regular.getDistance();
        regular.step();
        assertEquals(regularStep, regular.getDistance()-prev, TEST_PRECISION);
    }
}