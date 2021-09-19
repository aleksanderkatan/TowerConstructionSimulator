package controller;

import javafx.scene.layout.StackPane;
import model.grid.Grid;
import view.GamePane;

public interface GameExecutorFactory {
    GameExecutor produce(StackPane root, String levelName, String generatorName, FileStringifier stringifier);
}
