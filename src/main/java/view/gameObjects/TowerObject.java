package view.gameObjects;

import controller.Config;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import model.tower.Tower;

import static view.GraphicsConfig.*;

public class TowerObject {

    public static void draw(GraphicsContext graphicsContext, double x, double y, Tower tower){
        Image image = null;
        //noinspection EnhancedSwitchMigration
        switch (tower.getTowerType()){
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
            graphicsContext.drawImage(image,x * Config.TILE_SIZE + Config.TILE_SIZE/7 ,
                    y * Config.TILE_SIZE + Config.TILE_SIZE/7, Config.TOWER_WIDTH, Config.TOWER_HEIGHT);
        }
    }
}
