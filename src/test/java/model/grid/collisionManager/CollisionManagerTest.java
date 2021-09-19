package model.grid.collisionManager;

import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.mathematics.Point;
import model.grid.Defaults;
import model.grid.Enemies;
import model.grid.Grid;
import model.grid.GridFactory;
import model.grid.pathway.Pathway;
import model.grid.pathway.PathwayFactory;
import model.grid.tiles.*;
import model.projectile.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CollisionManagerTest {

    List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new PathTile(), new EmptyTile()));
    List<Tile> row2 = new ArrayList<>(Arrays.asList(new PlaceableTile(), new PathTile(), new EndTile(15)));
    List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2));
    Pathway simplePathway = PathwayFactory.produce(tiles);
    @Test
    void checkIfHitTest() {
        Point a = new Point(0, 0);
        Point b = new Point(1, 1);
        Point c = new Point(0.5, 0.5);

        Projectile bullet = new BulletProjectile(0, 0, a, b);
        Projectile bullet2 = new BulletProjectile(0, 0, c, b);
        Projectile explosive = new ExplosiveProjectile(0, 0, c, b, 2);
        Projectile freezing = new FreezingProjectile(0, 0, c, b, 0.5);


        Enemies enemies = new Enemies();
        Enemy enemy = new Enemy(EnemyType.REGULAR, 0, 0 , 1 , 1,1);
        Enemy enemy2 = new Enemy(EnemyType.REGULAR, 0, 0 , 1 , 1,1);
        enemies.getEnemies().add(enemy);

        List<Explosion> explosions = new ArrayList<>();

        assertFalse(CollisionManager.checkIfHitAndReact(bullet, enemies, simplePathway, explosions));
        assertTrue(CollisionManager.checkIfHitAndReact(bullet2, enemies, simplePathway, explosions));
        assertEquals(0, explosions.size());
        assertTrue(CollisionManager.checkIfHitAndReact(explosive, enemies, simplePathway, explosions));
        assertEquals(1, explosions.size());
        assertTrue(CollisionManager.checkIfHitAndReact(freezing, enemies, simplePathway, explosions));
        enemy.step();
        enemy2.step();
        assertTrue(enemy.getDistance()<enemy2.getDistance());
    }

    @Test
    void testCheckIfOutsideNoOffset() {
        String gridString = "SE\nLL\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());
        List<List<Tile>> tiles = grid.getTiles();

        ProjectileFactory factory = new ProjectileFactory(1, 1, ProjectileType.BULLET);

        Projectile projectile1 = factory.produceProjectile(new Point(-1, 1), new Point(1, 1));
        Projectile projectile2 = factory.produceProjectile(new Point(4, 1), new Point(1, 1));
        Projectile projectile3 = factory.produceProjectile(new Point(1, 0.5), new Point(1, 1));
        Projectile projectile4 = factory.produceProjectile(new Point(1, -2), new Point(1, 1));
        Projectile projectile5 = factory.produceProjectile(new Point(1, 4), new Point(1, 1));


        boolean result1 = CollisionManager.checkIfOutsideAndReact(projectile1, tiles, 0);
        boolean result2 = CollisionManager.checkIfOutsideAndReact(projectile2, tiles, 0);
        boolean result3 = CollisionManager.checkIfOutsideAndReact(projectile3, tiles, 0);
        boolean result4 = CollisionManager.checkIfOutsideAndReact(projectile4, tiles, 0);
        boolean result5 = CollisionManager.checkIfOutsideAndReact(projectile5, tiles, 0);


        assertTrue(result1);
        assertTrue(result2);
        assertFalse(result3);
        assertTrue(result4);
        assertTrue(result5);
    }

    @Test
    void testCheckIfOutsideWithOffset() {
        String gridString = "SE\nLL\nLL\n";
        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());
        List<List<Tile>> tiles = grid.getTiles();

        ProjectileFactory factory = new ProjectileFactory(1, 1, ProjectileType.BULLET);

        Projectile projectile1 = factory.produceProjectile(new Point(-0.6, 1), new Point(1, 1));
        Projectile projectile2 = factory.produceProjectile(new Point(2.9, 1), new Point(1, 1));
        Projectile projectile3 = factory.produceProjectile(new Point(-5, 0.5), new Point(1, 1));
        Projectile projectile4 = factory.produceProjectile(new Point(1, -0.6), new Point(1, 1));
        Projectile projectile5 = factory.produceProjectile(new Point(1, 3.2), new Point(1, 1));

        boolean result1 = CollisionManager.checkIfOutsideAndReact(projectile1, tiles, 1);
        boolean result2 = CollisionManager.checkIfOutsideAndReact(projectile2, tiles, 1);
        boolean result3 = CollisionManager.checkIfOutsideAndReact(projectile3, tiles, 1);
        boolean result4 = CollisionManager.checkIfOutsideAndReact(projectile4, tiles, 1);
        boolean result5 = CollisionManager.checkIfOutsideAndReact(projectile5, tiles, 1);


        assertFalse(result1);
        assertFalse(result2);
        assertTrue(result3);
        assertFalse(result4);
        assertFalse(result5);
    }
}