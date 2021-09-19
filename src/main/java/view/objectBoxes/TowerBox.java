package view.objectBoxes;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.tower.TowerType;
import view.GraphicsConfig;

public class TowerBox extends ObjectBox{
    private final TowerType towerType;
    public TowerBox(TowerType towerType, double size){
        super(size);
        Image image = null;
        //noinspection EnhancedSwitchMigration
        switch (towerType){
            case BASIC:
                image = GraphicsConfig.basicTowerTexture;
                break;
            case SNIPER:
                image = GraphicsConfig.sniperTowerTexture;
                break;
            case CANNON:
                image = GraphicsConfig.cannonTowerTexture;
                break;
            case FREEZING:
                image = GraphicsConfig.freezingTowerTexture;
                break;
            case SHOTGUN:
                image = GraphicsConfig.shotgunTowerTexture;
        }
        if(image != null){
            ImagePattern imagePattern = new ImagePattern(image);
            this.rectangle.setFill(imagePattern);
        }
        this.towerType = towerType;


    }

    public VBox getvBox() {
        return super.getvBox();
    }
    public void setTooltips(int cost){
        StringBuilder bobTheBuilder = new StringBuilder();
        bobTheBuilder.append(towerType.name());
        bobTheBuilder.append("\nPrice: ");
        bobTheBuilder.append(cost);
        bobTheBuilder.append("\n");
        String description;
        //noinspection EnhancedSwitchMigration
        switch (towerType){
            case BASIC:
                description = "The most basic tower there is. Good for early game.";
                break;
            case FREEZING:
                description = "Shots freezing projectiles which slow down enemies. Careful - Ices don't care about that.";
                break;
            case CANNON:
                description = "Harms many enemies at once using explosives.";
                break;
            case SNIPER:
                description = "Shots fast and powerful bullets, but needs a rest every now and then.";
                break;
            case SHOTGUN:
                description = "Fires many pellets at once. The coolest and most badass tower there is.";
                break;
            default:
                description = "";
        }
        bobTheBuilder.append(description);
        Tooltip tooltip = new Tooltip(bobTheBuilder.toString());
        tooltip.setShowDelay(new Duration(250));
        tooltip.setFont(GraphicsConfig.fontMainGameSmall);
        Tooltip.install(rectangle, tooltip);
    }

    public TowerType getTowerType() {
        return towerType;
    }
}
