package model.grid.tiles;

import model.tower.Tower;

public class PlaceableTile implements Tile {
    private Tower tower;

    public void setTower(Tower tower) { this.tower = tower; }
    public Tower getTower() { return tower; }

    @Override
    public TileType getType() {
        return TileType.PLACEABLE;
    }
}
