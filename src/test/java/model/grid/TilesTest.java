package model.grid;

import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import model.grid.tiles.*;
import model.tower.Tower;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class TilesTest {
    @Test
    void testTileTypes() {
        StartTile startTile = new StartTile(Defaults.getDefaultGenerator());
        PathTile pathTile = new PathTile();
        EndTile endTile = new EndTile(15);
        PlaceableTile placeableTile = new PlaceableTile();
        EmptyTile emptyTile = new EmptyTile();

        Tile.TileType type1 = startTile.getType();
        Tile.TileType type2 = pathTile.getType();
        Tile.TileType type3 = endTile.getType();
        Tile.TileType type4 = placeableTile.getType();
        Tile.TileType type5 = emptyTile.getType();

        assertEquals(Tile.TileType.START, type1);
        assertEquals(Tile.TileType.PATH, type2);
        assertEquals(Tile.TileType.END, type3);
        assertEquals(Tile.TileType.PLACEABLE, type4);
        assertEquals(Tile.TileType.EMPTY, type5);
    }

    @Test
    void testEndMaxHP() {
        EndTile endTile = new EndTile(15);

        int currentLives = endTile.getRemainingHP();
        int maxLives = endTile.getMaxHP();

        assertEquals(maxLives, currentLives);
    }

    @Test
    void testEndHandleIncoming() {
        EndTile endTile = new EndTile(10);
        List<Enemy> incoming = new ArrayList<>();
        incoming.add(new Enemy(EnemyType.BOSS, 1, 1, 1, 1, 3));
        incoming.add(new Enemy(EnemyType.REGULAR, 1, 1, 1, 1, 2));

        int lives1 = endTile.getRemainingHP();
        endTile.handleIncoming(incoming);
        int lives2 = endTile.getRemainingHP();
        int difference = lives1 - lives2;

        assertEquals(5, difference);
    }

    @Test
    void testPlaceablePlaceTower() {
        PlaceableTile placeableTile = new PlaceableTile();
        Tower tower = mock(Tower.class);

        Tower contents1 = placeableTile.getTower();
        placeableTile.setTower(tower);
        Tower contents2 = placeableTile.getTower();

        assertNull(contents1);
        assertSame(tower, contents2);
    }

    @Test
    void testStartWaveIndex() {
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator enemyGenerator = factory.produce("1\n1\n1 REGULAR\n1 REGULAR\n");
        StartTile startTile = new StartTile(enemyGenerator);

        int waveIndex1 = startTile.getWaveIndex();      // wave 0
        startTile.step();                               // wave 1 wait beginning (still wave 0)
        int waveIndex2 = startTile.getWaveIndex();
        while (startTile.getCoolDown() < 1) startTile.step();   // waiting for wave 1 enemies (still wave 0)
        startTile.step();                               // enemy 1 deployed
        int waveIndex3 = startTile.getWaveIndex();
        while (startTile.getCoolDown() < 1) startTile.step();   // waiting for summoning sickness to go out
        startTile.step();                               // waiting for wave 2 enemies (still wave 1)
        int waveIndex4 = startTile.getWaveIndex();

        assertEquals(0, waveIndex1);
        assertEquals(0, waveIndex2);
        assertEquals(1, waveIndex3);
        assertEquals(1, waveIndex4);
    }

    @Test
    void testStartGetWaveTypes() {
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator enemyGenerator = factory.produce("1\n1\n1 REGULAR\n1 REGULAR\n");
        StartTile startTile = new StartTile(enemyGenerator);

        var expectedWaveTypes = enemyGenerator.getWaveTypes();
        var actualWaveTypes = startTile.getWaveTypes();

        assertEquals(expectedWaveTypes, actualWaveTypes);
    }

    @Test
    void testStartTile(){
        EnemyGeneratorFactory factory = new EnemyGeneratorFactory();
        EnemyGenerator enemyGenerator = factory.produce("1\n1\n1 REGULAR\n1 REGULAR\n");
        StartTile startTile = new StartTile(enemyGenerator);

        int a = startTile.getWaveIndex();
        int b = startTile.getNextWaveIndex();
        int waves = startTile.getTotalWavesAmount();

        assertEquals(1, b-a);
        assertEquals(2, waves);
    }
}
