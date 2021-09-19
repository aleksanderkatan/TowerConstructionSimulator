package view.objectBoxes;

import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.util.Duration;
import model.enemy.EnemyType;
import view.GraphicsConfig;

public class EnemyBox extends ObjectBox{

    public EnemyBox(EnemyType enemyType, double size){
        super(size);
        Image image = null;
        String description;
        //noinspection EnhancedSwitchMigration
        switch (enemyType){
            case REGULAR:
                image = GraphicsConfig.regularEnemyTexture;
                description = "Just another ordinary adversary.";
                break;
            case FAST:
                image = GraphicsConfig.fastEnemyTexture;
                description = "Really fast one. Catch him if you can.";
                break;
            case TANK:
                image = GraphicsConfig.tankEnemyTexture;
                description = "He's a heavy boy, but not really quick.";
                break;
            case ICE:
                image = GraphicsConfig.iceEnemyTexture;
                description = "Born in the north, immune to freezing.";
                break;
            case SWARMING:
                image = GraphicsConfig.swarmingEnemyTexture;
                description = "Easy to kill, coward always comes in groups";
                break;
            case BOSS:
                image = GraphicsConfig.bossEnemyTexture;
                description = "The final one. Kill him and you will suffer no more.";
                break;
            default:
                description = "";
        }
        if(image != null){
            ImagePattern imagePattern = new ImagePattern(image);
            this.rectangle.setFill(imagePattern);
        }

        String text = enemyType.name() +
                "\n" +
                description;

        Tooltip tooltip = new Tooltip(text);
        tooltip.setFont(GraphicsConfig.fontMainGameSmall);
        tooltip.setShowDelay(new Duration(250));
        Tooltip.install(rectangle, tooltip);

    }

    public VBox getvBox() {
        return super.getvBox();
    }
}
