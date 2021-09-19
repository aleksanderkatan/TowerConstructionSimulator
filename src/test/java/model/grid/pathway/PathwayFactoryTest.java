package model.grid.pathway;
import model.grid.Defaults;
import model.grid.tiles.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class PathwayFactoryTest {
    @Test
    void testNull(){

        assertThrows(InvalidGridStructureException.class, ()-> PathwayFactory.produce(null));

    }
    @Test
    void testStartTileNotFound(){
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new PathTile(), new EndTile(15)));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2));

        assertThrows(InvalidGridStructureException.class, ()->PathwayFactory.produce(tiles));

    }
    @Test
    void testEndTileNotFound(){
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new PathTile(), new EmptyTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2));

        assertThrows(InvalidGridStructureException.class, ()->PathwayFactory.produce(tiles));

    }

    @Test
    void testSimplePathway(){
        List<TileIndex> tileIndexList = new ArrayList<>(Arrays.asList(new TileIndex(0,0), new TileIndex(1, 0), new TileIndex(1, 1), new TileIndex(1, 2), new TileIndex(2, 2)));
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new PathTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new EmptyTile(), new PathTile(), new EmptyTile()));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new PathTile(), new PathTile(), new EndTile(15)));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        Pathway pathway = PathwayFactory.produce(tiles);

        assertEquals(tileIndexList, pathway.getTiles());
    }
    @Test
    void testDifferentTiles(){
        List<TileIndex> tileIndexList = new ArrayList<>(Arrays.asList(new TileIndex(0, 0), new TileIndex(1, 0), new TileIndex(2, 0), new TileIndex(3, 0),
                new TileIndex(3,1), new TileIndex(3,2), new TileIndex(2,2), new TileIndex(1, 2), new TileIndex(0,2)));
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new PathTile(), new PathTile(), new PathTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new EmptyTile(), new PlaceableTile(), new PlaceableTile(), new PathTile()));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new EndTile(15), new PathTile(), new PathTile(), new PathTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        Pathway pathway = PathwayFactory.produce(tiles);

        assertEquals(tileIndexList, pathway.getTiles());
    }
    @Test
    void testStartInTheMiddle(){
        List<TileIndex> tileIndexList = new ArrayList<>(Arrays.asList(new TileIndex(2, 1), new TileIndex(1, 1), new TileIndex(1, 0), new TileIndex(0, 0)));
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new EndTile(15), new PathTile(), new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new EmptyTile(), new PathTile(), new StartTile(Defaults.getDefaultGenerator()), new PathTile()));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new PathTile(), new PathTile(), new PathTile(), new EmptyTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        Pathway pathway = PathwayFactory.produce(tiles);

        assertEquals(tileIndexList, pathway.getTiles());
    }
    @Test
    void testEndFromBelow(){
        List<TileIndex> tileIndexList = new ArrayList<>(Arrays.asList(new TileIndex(2, 1), new TileIndex(1, 1), new TileIndex(0, 1), new TileIndex(0,0)));
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new EndTile(15), new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new PathTile(), new PathTile(), new StartTile(Defaults.getDefaultGenerator())));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new EmptyTile(), new PathTile(), new EmptyTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        Pathway pathway = PathwayFactory.produce(tiles);

        assertEquals(tileIndexList, pathway.getTiles());
    }
    @Test
    void testEndFromAbove(){
        List<TileIndex> tileIndexList = new ArrayList<>(Arrays.asList(new TileIndex(2, 1), new TileIndex(1, 1), new TileIndex(0, 1), new TileIndex(0,2)));
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new PathTile(), new PathTile(), new StartTile(Defaults.getDefaultGenerator())));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new EndTile(15), new EmptyTile(), new EmptyTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        Pathway pathway = PathwayFactory.produce(tiles);

        assertEquals(tileIndexList, pathway.getTiles());
    }
    @Test
    void getPositionTest(){
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new PathTile(), new PathTile(), new StartTile(Defaults.getDefaultGenerator())));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new EndTile(15), new EmptyTile(), new EmptyTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        Pathway pathway = PathwayFactory.produce(tiles);

        assertThrows(EndOfPathException.class, () -> pathway.getPosition(pathway.getLength()+1));
    }
    @Test
    void constructWithoutEndTest() {
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new StartTile(Defaults.getDefaultGenerator()), new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile(), new EmptyTile()));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile(), new EmptyTile()));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        assertThrows(InvalidGridStructureException.class, () -> PathwayFactory.produce(tiles));
    }
    @Test
    void constructWithoutStartTest() {
        List<Tile> row1 = new ArrayList<>(Arrays.asList(new EndTile(15), new EmptyTile(), new EmptyTile()));
        List<Tile> row2 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile(), new EmptyTile()));
        List<Tile> row3 = new ArrayList<>(Arrays.asList(new EmptyTile(), new EmptyTile(), new StartTile(Defaults.getDefaultGenerator())));
        List<List<Tile>> tiles = new ArrayList<>(Arrays.asList(row1, row2, row3));

        assertThrows(InvalidGridStructureException.class, () -> PathwayFactory.produce(tiles));
    }
}
