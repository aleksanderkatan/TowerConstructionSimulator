package view.gameObjects;

import controller.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class EnemyLoader {
    public static void draw(GraphicsContext graphicsContext, double coolDown, double x, double y){
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(Config.TILE_SIZE/14);
        graphicsContext.strokeArc(x * Config.TILE_SIZE + Config.ENEMY_WIDTH/2,
                y * Config.TILE_SIZE + Config.ENEMY_HEIGHT/2, Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT,
                90, coolDown*360, ArcType.OPEN);
    }
}
