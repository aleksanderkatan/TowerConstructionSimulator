package model.grid;

import model.grid.pathway.InvalidGridStructureException;
import model.grid.tiles.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GridFactoryTest {
    @Test
    void testEmpty(){
        String gridString = "";
        List<List<Tile>> tiles = new ArrayList<>();


        assertThrows(InvalidGridStructureException.class, () -> GridFactory.produce(gridString, Defaults.getDefaultGenerator()));
    }

    @Test
    void testSquare(){
        String gridString = "MM\nSE\n";

        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        assertInstanceOf(EmptyTile.class, grid.getTiles().get(0).get(0));
        assertInstanceOf(EmptyTile.class, grid.getTiles().get(0).get(1));
        assertInstanceOf(StartTile.class, grid.getTiles().get(1).get(0));
        assertInstanceOf(EndTile.class, grid.getTiles().get(1).get(1));
    }

    @Test
    void testRectangle(){
        String gridString = "ML\nSP\nLE\n";

        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        assertInstanceOf(EmptyTile.class, grid.getTiles().get(0).get(0));
        assertInstanceOf(PlaceableTile.class, grid.getTiles().get(0).get(1));
        assertInstanceOf(StartTile.class, grid.getTiles().get(1).get(0));
        assertInstanceOf(PathTile.class, grid.getTiles().get(1).get(1));
        assertInstanceOf(PlaceableTile.class, grid.getTiles().get(2).get(0));
        assertInstanceOf(EndTile.class, grid.getTiles().get(2).get(1));

    }

    @Test
    void testNoEndline(){
        String gridString = "MM\nSE";

        Grid grid = GridFactory.produce(gridString, Defaults.getDefaultGenerator());

        assertInstanceOf(EmptyTile.class, grid.getTiles().get(0).get(0));
        assertInstanceOf(EmptyTile.class, grid.getTiles().get(0).get(1));
        assertInstanceOf(StartTile.class, grid.getTiles().get(1).get(0));
        assertInstanceOf(EndTile.class, grid.getTiles().get(1).get(1));

    }

    @Test
    void testUnsupportedTile(){
        String gridString = "ML\nS*\nLE\n";

        assertThrows(UnsupportedGridStringException.class, ()-> GridFactory.produce(gridString, Defaults.getDefaultGenerator()));
    }

    @Test
    void testNonRectangular() {
        String gridString = "ML\nPS\nE";

        assertThrows(UnsupportedGridStringException.class, ()-> GridFactory.produce(gridString, Defaults.getDefaultGenerator()));
    }
}
