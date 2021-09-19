package controller;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import view.Getter;

public class Program extends Application {
    static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        Program.primaryStage = primaryStage;
        primaryStage.setTitle("TowerConstructionSimulator");
        setMainMenu(primaryStage);
        primaryStage.setResizable(false);
        primaryStage.show();
        primaryStage.getIcons().add(Getter.getImage("textures/logoTCS.png"));
    }
    public static void start() {
        new Program().start(primaryStage);
    }
    public static void setMainMenu(Stage stage) {
        stage.setScene(view.mainMenu.SceneGetter.get(stage));
    }
    public static void setGame(Stage stage, String levelName, String generatorName, GameExecutorFactory factory) {
        StackPane root = new StackPane();
        Scene scene = new Scene(root, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        stage.setScene(scene);
        GameExecutor gameExecutor = factory.produce(root, levelName, generatorName, new FileStringifierImpl());

        gameExecutor.run();
    }
}
