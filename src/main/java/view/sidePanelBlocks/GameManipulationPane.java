package view.sidePanelBlocks;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import view.Getter;

public class GameManipulationPane {
    private final VBox vBox;
    private final HBox hBox;
    private final Button startStopButton;
    private final Button speedChangeButton;

    public GameManipulationPane(){
        this.vBox = new VBox();
        this.vBox.setAlignment(Pos.TOP_CENTER);

        this.hBox = new HBox();
        this.hBox.setAlignment(Pos.CENTER);
        this.hBox.setSpacing(5);

        this.speedChangeButton = new Button("1X");
        this.speedChangeButton.getStylesheets().add(Getter.getFile("view/mainMenu/style.css").toURI().toString());
        this.startStopButton = new Button();
        setStop();
        this.startStopButton.getStylesheets().add(Getter.getFile("view/mainMenu/style.css").toURI().toString());

        this.hBox.getChildren().addAll(startStopButton, speedChangeButton);
        this.vBox.getChildren().add(hBox);
    }

    ImageView getButtonView(String path) {
        Image image = Getter.getImage(path);
        ImageView view = new ImageView(image);
        view.setFitHeight(22);
        view.setFitWidth(22);
        return view;
    }

    public void setStart() {
        ImageView view = getButtonView("textures/arrow_right.png");
        this.startStopButton.setGraphic(view);
    }

    public void setStop() {
        ImageView view = getButtonView("textures/stop.png");
        this.startStopButton.setGraphic(view);
    }

    public void setSpeed(int speed) {
        this.speedChangeButton.setText(speed+"X");
    }

    public VBox getvBox() {
        return vBox;
    }

    public Button getSpeedChangeButton() {
        return speedChangeButton;
    }

    public Button getStartStopButton() {
        return startStopButton;
    }
}
