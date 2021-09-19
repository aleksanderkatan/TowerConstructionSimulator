package view;

import controller.Config;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import model.tower.TowerType;
import view.objectBoxes.TowerBox;
import view.sidePanelBlocks.RectangularSidePanelBox;

public class TowerPane {
    private final StackPane stackPane;
    private final RectangularSidePanelBox rectangularSidePanelBox;
    private final TowerBox towerBox;

    private final Color normalColor;
    private final Color selectedColor;
    private final Color notAvailableColor;

    private boolean clicked;
    private boolean available;

    public TowerPane(TowerType towerType){
        this.stackPane = new StackPane();
        this.stackPane.setPrefSize(Config.TILE_SIZE, Config.TILE_SIZE);

        this.towerBox = new TowerBox(towerType, Config.TOWER_WIDTH);
        this.rectangularSidePanelBox = new RectangularSidePanelBox(towerBox.getvBox());
        this.rectangularSidePanelBox.prepare(Config.TILE_SIZE, Config.TILE_SIZE, 1);
        this.rectangularSidePanelBox.modifyArcSize(rectangularSidePanelBox.getvBox().getMaxWidth()/4);

        normalColor = GraphicsConfig.startTileColor;
        selectedColor = normalColor.brighter();
        notAvailableColor = normalColor.darker().darker().darker();

        this.clicked = false;
        this.available = true;

        this.stackPane.setAlignment(Pos.CENTER);
        this.stackPane.getChildren().add(rectangularSidePanelBox.getStackPane());
    }

    public StackPane getStackPane(){return stackPane;}
    public boolean isClicked() {
        return clicked;
    }
    public void setClicked(boolean clicked) {
        if(clicked){
            this.rectangularSidePanelBox.changeColor(selectedColor);
        }else {
            this.rectangularSidePanelBox.changeColor(normalColor);
        }
        this.clicked = clicked;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available){
        if(available){
            if(clicked){
                this.rectangularSidePanelBox.changeColor(selectedColor);
            }else {
                this.rectangularSidePanelBox.changeColor(normalColor);
            }

        }
        else{
            this.rectangularSidePanelBox.changeColor(notAvailableColor);
        }
        this.available = available;
    }

    public TowerType getTowerType() {
        return towerBox.getTowerType();
    }
    public TowerBox getTowerBox(){
        return towerBox;
    }

}
