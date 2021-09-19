package model.grid.tiles;

public class EmptyTile implements Tile {

    @Override
    public TileType getType() {
        return TileType.EMPTY;
    }
}
