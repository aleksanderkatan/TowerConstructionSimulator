package controller;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import model.grid.moneyManager.MoneyManager;
import model.grid.pathway.TileIndex;
import model.grid.tiles.Tile;
import model.tower.Tower;
import model.tower.TowerFactory;
import model.tower.TowerType;
import view.TilePane;
import view.TowerPane;
import view.containers.CurrentTowerTypeContainer;
import view.containers.CurrentlyFocusedTowerContainer;
import view.containers.GhostTowerObjectContainer;
import view.customObjects.LoopingChooser;
import view.gameObjects.GhostTowerObject;
import view.sidePanelBlocks.FocusedTowerPane;


import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

public class MouseActionManager {
    public static void onMouseMovedCanvas(MouseEvent event, List<List<TilePane>> tilePanes,
                                          GhostTowerObjectContainer ghostTowerObjectContainer,
                                          TowerType currentTowerType){
        if(currentTowerType != null){
            int x = (int) event.getX() / (int) Config.TILE_SIZE;
            int y = (int) event.getY() / (int) Config.TILE_SIZE;
            if(tilePanes.get(y).get(x).isAvailable()
                    && (ghostTowerObjectContainer.ghostTowerObject == null
                    || !((int)ghostTowerObjectContainer.ghostTowerObject.getPosition().getX() == x
                    && (int)ghostTowerObjectContainer.ghostTowerObject.getPosition().getY() == y))){
                ghostTowerObjectContainer.ghostTowerObject =
                        new GhostTowerObject(currentTowerType, x, y, TowerFactory.getBaseTowerRange(currentTowerType));
            }
        }
    }
    public static void onMouseExitedCanvas(GhostTowerObjectContainer ghostTowerObjectContainer){
        ghostTowerObjectContainer.ghostTowerObject = null;
    }
    public static void onMouseClickLoopingChooser(LoopingChooser loopingChooser,
                                                  CurrentlyFocusedTowerContainer currentlyFocusedTowerContainer,
                                                  boolean isLeft){
        if(isLeft) {
            currentlyFocusedTowerContainer.currentlyFocusedTower.getTargetingManager().previousTargeting();
        }
        else {
            currentlyFocusedTowerContainer.currentlyFocusedTower.getTargetingManager().nextTargeting();
        }
        loopingChooser.changeChoice(currentlyFocusedTowerContainer.currentlyFocusedTower.getTargetingManager().getTargetingType().name());
    }
    public static void onMouseClickedCanvas(MouseEvent event, List<List<TilePane>> tilePanes,
                                            List<TowerPane> towerPanes,
                                            FocusedTowerPane focusedTowerPane,
                                            CurrentTowerTypeContainer currentTowerTypeContainer,
                                            CurrentlyFocusedTowerContainer currentlyFocusedTowerContainer,
                                            GhostTowerObjectContainer ghostTowerObjectContainer,
                                            MoneyManager moneyManager,
                                            Function<TileIndex, Tower> findTowerByTileIndexGridFunction,
                                            BiFunction<Integer, Integer, Tile> getTileGridFunction,
                                            Consumer<Tower> upgradeTowerGridFunction,
                                            BiConsumer<TileIndex, TowerType> placeTowerGridFunction){
        int x = (int) event.getX() / (int) Config.TILE_SIZE;
        int y = (int) event.getY() / (int) Config.TILE_SIZE;
        if(tilePanes.get(y).get(x).isOccupied()){
            ghostTowerObjectContainer.ghostTowerObject = null;
            currentTowerTypeContainer.currentTowerType = null;
            for(TowerPane otherTowerPane: towerPanes){
                    otherTowerPane.setClicked(false);
            }

            TileIndex tileIndex = new TileIndex(x, y);
            currentlyFocusedTowerContainer.currentlyFocusedTower = findTowerByTileIndexGridFunction.apply(tileIndex);
            Tower tower = currentlyFocusedTowerContainer.currentlyFocusedTower;

            focusedTowerPane.setFocusedTower();

            focusedTowerPane.disableUpgradeButton(!moneyManager.canIUpgradeTower(tower));
            focusedTowerPane.getUpgradeButton().setVisible(!tower.isMaxLevel());
            focusedTowerPane.getUpgradeCostInfoPane().setVisible(!tower.isMaxLevel());

            focusedTowerPane.setLevel(tower.getLevel());
            focusedTowerPane.setUpgradeCost(MoneyManager.getUpgradeCost(tower));
            focusedTowerPane.setLabel(tower.getTowerType().name());
            focusedTowerPane.getLoopingChooser().changeChoice(tower.getTargetingManager().getTargetingType().name());
            focusedTowerPane.clearStats();
            focusedTowerPane.setStats(tower.getCurrentStats());

            focusedTowerPane.getUpgradeButton().setOnMouseClicked((MouseEvent event2) ->
                    onClickUpgradeButton(upgradeTowerGridFunction, focusedTowerPane,
                            currentlyFocusedTowerContainer, moneyManager));
            focusedTowerPane.getUpgradeButton().setOnMouseEntered((MouseEvent event2) ->
                    onEnteredUpgradeButton(focusedTowerPane));
            focusedTowerPane.getUpgradeButton().setOnMouseExited((MouseEvent event2) ->
                    onExitedUpgradeButton(focusedTowerPane));
        }
        else if(tilePanes.get(y).get(x).isAvailable() && currentTowerTypeContainer.currentTowerType != null){
            ghostTowerObjectContainer.ghostTowerObject = null;
            if(event.getButton() == MouseButton.PRIMARY){
                currentlyFocusedTowerContainer.currentlyFocusedTower = null;
                focusedTowerPane.setVisible(false);
                placeTowerGridFunction.accept(new TileIndex(x, y), currentTowerTypeContainer.currentTowerType);
                tilePanes.get(y).get(x).setOccupied(true);
                tilePanes.get(y).get(x).setAvailable(false);
                for(TowerPane towerPane: towerPanes){
                    towerPane.setClicked(false);
                    towerPane.setAvailable(moneyManager.canIBuyTower(towerPane.getTowerType()));
                }

            }
            else{
                for(TowerPane towerPane: towerPanes){
                    towerPane.setClicked(false);
                }
            }
            for(int i = 0; i < tilePanes.size(); ++i){
                for(int j = 0; j < tilePanes.get(i).size(); ++j){
                    if(getTileGridFunction.apply(j, i).getType() == Tile.TileType.PLACEABLE){
                        tilePanes.get(i).get(j).setAvailable(false);
                    }
                }
            }
        }
        else{
            currentlyFocusedTowerContainer.currentlyFocusedTower = null;
            focusedTowerPane.setVisible(false);
        }
    }
    public static void onClickTowerPane(List<List<TilePane>> tilePanes, List<TowerPane> towerPanes,
                                        List<Tower> towers, TowerPane currentTowerPane,
                                        CurrentTowerTypeContainer currentTowerTypeContainer,
                                        CurrentlyFocusedTowerContainer currentlyFocusedTowerContainer,
                                        GhostTowerObjectContainer ghostTowerObjectContainer,
                                        FocusedTowerPane focusedTowerPane,
                                        BiFunction<Integer, Integer, Tile> getTileGridFunction){
        if(currentTowerPane.isAvailable()){
            focusedTowerPane.setVisible(false);
            currentlyFocusedTowerContainer.currentlyFocusedTower = null;
            currentTowerPane.setClicked(!currentTowerPane.isClicked());
            for(TowerPane otherTowerPane: towerPanes){
                if(currentTowerPane != otherTowerPane){
                    otherTowerPane.setClicked(false);
                }
            }

            if(currentTowerPane.isClicked()){
                currentTowerTypeContainer.currentTowerType = currentTowerPane.getTowerType();

                for(int y = 0; y < tilePanes.size(); ++y){
                    for(int x = 0; x < tilePanes.get(y).size(); ++x){
                        if(getTileGridFunction.apply(x, y).getType() == Tile.TileType.PLACEABLE){
                            tilePanes.get(y).get(x).setAvailable(true);
                        }
                    }
                }
                for(Tower tower : towers){
                    int x = tower.getTileIndex().getX();
                    int y = tower.getTileIndex().getY();
                    tilePanes.get(y).get(x).setAvailable(false);
                }
            }
            else{
                currentTowerTypeContainer.currentTowerType = null;
                ghostTowerObjectContainer.ghostTowerObject = null;
            }
        }

    }
    private static void onClickUpgradeButton(Consumer<Tower> upgradeTowerGridFunction,
                                             FocusedTowerPane focusedTowerPane,
                                             CurrentlyFocusedTowerContainer currentlyFocusedTowerContainer,
                                             MoneyManager moneyManager){
        Tower tower = currentlyFocusedTowerContainer.currentlyFocusedTower;
        upgradeTowerGridFunction.accept(tower);
        focusedTowerPane.setLevel(tower.getLevel());
        focusedTowerPane.disableUpgradeButton(!moneyManager.canIUpgradeTower(tower));
        focusedTowerPane.getUpgradeButton().setVisible(!tower.isMaxLevel());
        focusedTowerPane.getUpgradeCostInfoPane().setVisible(!tower.isMaxLevel());
        focusedTowerPane.getUpgradeCostInfoPane().setCost(MoneyManager.getUpgradeCost(tower));
    }
    private static void onEnteredUpgradeButton(FocusedTowerPane focusedTowerPane) {
        focusedTowerPane.setUpgradedMode(true);
    }
    private static void onExitedUpgradeButton(FocusedTowerPane focusedTowerPane) {
        focusedTowerPane.setUpgradedMode(false);
    }
}
