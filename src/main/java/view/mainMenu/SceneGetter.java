package view.mainMenu;

import controller.Config;
import controller.GameExecutorFactoryImpl;
import controller.Program;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import controller.FileStringifierImpl;
import model.grid.Grid;
import model.grid.GridFactory;
import view.GraphicsConfig;
import view.GridTilePane;

import java.io.File;
import java.util.Calendar;
import java.util.List;

public class SceneGetter {
    private final static String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";
    public static ToggleButton getMap(String name) {
        GridTilePane gridTilePane = new GridTilePane();
        String stringifiedLevel = new FileStringifierImpl().stringifyFile(
                resourcesPath + "game_levels/"+name);
        Grid grid = GridFactory.produce(stringifiedLevel, null);
        gridTilePane.setTilePanes(grid.getTiles(), 15);

        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setGraphic(gridTilePane.getGridPane());

        return toggleButton;
    }

    public static Scene get(Stage stage) {

        Label title = new Label("Tower Construction Simulator");
        title.setStyle("-fx-font-size: 40;");
        VBox vbox = new VBox(title);
        vbox.setPadding(new Insets(50, 0, 0, 300));
        VBox.setMargin(title,  new Insets(0, 0, 50, 0));

        Label label = new Label("Choose difficulty:");
        vbox.getChildren().add(label);
        OptionsHBox difficultiesOptions = new OptionsHBox( List.of(new ToggleButton("Easy"),
                new ToggleButton("Medium"), new ToggleButton("Hard")), 1);
        HBox difficulties = difficultiesOptions.getHBox();
        vbox.getChildren().add(difficulties);
        VBox.setMargin(difficulties,  new Insets(0, 0, 20, 0));

        Label label2 = new Label("Choose map:");
        vbox.getChildren().add(label2);
        OptionsHBox mapsOptions = new OptionsHBox( List.of(getMap("level_beginner.txt"),
                getMap("level_advanced.txt"), getMap("level_expert.txt")), 1);
        HBox maps = mapsOptions.getHBox();
        vbox.getChildren().add(maps);

        Button start = new Button("Start");
        start.setOnAction(e-> {
            String levelName = null;
            //noinspection EnhancedSwitchMigration
            switch (mapsOptions.getIndex()) {
                case 0:
                    levelName = "level_beginner.txt";
                    break;
                case 1:
                    levelName = "level_advanced.txt";
                    break;
                case 2:
                    levelName = "level_expert.txt";
                    break;
            }
            String generatorName = null;
            //noinspection EnhancedSwitchMigration
            switch (difficultiesOptions.getIndex()) {
                case 0:
                    generatorName = "generator_easy.txt";
                    break;
                case 1:
                    generatorName = "generator_medium.txt";
                    break;
                case 2:
                    generatorName = "generator_hard.txt";
                    break;
            }
            Program.setGame(stage, levelName, generatorName, new GameExecutorFactoryImpl());
        });
        vbox.getChildren().add(start);
        VBox.setMargin(start,  new Insets(50, 0, 0, 0));
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.TOP_CENTER);
        Calendar calendar = Calendar.getInstance();
        boolean isDay = (calendar.get(Calendar.HOUR_OF_DAY) >= 7 && calendar.get(Calendar.HOUR_OF_DAY) < 19);
        if(isDay){
            vbox.setBackground(new Background(GraphicsConfig.background_day_main));
            label.getStyleClass().add("label_day");
            label2.getStyleClass().add("label_day");
            title.getStyleClass().add("label_day");

        }
        else{
            vbox.setBackground(new Background(GraphicsConfig.background_night_main));
        }
        Scene scene = new Scene(vbox, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        scene.getStylesheets().add(new File("src/main/resources/view/mainMenu/style.css").toURI().toString());
        return scene;
    }
}
