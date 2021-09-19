package view.sidePanelBlocks;

import controller.Config;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import model.enemy.EnemyType;
import view.objectBoxes.EnemyBox;
import view.GraphicsConfig;

import java.util.List;

public class EnemyWavePane {

    private final VBox vBox;

    public EnemyWavePane(int number, List<EnemyType> enemyTypeList, double width){
        this.vBox = new VBox();
        this.vBox.setMinWidth(width);
        this.vBox.setMaxWidth(width);
        this.vBox.setAlignment(Pos.CENTER);

        FlowPane flowPane = new FlowPane();
        flowPane.setAlignment(Pos.CENTER);
        for(EnemyType enemyType: enemyTypeList){
            EnemyBox enemyBox = new EnemyBox(enemyType, Config.ENEMY_WIDTH);
            flowPane.getChildren().add(enemyBox.getvBox());
        }
        flowPane.setMaxWidth(width);
        flowPane.setPrefWrapLength(width*3/4f);

        Label numberLabel = new Label(String.valueOf(number));
        numberLabel.setAlignment(Pos.CENTER);
        numberLabel.setFont(GraphicsConfig.fontMainGame);

        this.vBox.getChildren().addAll(flowPane, numberLabel);
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(5);

    }

    public VBox getvBox() {
        return vBox;
    }
}
