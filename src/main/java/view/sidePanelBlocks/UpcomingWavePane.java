package view.sidePanelBlocks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.util.Pair;
import model.enemy.EnemyType;
import view.GraphicsConfig;

import java.util.List;

public class UpcomingWavePane {
    private final VBox vBox;
    private final FlowPane flowPane;
    private final Label nameLabel;
    private final Label waveNumberLabel;

    public UpcomingWavePane(){
        this.vBox = new VBox();
        this.flowPane = new FlowPane();

        VBox labelVBox = new VBox();
        labelVBox.setSpacing(5);
        labelVBox.setAlignment(Pos.TOP_CENTER);

        this.nameLabel = new Label("UPCOMING\nWAVE");
        this.nameLabel.setTextAlignment(TextAlignment.CENTER);
        this.nameLabel.setFont(GraphicsConfig.fontMainGameBig);

        this.waveNumberLabel = new Label();
        this.waveNumberLabel.setTextAlignment(TextAlignment.CENTER);
        this.waveNumberLabel.setFont(GraphicsConfig.fontMainGameMid);
        this.waveNumberLabel.setTextFill(GraphicsConfig.placeableTileColor.darker());

        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(10);
        this.vBox.setPadding(new Insets(15,10,3,10));

        this.flowPane.setAlignment(Pos.CENTER);
        this.flowPane.setVgap(10);

        labelVBox.getChildren().addAll(nameLabel, waveNumberLabel);
        this.vBox.getChildren().addAll(labelVBox, flowPane);
    }

    public VBox getvBox() {
        return vBox;
    }
    public void update(List<Pair<Integer, List<EnemyType>>> upcomingWaves, int currentWave){
        flowPane.getChildren().clear();
        for(int i = currentWave - 1; i < currentWave + 2; i++){
            if(i >= upcomingWaves.size()) break;
            Pair<Integer, List<EnemyType>> wave = upcomingWaves.get(i);
            EnemyWavePane enemyWavePane = new EnemyWavePane(wave.getKey(), wave.getValue(), this.vBox.getWidth());
            flowPane.getChildren().add(enemyWavePane.getvBox());
        }
    }
    public void setWaveNumber(String value){
        waveNumberLabel.setText(value);
    }
    public void setLabel(String value) { nameLabel.setText(value); }
}
