package view.gameEndPopup;

import controller.Program;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import model.grid.Grid;
import view.Getter;
import view.sidePanelBlocks.RectangularSidePanelBox;

public class GameEndPopup {
    public static void show(StackPane gamePane, Grid grid) {
        Label title = new Label("Congratulations!");
        title.setStyle("-fx-font-size: 40; -fx-text-fill: black;");
        VBox vbox = new VBox(title);

        String information;
        if(grid.getPathway().getEndTile().getRemainingHP() > 0) {
            information = "You survived all the waves";
        } else {
            information = "You survived to the wave " + (grid.getPathway().getStartTile().getWaveIndex());
        }
        Label label = new Label(information);
        label.setStyle("-fx-font-size: 20; -fx-text-fill: black;");
        vbox.getChildren().add(label);

        Button button = new Button("Go Back");
        button.setOnAction(event -> Program.start());

        vbox.getChildren().add(button);
        vbox.setSpacing(30);
        vbox.setAlignment(Pos.CENTER);
        RectangularSidePanelBox box = new RectangularSidePanelBox(vbox);
        box.prepare(420, 250, 1);
        box.getStackPane().getStylesheets().add(Getter.getFile("view/mainMenu/style.css").toURI().toString());
        gamePane.getChildren().add(box.getStackPane());
    }
}
