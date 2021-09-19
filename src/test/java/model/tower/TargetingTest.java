package model.tower;

import model.enemy.Enemy;
import model.enemy.EnemyType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TargetingTest {
    Enemy enemy1 = new Enemy(EnemyType.REGULAR, 10, 1, 1, 1, 1);
    Enemy enemy2 = new Enemy(EnemyType.REGULAR, 10, 1, 1, 1, 1);
    Enemy enemy3 = new Enemy(EnemyType.REGULAR, 20, 1, 1, 1, 1);
    Enemy enemy4 = new Enemy(EnemyType.REGULAR, 10, 1, 1, 1, 1);
    List<Enemy> enemies = Arrays.asList(enemy1, enemy2, enemy3, enemy4);

    {
        enemy1.step();

        enemy3.step();
        enemy3.step();

        enemy4.step();
        enemy4.step();
        enemy4.step();
    }                   // order of enemies on battlefield: 2, 1, 3, 4

    @Test
    void testNextPrevTargeting() {
        TargetingType type = TargetingType.RANDOM;

        TargetingType newType = type.next().prev();

        assertEquals(type, newType);
    }

    @Test
    void testFirst() {

        Enemy target = TargetFinder.findTarget(enemies, TargetingType.FIRST);

        assertSame(enemy4, target);
    }

    @Test
    void testLast() {

        Enemy target = TargetFinder.findTarget(enemies, TargetingType.LAST);

        assertSame(enemy2, target);
    }

    @Test
    void testStrong() {

        Enemy target = TargetFinder.findTarget(enemies, TargetingType.STRONG);

        assertSame(enemy3, target);
    }

    @Test
    void testRandom() {

        Enemy target = TargetFinder.findTarget(enemies, TargetingType.RANDOM);
        boolean inList = enemies.contains(target);

        assertTrue(inList);
    }
}
