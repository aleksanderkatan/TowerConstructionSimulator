package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.grid.*;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;
import view.gameEndPopup.GameEndPopup;
import view.GamePane;
import view.sidePanelBlocks.GameManipulationPane;


public class GameExecutorImpl implements GameExecutor, Config {
    private final Grid grid;
    private final ViewManager viewManager;
    private final StackPane root;
    private final GamePane gamePane;
    private int gameSpeed = 1;
    private boolean running = false;

    public GameExecutorImpl(StackPane root, Grid grid, GamePane pane, ViewManager manager) {
        this.root = root;
        this.grid = grid;
        this.gamePane = pane;
        this.viewManager = manager;
    }

    Timeline timeline;
    @Override
    public void run() {
        viewManager.start();
        timeline = new Timeline(
                new KeyFrame(Duration.millis(1000.0/FRAME_RATE),
                        event -> step()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        setUpButtons();
        timeline.play();
        running = true;
    }

    @Override
    public void step() {
        if(running) {
            try {
                for(int i=0; i<gameSpeed; ++i)
                    grid.step();
            } catch (EndOfGameException e) {
                timeline.stop();
                viewManager.draw();
                gamePane.getPane().setDisable(true);
                GameEndPopup.show(root, grid);
                return;
            }
        }
        viewManager.draw();
    }

    private void setUpButtons() {
        GameManipulationPane gameManipulationPane = gamePane.getRightSubPane().getRightPanelInfoPane().getGameManipulationPane();
        Button speedChangeBtn = gameManipulationPane.getSpeedChangeButton();
        Button startStopBtn = gameManipulationPane.getStartStopButton();

        startStopBtn.setOnAction(e->changeState(gameManipulationPane));
        speedChangeBtn.setOnAction(e->changeSpeed(gameManipulationPane));
    }

    public void changeSpeed(GameManipulationPane gameManipulationPane) {
        gameSpeed = (gameSpeed+2)%6;
        gameManipulationPane.setSpeed(gameSpeed);
    }

    public void changeState(GameManipulationPane gameManipulationPane) {
        running = !running;
        if(running) {
            gameManipulationPane.setStop();
        } else {
            gameManipulationPane.setStart();
        }
    }
}
