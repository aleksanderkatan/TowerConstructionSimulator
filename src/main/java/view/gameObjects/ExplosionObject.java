package view.gameObjects;

import controller.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class ExplosionObject {

    public static void draw(GraphicsContext graphicsContext, double x, double y, double explosionStage,
                            double explosionMaxRange){
        double explosionTotalRange = explosionStage*explosionMaxRange;
        graphicsContext.setFill(Color.ORANGE);
        graphicsContext.setGlobalAlpha((1-explosionStage) * 0.8);
        graphicsContext.fillOval(x * Config.TILE_SIZE - explosionTotalRange * Config.TILE_SIZE,
                y * Config.TILE_SIZE - explosionTotalRange * Config.TILE_SIZE,
                explosionTotalRange * 2 * Config.TILE_SIZE, explosionTotalRange * 2 * Config.TILE_SIZE);
        graphicsContext.setGlobalAlpha(1.0);
    }
}
