package view.gameObjects;

import controller.Config;
import javafx.scene.canvas.GraphicsContext;
import model.projectile.ProjectileType;
import view.GraphicsConfig;

public class ProjectileObject {
    public static void draw(GraphicsContext graphicsContext, double x, double y, ProjectileType projectileType){
        switch (projectileType){
            case FREEZING:
                graphicsContext.setFill(GraphicsConfig.freezingProjectileColor);
                break;
            case EXPLOSIVE:
                graphicsContext.setFill(GraphicsConfig.explosiveProjectileColor);
                break;
            case BULLET:
                graphicsContext.setFill(GraphicsConfig.bulletProjectileColor);
                break;
            case PELLET:
                graphicsContext.setFill(GraphicsConfig.pelletProjectileColor);
                break;
            default:
                return;
        }
        graphicsContext.fillOval(x * Config.TILE_SIZE - Config.PROJECTILE_WIDTH/2,
                y * Config.TILE_SIZE - Config.PROJECTILE_HEIGHT/2, Config.PROJECTILE_WIDTH, Config.PROJECTILE_HEIGHT);
        graphicsContext.setLineWidth(1);
        graphicsContext.strokeOval(x * Config.TILE_SIZE - Config.PROJECTILE_WIDTH/2,
                y * Config.TILE_SIZE - Config.PROJECTILE_HEIGHT/2, Config.PROJECTILE_WIDTH, Config.PROJECTILE_HEIGHT);
        graphicsContext.stroke();
    }
}
