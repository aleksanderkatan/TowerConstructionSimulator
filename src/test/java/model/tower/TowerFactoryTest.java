package model.tower;

import model.grid.Defaults;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TowerFactoryTest {
    List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new PathTile(), new EmptyTile()));
    List<Tile> row2 = new ArrayList<>(Arrays.asList(new PlaceableTile(), new PathTile(), new EndTile(15)));
    List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2));
    Pathway simplePathway = PathwayFactory.produce(tiles);
    Enemies simpleEnemies = new Enemies();
    TileIndex simpleIndex = new TileIndex(0, 0);

    @Test
    void testProduce() {
        TowerFactory factory = new TowerFactory(simplePathway, simpleEnemies);

        Tower tower1 = factory.produceTower(TowerType.BASIC, simpleIndex);
        Tower tower2 = factory.produceTower(TowerType.SNIPER, simpleIndex);
        Tower tower3 = factory.produceTower(TowerType.FREEZING, simpleIndex);
        Tower tower4 = factory.produceTower(TowerType.CANNON, simpleIndex);
        Tower tower5 = factory.produceTower(TowerType.SHOTGUN, simpleIndex);

        assertEquals(TowerType.BASIC, tower1.getTowerType());
        assertEquals(TowerType.SNIPER, tower2.getTowerType());
        assertEquals(TowerType.FREEZING, tower3.getTowerType());
        assertEquals(TowerType.CANNON, tower4.getTowerType());
        assertEquals(TowerType.SHOTGUN, tower5.getTowerType());
    }

}
