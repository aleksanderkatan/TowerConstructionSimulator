package view.gameObjects;

import controller.Config;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import model.tower.TowerType;

import static view.GraphicsConfig.*;

public class GhostTowerObject {
    private final double x;
    private final double y;
    private final TowerType towerType;
    private final double range;
    public GhostTowerObject(TowerType towerType, double x, double y, double range){
        this.towerType = towerType;
        this.x = x;
        this.y = y;
        this.range = range;
    }
    public Point2D getPosition(){
        return new Point2D(x, y);
    }
    public void draw(GraphicsContext graphicsContext){
        Image image = null;
        //noinspection EnhancedSwitchMigration
        switch (towerType){
            case BASIC:
                image = basicTowerTexture;
                break;
            case CANNON:
                image = cannonTowerTexture;
                break;
            case FREEZING:
                image = freezingTowerTexture;
                break;
            case SNIPER:
                image = sniperTowerTexture;
                break;
            case SHOTGUN:
                image = shotgunTowerTexture;
        }
        if(image != null){
            double xPos = x * Config.TILE_SIZE + Config.TILE_SIZE/2 - range * Config.TILE_SIZE;
            double yPos = y * Config.TILE_SIZE + Config.TILE_SIZE/2 - range * Config.TILE_SIZE;
            double size = 2 * range * Config.TILE_SIZE;
            graphicsContext.setFill(Color.DARKSLATEGRAY);
            graphicsContext.setGlobalAlpha(0.3);
            graphicsContext.fillOval(xPos, yPos, size, size);
            graphicsContext.setGlobalAlpha(1.0);
            graphicsContext.setLineWidth(2);
            graphicsContext.setStroke(Color.DARKSLATEGRAY);
            graphicsContext.strokeOval(xPos, yPos, size, size);
            graphicsContext.setGlobalAlpha(0.5);
            graphicsContext.drawImage(image,x * Config.TILE_SIZE + Config.TILE_SIZE/7 ,
                    y * Config.TILE_SIZE + Config.TILE_SIZE/7, Config.TOWER_WIDTH, Config.TOWER_HEIGHT);
            graphicsContext.setGlobalAlpha(1.0);
        }
    }
}
