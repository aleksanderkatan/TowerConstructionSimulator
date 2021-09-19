package view.gameObjects;

import controller.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class RangeObject {
    public static void draw(GraphicsContext graphicsContext, double x, double y, double radius){
        double xPos = x * Config.TILE_SIZE + Config.TILE_SIZE/2 - radius * Config.TILE_SIZE;
        double yPos = y * Config.TILE_SIZE + Config.TILE_SIZE/2 - radius * Config.TILE_SIZE;
        graphicsContext.setFill(Color.DARKSLATEGRAY);
        graphicsContext.setGlobalAlpha(0.5);
        graphicsContext.fillOval(xPos, yPos, 2 * radius * Config.TILE_SIZE, 2 * radius * Config.TILE_SIZE);
        graphicsContext.setGlobalAlpha(1.0);
        graphicsContext.setLineWidth(2);
        graphicsContext.setStroke(Color.DARKSLATEGRAY);
        graphicsContext.strokeOval(xPos, yPos, 2 * radius * Config.TILE_SIZE, 2 * radius * Config.TILE_SIZE);
    }
}
