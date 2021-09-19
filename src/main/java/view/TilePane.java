package view;

import controller.Config;
import javafx.scene.layout.*;
import model.grid.tiles.Tile;

public class TilePane {
    private final StackPane pane;
    private final Background backgroundNormal;
    private final Background backgroundAvailable;
    private boolean available;
    private boolean occupied;

    public TilePane(Background background, Background background_light){
        this.pane = new StackPane();
        this.pane.setPrefSize(Config.TILE_SIZE, Config.TILE_SIZE);
        this.backgroundNormal = background;
        this.backgroundAvailable = background_light;
        this.pane.setBackground(backgroundNormal);
        this.available = false;
        this.occupied = false;
    }
    public void draw(Tile tile){}

    public StackPane getPane() {
        return pane;
    }
    public void setAvailable(boolean available){
        this.available = available;
        if(available){
            this.pane.setBackground(backgroundAvailable);
        }else {
            this.pane.setBackground(backgroundNormal);
        }
    }

    public boolean isAvailable() {
        return available;
    }
    public boolean isOccupied() {
        return occupied;
    }
    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }
}
