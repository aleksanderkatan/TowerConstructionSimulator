package controller;

import javafx.scene.layout.StackPane;
import model.grid.Grid;
import model.grid.GridFactory;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import view.GamePane;

public class GameExecutorFactoryImpl implements GameExecutorFactory {
    private final static String resourcesPath = System.getProperty("user.dir") + "/src/main/resources/";

    @Override
    public GameExecutor produce(StackPane root, String levelName, String generatorName, FileStringifier stringifier) {
        String stringifiedGenerator = stringifier.stringifyFile(
                resourcesPath + "enemy_generators/" + generatorName);
        EnemyGenerator generator = new EnemyGeneratorFactory().produce(stringifiedGenerator);
        String stringifiedLevel = stringifier.stringifyFile(
                resourcesPath + "game_levels/" + levelName);
        Grid grid = GridFactory.produce(stringifiedLevel, generator);
        GamePane pane = new GamePane(Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);

        root.getChildren().add(pane.getPane());
        ViewManager manager = new ViewManager(pane, grid);

        return new GameExecutorImpl(root, grid, pane, manager);
    }
}
