package model.grid.tiles;

public interface Tile {
    enum TileType {
        EMPTY, END, PATH, PLACEABLE, START
    }

    TileType getType();
}