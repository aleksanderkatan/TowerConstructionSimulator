package model.grid.pathway;

import controller.Config;
import model.mathematics.Point;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TileIndexTest {
    final double TEST_PRECISION = Config.PRECISION * 10;
    @Test
    void getPointEasyTest() {
        TileIndex tileIndex = new TileIndex(1, 1);
        Point point = tileIndex.getPoint();
        assertEquals(1.5, point.getX(), TEST_PRECISION);
        assertEquals(1.5, point.getY(), TEST_PRECISION);
    }
    @Test
    void hashCodeTest() {
        TileIndex tileIndex = new TileIndex(21, 37);
        TileIndex tileIndex2 = new TileIndex(21, 37);
        assertEquals(tileIndex, tileIndex2);
        assertEquals(tileIndex.hashCode(), tileIndex2.hashCode());
    }
}