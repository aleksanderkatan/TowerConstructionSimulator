package view.gameObjects;

import controller.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.enemy.Enemy;

import static view.GraphicsConfig.*;

public class EnemyObject {

    public static void draw(GraphicsContext graphicsContext, double x, double y, Enemy enemy){
        double HP_FILL = enemy.getHP()/enemy.getMaxHP();
        if(enemy.getHP() != enemy.getMaxHP()){
            graphicsContext.setFill(Color.BLACK);
            graphicsContext.fillRect(x * Config.TILE_SIZE - Config.ENEMY_WIDTH/2,
                    y * Config.TILE_SIZE - 4/3f * Config.ENEMY_HEIGHT/2, Config.ENEMY_WIDTH,
                    1/10f * Config.ENEMY_HEIGHT);
            graphicsContext.setFill(Color.LIGHTGREEN);
            graphicsContext.fillRect(x * Config.TILE_SIZE - Config.ENEMY_WIDTH/2,
                    y * Config.TILE_SIZE - 4/3f * Config.ENEMY_HEIGHT/2,  HP_FILL * Config.ENEMY_WIDTH,
                    1/10f * Config.ENEMY_HEIGHT);
        }

        Image image = null;
        //noinspection EnhancedSwitchMigration
        switch (enemy.getEnemyType()){
            case REGULAR:
                image = regularEnemyTexture;
                break;
            case ICE:
                image = iceEnemyTexture;
                break;
            case TANK:
                image = tankEnemyTexture;
                break;
            case FAST:
                image = fastEnemyTexture;
                break;
            case SWARMING:
                image = swarmingEnemyTexture;
                break;
            case BOSS:
                image = bossEnemyTexture;
                break;
        }
        if(image != null){
            graphicsContext.drawImage(image,x * Config.TILE_SIZE - Config.ENEMY_WIDTH/2,
                    y * Config.TILE_SIZE - Config.ENEMY_HEIGHT/2 );
        }
    }
}
