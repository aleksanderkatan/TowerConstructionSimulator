package model.grid.pathway;

import javafx.util.Pair;
import model.grid.tiles.*;

import java.util.*;

public class PathwayFactory {
    public static Pathway produce(List<List<Tile>> tiles) throws InvalidGridStructureException {
        if(tiles == null){
            throw new InvalidGridStructureException();
        }
        List<TileIndex> pathwayTiles = new ArrayList<>();
        TileIndex startTileIndex = null;
        outer: for(int y = 0; y < tiles.size(); ++y){
            for(int x = 0; x < tiles.get(y).size(); ++x){
                if(tiles.get(y).get(x) instanceof StartTile){
                    startTileIndex = new TileIndex(x, y);
                    break outer;
                }
            }
        }
        if(startTileIndex == null){
            throw new InvalidGridStructureException();
        }

        List<List<Pair<Integer, TileIndex>>> matrix = new ArrayList<>();
        List<Pair<Integer, TileIndex>> rowFirst = new ArrayList<>();
        List<Pair<Integer, TileIndex>> rowLast = new ArrayList<>();
        for(int i = 0; i < tiles.get(0).size() + 2; ++i){
            rowFirst.add(new Pair<>(-1, null));
            rowLast.add(new Pair<>(-1, null));
        }
        matrix.add(rowFirst);
        for (List<Tile> tile : tiles) {
            List<Pair<Integer, TileIndex>> row = new ArrayList<>();
            row.add(new Pair<>(-1, null));
            for (int j = 0; j < tile.size(); ++j) {
                row.add(new Pair<>(0, null));
            }
            row.add(new Pair<>(-1, null));
            matrix.add(row);
        }
        matrix.add(rowLast);
        TileIndex endTile = null;
        EndTile endTileTile = null;
        StartTile startTileTile = (StartTile)tiles.get(startTileIndex.getY()).get(startTileIndex.getX());
        Queue<TileIndex> queue = new LinkedList<>();
        int [][] mask = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
        queue.offer(startTileIndex);
        outer: while (queue.size() > 0){
            TileIndex element = queue.poll();
            int x = element.getX();
            int y = element.getY();

            for(int i = 0; i < 4; ++i){
                if(matrix.get(y + mask[i][1] + 1).get(x + mask[i][0] + 1).getKey() == 0){
                    Tile current = tiles.get(y + mask[i][1]).get(x +  mask[i][0]);

                    if(current instanceof PathTile){
                        matrix.get(y + mask[i][1] + 1).set(x + mask[i][0] + 1 ,new Pair<>(1, element));
                        TileIndex newElement = new TileIndex(x + mask[i][0], y + mask[i][1]);
                        queue.offer(newElement);
                    }else if(current instanceof EndTile){
                        endTileTile = (EndTile)tiles.get(y + mask[i][1]).get(x + mask[i][0]);
                        endTile = new TileIndex(x + mask[i][0], y + mask[i][1]);
                        matrix.get(y + mask[i][1] + 1).set(x + mask[i][0] + 1 , new Pair<>(1, element));
                        break outer;
                    }
                }
            }
        }
        if(endTile == null){
            throw new InvalidGridStructureException();
        }
        TileIndex currentTile = endTile;
        while(currentTile != startTileIndex){
            pathwayTiles.add(currentTile);
            currentTile = matrix.get(currentTile.getY() + 1).get(currentTile.getX() + 1).getValue();
        }
        pathwayTiles.add(startTileIndex);
        Collections.reverse(pathwayTiles);
        return new Pathway(pathwayTiles, startTileTile, endTileTile);
    }
}
