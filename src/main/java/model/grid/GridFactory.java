package model.grid;

import controller.Config;
import model.enemy.EnemyFactory;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import model.grid.moneyManager.MoneyManager;
import model.grid.tiles.*;
import model.tower.TowerFactory;

import java.util.ArrayList;
import java.util.List;

public class GridFactory {
    public static Grid produce(String gridString, EnemyGenerator generator) throws UnsupportedGridStringException {
        List<List<Tile>> fields = new ArrayList<>();
        List<Tile> row = new ArrayList<>();
        for(int i = 0; i < gridString.length(); ++i){
            Tile tile;
            //noinspection EnhancedSwitchMigration
            switch (gridString.charAt(i)){
                case 'S': //start
                    tile = new StartTile(generator);
                    row.add(tile);
                    break;
                case 'E': //end
                    tile = new EndTile(Config.STARTING_HEALTH);
                    row.add(tile);
                    break;
                case 'P': //path
                    tile = new PathTile();
                    row.add(tile);
                    break;
                case 'L': //placeable
                    tile = new PlaceableTile();
                    row.add(tile);
                    break;
                case 'M': //empty
                    tile = new EmptyTile();
                    row.add(tile);
                    break;
                case '\n':
                    fields.add(row);
                    row = new ArrayList<>();
                    break;
                default:
                    throw new UnsupportedGridStringException();
            }
        }
        if(row.size() > 0) {
            fields.add(row);
        }

        for(List<Tile> tileRow: fields){
            if(tileRow.size() != fields.get(0).size()){
                throw new UnsupportedGridStringException();
            }
        }
        Grid grid = new Grid(fields, new Enemies(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                new MoneyManager(Config.STARTING_GOLD));
        grid.setPathway();
        grid.setTowerFactory(new TowerFactory(grid.getPathway(), grid.getEnemies()));
        return grid;
    }
}
/*  S - start
*   E - end
*   P - path
*   L - placeable
*   M - empty
 */
