package view.sidePanelBlocks;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import model.tower.TowerStats;
import view.Getter;
import view.GraphicsConfig;
import view.textValuePane.StatsInfoPane;
import view.textValuePane.TowerLevelInfoPane;
import view.textValuePane.UpgradeCostInfoPane;
import view.customObjects.LoopingChooser;

import java.util.ArrayList;
import java.util.List;

public class FocusedTowerPane {
    private final VBox vBox;
    private final TowerLevelInfoPane towerLevelInfoPane;
    private final UpgradeCostInfoPane upgradeCostInfoPane;
    private final Button upgradeButton;
    private final Label towerTypeLabel;
    private final LoopingChooser loopingChooser;
    private final VBox statsVBox;
    private boolean visible;
    private boolean upgradedMode = false;

    public FocusedTowerPane(){

        this.vBox = new VBox();
        this.vBox.setAlignment(Pos.TOP_CENTER);
        this.vBox.setSpacing(6);
        this.vBox.setVisible(false);
        this.vBox.setPadding(new Insets(15,5,0,5));
        this.statsVBox = new VBox();
        this.statsVBox.setAlignment(Pos.TOP_CENTER);
        this.statsVBox.setSpacing(6);

        this.towerLevelInfoPane = new TowerLevelInfoPane();

        this.upgradeCostInfoPane = new UpgradeCostInfoPane();

        this.upgradeButton = new Button("UPGRADE");
        this.upgradeButton.setFont(GraphicsConfig.fontMainGame);
        this.upgradeButton.setDisable(true);
        this.upgradeButton.setBackground(new Background(
                new BackgroundFill(GraphicsConfig.endTileColor, null, null)));
        this.upgradeButton.getStylesheets().add(Getter.getFile("view/mainMenu/style.css").toURI().toString());

        this.loopingChooser = new LoopingChooser();

        this.towerTypeLabel = new Label();
        this.towerTypeLabel.setAlignment(Pos.CENTER);
        this.towerTypeLabel.setFont(GraphicsConfig.fontMainGameBig);

        this.vBox.getChildren().addAll(towerTypeLabel, towerLevelInfoPane.gethBox(),
                 loopingChooser.gethBox(), upgradeCostInfoPane.gethBox(), upgradeButton, statsVBox);
        visible = false;
    }
    public void clearStats(){
        statsVBox.getChildren().clear();
    }
    public void setStats(TowerStats stats){
        clearStats();

        List<StatsInfoPane> statsPanes = new ArrayList<>();
        StatsInfoPane pane;

        pane = new StatsInfoPane("Damage");
        pane.setStatValue(String.valueOf((int)stats.damage));
        statsPanes.add(pane);

        pane = new StatsInfoPane("Fire-rate");
        pane.setStatValue(String.format("%.2f/s", stats.fireRate));
        statsPanes.add(pane);

        pane = new StatsInfoPane("Range");
        pane.setStatValue(String.format("%.2f", stats.range));
        statsPanes.add(pane);

        pane = new StatsInfoPane("Velocity");
        pane.setStatValue(String.format("%.1f", stats.projectileVelocity));
        statsPanes.add(pane);


        switch (stats.type) {
            case SNIPER:
            case BASIC:
                break;
            case CANNON:
                pane = new StatsInfoPane("Expl.range");
                pane.setStatValue(String.format("%.2f", stats.explosionRange));
                statsPanes.add(pane);
                break;
            case FREEZING:
                pane = new StatsInfoPane("Freezing");
                pane.setStatValue((int) (stats.freezingPower * 100) + "%");
                statsPanes.add(pane);
                break;
            case SHOTGUN:
                pane = new StatsInfoPane("Projectiles");
                pane.setStatValue(String.valueOf(stats.projectileAmount));
                statsPanes.add(pane);

                pane = new StatsInfoPane("Offset");
                pane.setStatValue((int) (stats.projectileOffset * 100) + "%");
                statsPanes.add(pane);
                break;
        }

        for (var p : statsPanes) {
            if (upgradedMode)
                p.getValueLabel().setTextFill(GraphicsConfig.upgradeBonusTextColor);
            this.statsVBox.getChildren().add(p.gethBox());
        }

    }

    public void updateStats(TowerStats stats){
        setStats(stats);
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
        this.vBox.setVisible(visible);
    }

    public void setUpgradedMode(boolean value) {
        this.upgradedMode = value;
    }
    public boolean isUpgradedMode() {
        return this.upgradedMode;
    }

    public void setFocusedTower(){
        this.vBox.setVisible(true);
        visible = true;
    }

    public boolean isVisible() {
        return visible;
    }

    public LoopingChooser getLoopingChooser() {
        return loopingChooser;
    }
    public VBox getvBox() {
        return vBox;
    }
    public Button getUpgradeButton() {
        return upgradeButton;
    }
    public void setLevel(int level){
        towerLevelInfoPane.setLevel(level);
    }
    public void setUpgradeCost(int cost){
        upgradeCostInfoPane.setCost(cost);
    }
    public void setLabel(String value){
        this.towerTypeLabel.setText(value);
    }
    public void disableUpgradeButton(boolean disable){
        upgradeButton.setDisable(disable);
    }
    public String getTowerTypeLabelText(){
        return towerTypeLabel.getText();
    }
    public int getLevel(){
        return towerLevelInfoPane.getLevel();
    }

    public UpgradeCostInfoPane getUpgradeCostInfoPane() {
        return upgradeCostInfoPane;
    }

}
