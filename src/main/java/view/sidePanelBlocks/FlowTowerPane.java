package view.sidePanelBlocks;

import javafx.geometry.Pos;
import javafx.scene.layout.FlowPane;
import model.tower.TowerType;
import view.TowerPane;

import java.util.ArrayList;
import java.util.List;

public class FlowTowerPane {
    private final FlowPane pane;
    private final List<TowerPane> towerPanes;
    public FlowTowerPane(){
        this.pane = new FlowPane();
        this.pane.setHgap(10);
        this.pane.setAlignment(Pos.CENTER);
        this.towerPanes = new ArrayList<>();
        for(TowerType type : TowerType.values()){
            TowerPane towerPane = new TowerPane(type);
            this.towerPanes.add(towerPane);
            this.pane.getChildren().add(towerPane.getStackPane());
        }
    }

    public FlowPane getPane() {
        return pane;
    }
    public List<TowerPane> getTowerPanes() {
        return towerPanes;
    }
}
