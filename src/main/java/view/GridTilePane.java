package view;

import controller.Config;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.GridPane;
import model.grid.tiles.Tile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GridTilePane{
    private final GridPane pane;
    private final List<List<TilePane>> tilePanes;

    public GridTilePane(){
        this.pane = new GridPane();
        this.tilePanes = new ArrayList<>();
        this.pane.setBackground(new Background(
                new BackgroundFill(GraphicsConfig.placeableTileColor, null, null)));
    }

    @SuppressWarnings("all")
    public void setTilePanes(List<List<Tile>> tiles){
        for(int i = 0; i < tiles.size(); ++i){
            List<TilePane> physicalRow = new ArrayList<>();
            for(int j = 0; j < tiles.get(i).size(); j++){
                String tileName = tiles.get(i).get(j).getType().name().toLowerCase();
                File file = new File("src/main/resources/textures/" + tileName +"_tile.png");
                Background background = new Background(new BackgroundImage(new Image(file.toURI().toString(),
                        Config.TILE_SIZE, Config.TILE_SIZE, false, false),
                        null, null, null, null));
                Background background_light = null;
                if(tiles.get(i).get(j).getType() == Tile.TileType.PLACEABLE){
                    File file2 = new File("src/main/resources/textures/placeable_light_tile.png");
                    background_light =  new Background(new BackgroundImage(new Image(file2.toURI().toString(),
                            Config.TILE_SIZE, Config.TILE_SIZE, false, false),
                            null, null, null, null));
                }
                TilePane tilePane = new TilePane(background, background_light);
                physicalRow.add(tilePane);
                this.pane.add(tilePane.getPane(), j, i);
            }
            tilePanes.add(physicalRow);
        }
        this.pane.setMaxSize(tiles.get(0).size() * Config.TILE_SIZE, tiles.size() * Config.TILE_SIZE);
        this.pane.setPrefSize(tiles.get(0).size() * Config.TILE_SIZE, tiles.size() * Config.TILE_SIZE);
    }
    public void setTilePanes(List<List<Tile>> tiles, double tileSize){
        for(int i = 0; i < tiles.size(); ++i){
            List<TilePane> physicalRow = new ArrayList<>();
            for(int j = 0; j < tiles.get(i).size(); j++){
                String tileName = tiles.get(i).get(j).getType().name().toLowerCase();
                File file = new File("src/main/resources/textures/" + tileName +"_tile.png");
                Background background = new Background(new BackgroundImage(
                        new Image(file.toURI().toString(), tileSize, tileSize, false, false),
                        null, null, null, null));
                Background background_light = null;
                if(tiles.get(i).get(j).getType() == Tile.TileType.PLACEABLE){
                    File file2 = new File("src/main/resources/textures/placeable_light_tile.png");
                    background_light =  new Background(new BackgroundImage(
                            new Image(file2.toURI().toString(), tileSize, tileSize, false, false),
                            null, null, null, null));
                }
                TilePane tilePane = new TilePane(background, background_light);
                physicalRow.add(tilePane);
                this.pane.add(tilePane.getPane(), j, i);
            }
            tilePanes.add(physicalRow);
        }
        this.pane.setMaxSize(tiles.get(0).size() * tileSize, tiles.size() * tileSize);
        this.pane.setPrefSize(tiles.get(0).size() * tileSize, tiles.size() * tileSize);
    }

    public List<List<TilePane>> getTilePanes(){
        return tilePanes;
    }
    public GridPane getGridPane(){return pane;}

}
