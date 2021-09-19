package model.grid.pathway;

import controller.Config;
import model.mathematics.Geometry;
import model.mathematics.Point;
import model.grid.tiles.EndTile;
import model.grid.tiles.StartTile;

import java.util.List;

public class Pathway implements Config {
    private final List<TileIndex> tiles;
    private final StartTile startTile;
    private final EndTile endTile;

    Pathway(List<TileIndex> tiles, StartTile startTile, EndTile endTile) {
        this.tiles = tiles;
        this.startTile = startTile;
        this.endTile = endTile;
    }
    public Point getPosition(double distance) throws EndOfPathException {
        if(distance >= tiles.size()-1)
            throw new EndOfPathException();
        int last = (int)(distance);
        return Geometry.getOnSegment(tiles.get(last).getPoint(), tiles.get(last+1).getPoint(), distance - last );
    }
    public List<TileIndex> getTiles(){ return tiles; }

    public double getLength() {
        return tiles.size()-1;
    }

    public EndTile getEndTile() { return endTile; }

    public StartTile getStartTile() { return startTile; }
}
