package controller;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.enemy.Enemy;
import model.grid.tiles.StartTile;
import model.projectile.Projectile;
import model.grid.moneyManager.MoneyManager;
import model.projectile.Explosion;
import model.tower.Tower;
import model.grid.pathway.EndOfPathException;
import model.grid.Grid;
import model.tower.*;
import view.GraphicsConfig;
import view.GamePane;
import view.TowerPane;
import view.containers.CurrentTowerTypeContainer;
import view.containers.CurrentlyFocusedTowerContainer;
import view.containers.GhostTowerObjectContainer;
import view.gameObjects.*;
import view.sidePanelBlocks.FocusedTowerPane;

import java.util.List;

public class ViewManager {
    private final GamePane gamePane;
    private final Grid grid;
    private int upcomingWave;
    private final CurrentTowerTypeContainer currentTowerTypeContainer;
    private final CurrentlyFocusedTowerContainer currentlyFocusedTowerContainer;
    private final GhostTowerObjectContainer ghostTowerObjectContainer;
    public ViewManager(GamePane gamePane, Grid grid){
        this.gamePane = gamePane;
        this.grid = grid;
        this.currentlyFocusedTowerContainer = new CurrentlyFocusedTowerContainer();
        this.ghostTowerObjectContainer = new GhostTowerObjectContainer();
        this.currentTowerTypeContainer = new CurrentTowerTypeContainer();
    }
    public void start(){
        gamePane.getGridTilePane().setTilePanes(grid.getTiles());
        gamePane.getCanvasPane().setSize(gamePane.getGridTilePane().getGridPane().getMaxWidth(),
                gamePane.getGridTilePane().getGridPane().getMaxHeight());

        registerTooltips();
        registerMouseAction();
        updateHP();
        updateGold();

        gamePane.getPlayInfoPane().setBoxColors(GraphicsConfig.pathTileColor);
        gamePane.getPlayInfoPane().setBoxSizes(gamePane.getLeftSubPane().getPrefWidth(), 30);
        gamePane.getPlayInfoPane().setFocusedTowerPaneSize(gamePane.getLeftSubPane().getPrefWidth());
        gamePane.getRightSubPane().getRightPanelInfoPane().setUpcomingWavePaneSize(
                gamePane.getRightSubPane().getPrefWidth(), gamePane.getRightSubPane().getPrefWidth()*3.2);
        upcomingWave = 0;
    }
    private void registerMouseAction(){
        gamePane.getCanvasPane().getCanvas().setOnMouseMoved((MouseEvent event) ->
                MouseActionManager.onMouseMovedCanvas(event, gamePane.getGridTilePane().getTilePanes(),
                        ghostTowerObjectContainer, currentTowerTypeContainer.currentTowerType));
        gamePane.getCanvasPane().getCanvas().setOnMouseExited((MouseEvent event) ->
                MouseActionManager.onMouseExitedCanvas(ghostTowerObjectContainer));
        gamePane.getPlayInfoPane().getFocusedTowerPane().getLoopingChooser().getLeftButton().setOnMouseClicked(
                (MouseEvent event) ->
                        MouseActionManager.onMouseClickLoopingChooser(
                                gamePane.getPlayInfoPane().getFocusedTowerPane().getLoopingChooser(),
                                currentlyFocusedTowerContainer, true));
        gamePane.getPlayInfoPane().getFocusedTowerPane().getLoopingChooser().getRightButton().setOnMouseClicked(
                (MouseEvent event) ->
                        MouseActionManager.onMouseClickLoopingChooser(
                                gamePane.getPlayInfoPane().getFocusedTowerPane().getLoopingChooser(),
                                currentlyFocusedTowerContainer, false));
        gamePane.getCanvasPane().getCanvas().setOnMouseClicked((MouseEvent event) ->
                MouseActionManager.onMouseClickedCanvas(event, gamePane.getGridTilePane().getTilePanes(),
                        gamePane.getFlowTowerPane().getTowerPanes(), gamePane.getPlayInfoPane().getFocusedTowerPane(),
                        currentTowerTypeContainer, currentlyFocusedTowerContainer, ghostTowerObjectContainer,
                        grid.getMoneyManager(), grid::findTowerByTileIndex, grid::getTile, grid::upgradeTower,
                        grid::placeTower));
        for(TowerPane towerPane: gamePane.getFlowTowerPane().getTowerPanes()){
            towerPane.getStackPane().setOnMouseClicked((MouseEvent event) ->
                    MouseActionManager.onClickTowerPane(gamePane.getGridTilePane().getTilePanes(),
                            gamePane.getFlowTowerPane().getTowerPanes(), grid.getTowers(), towerPane,
                            currentTowerTypeContainer, currentlyFocusedTowerContainer, ghostTowerObjectContainer,
                            gamePane.getPlayInfoPane().getFocusedTowerPane(), grid::getTile));
        }
    }
    public void registerTooltips(){
        List<TowerPane> towerPanes = gamePane.getFlowTowerPane().getTowerPanes();
        for (TowerPane towerPane : towerPanes){
            towerPane.getTowerBox().setTooltips(MoneyManager.getTowerCost(towerPane.getTowerType()));
        }
    }
    public void updateGold(){
        int gold = grid.getMoneyManager().getGold();
        gamePane.getPlayInfoPane().setGold(gold);
    }
    public void updateHP(){
        int remainingHP = grid.getPathway().getEndTile().getRemainingHP();
        gamePane.getPlayInfoPane().setHP(remainingHP);
    }
    public void updateAvailableTowers(){
        List<TowerPane> towerPanes = gamePane.getFlowTowerPane().getTowerPanes();
        for(TowerPane towerPane : towerPanes){
            towerPane.setAvailable(grid.getMoneyManager().canIBuyTower(towerPane.getTowerType()));
        }
    }
    public void updateUpgradeIfVisible(){
        if(gamePane.getPlayInfoPane().getFocusedTowerPane().isVisible()){
            FocusedTowerPane focusedTowerPane = gamePane.getPlayInfoPane().getFocusedTowerPane();
            if(focusedTowerPane.isUpgradedMode())
                focusedTowerPane.updateStats(currentlyFocusedTowerContainer.currentlyFocusedTower.getUpgradedStats());
            else
                focusedTowerPane.updateStats(currentlyFocusedTowerContainer.currentlyFocusedTower.getCurrentStats());

            if(currentlyFocusedTowerContainer.currentlyFocusedTower.isMaxLevel()){
                return;
            }
            focusedTowerPane.getUpgradeButton().setDisable(!grid.getMoneyManager().canIUpgradeTower(
                    TowerType.valueOf(focusedTowerPane.getTowerTypeLabelText()), focusedTowerPane.getLevel()));
        }
    }
    public void draw(){
        updateGold();
        updateAvailableTowers();
        updateUpgradeIfVisible();
        updateHP();

        GraphicsContext graphicsContext = gamePane.getCanvasPane().getGraphicsContext();
        graphicsContext.clearRect(0, 0, Config.SCREEN_WIDTH, Config.SCREEN_HEIGHT);
        graphicsContext.setFill(Color.BLACK);

        EnemyLoader.draw(graphicsContext, grid.getCurrentCoolDown(), grid.getPathway().getTiles().get(0).getX(),
                grid.getPathway().getTiles().get(0).getY());

        if(ghostTowerObjectContainer.ghostTowerObject != null){
            ghostTowerObjectContainer.ghostTowerObject.draw(graphicsContext);
        }

        StartTile start = grid.getPathway().getStartTile();
        var pane = gamePane.getRightSubPane().getRightPanelInfoPane().getUpcomingWavePane();
        if(upcomingWave != start.getNextWaveIndex()) {
            upcomingWave = start.getNextWaveIndex();
            if (start.getTotalWavesAmount() < upcomingWave) {
                pane.setWaveNumber("");
                pane.setLabel("ALL WAVES\nDEPLOYED");
            } else {
                pane.setWaveNumber(upcomingWave + "/" + grid.getPathway().getStartTile().getWaveTypes().size());
            }
            pane.update(start.getWaveTypes(), upcomingWave);
        }

        List<Tower> towers = grid.getTowers();
        for(Tower tower : towers){
            double x = tower.getTileIndex().getX();
            double y = tower.getTileIndex().getY();

            if(tower == currentlyFocusedTowerContainer.currentlyFocusedTower){
                continue;
            }

            TowerObject.draw(graphicsContext, x, y, tower);
        }

        if (currentlyFocusedTowerContainer.currentlyFocusedTower != null) {
            Tower tower = currentlyFocusedTowerContainer.currentlyFocusedTower;
            double x = tower.getTileIndex().getX();
            double y = tower.getTileIndex().getY();
            RangeObject.draw(graphicsContext, x, y, tower.getRange());
            TowerObject.draw(graphicsContext, x, y, tower);
        }


        List<Enemy> enemies = grid.getEnemiesList();
        for (Enemy enemy : enemies) {
            try{
                double x = grid.getPathway().getPosition(enemy.getDistance()).getX();
                double y = grid.getPathway().getPosition(enemy.getDistance()).getY();
                EnemyObject.draw(graphicsContext, x, y, enemy);
            }catch (EndOfPathException ignored){
            }

        }

        List<Projectile> projectiles = grid.getProjectiles();
        for (Projectile projectile: projectiles){
            double x = projectile.getPosition().getX();
            double y = projectile.getPosition().getY();
            ProjectileObject.draw(graphicsContext, x, y, projectile.getType());
        }

        List<Explosion> explosions = grid.getExplosions();
        for(Explosion explosion: explosions){
            double x = explosion.getPoint().getX();
            double y = explosion.getPoint().getY();
            double ticksToEnd = explosion.getTicksToEnd();
            double explosionMaxRange = explosion.getExplosionMaxRange();
            ExplosionObject.draw(graphicsContext, x, y, 1 - ticksToEnd/Config.EXPLOSION_TOTAL_TICKS,
                    explosionMaxRange);
        }
    }

}
