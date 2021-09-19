package view;

import controller.Config;
import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.io.File;

public interface GraphicsConfig {
    //Tile colors
    Color placeableTileColor = Color.rgb(67,92,125);
    Color pathTileColor = Color.rgb(206,206,181);
    Color startTileColor = Color.rgb(185,146,49);
    Color endTileColor = Color.rgb(129,100,28);

    //Projectile colors
    Color bulletProjectileColor = Color.rgb(177,137,59);
    Color explosiveProjectileColor = Color.rgb(139,49,35).brighter();
    Color freezingProjectileColor = Color.rgb(132,207,214);
    Color pelletProjectileColor = Color.rgb(172,172,172);

    //Other colors
    Color upgradeBonusTextColor = Color.rgb(23, 118, 67);

    //Font
    File otomaFontFile = Getter.getFile("fonts/Otomanopee_One/OtomanopeeOne-Regular.ttf");
    Font fontMainGame = Font.loadFont(otomaFontFile.toURI().toString(), 13);
    Font fontMainGameSmall = Font.loadFont(otomaFontFile.toURI().toString(), 10);
    Font fontMainGameMid = Font.loadFont(otomaFontFile.toURI().toString(), 16);
    Font fontMainGameBig = Font.loadFont(otomaFontFile.toURI().toString(), 18);

    //EnemyObjects
    Image regularEnemyTexture = new Image(Getter.getFile("textures/regular.png").toURI().toString(),
            Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT, false, false);
    Image iceEnemyTexture = new Image(Getter.getFile("textures/ice.png").toURI().toString(),
            Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT, false, false);
    Image tankEnemyTexture = new Image(Getter.getFile("textures/tank.png").toURI().toString(),
            Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT, false, false);
    Image fastEnemyTexture = new Image(Getter.getFile("textures/fast.png").toURI().toString(),
            Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT, false, false);
    Image swarmingEnemyTexture = new Image(Getter.getFile("textures/swarming2.png").toURI().toString(),
            Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT, false, false);
    Image bossEnemyTexture = new Image(Getter.getFile("textures/boss.png").toURI().toString(),
            Config.ENEMY_WIDTH, Config.ENEMY_HEIGHT, false, false);

    //TowerObjects
    Image basicTowerTexture =  new Image(Getter.getFile("textures/basic.png").toURI().toString(),
            Config.TOWER_WIDTH, Config.TOWER_HEIGHT, false, false);
    Image cannonTowerTexture =  new Image(Getter.getFile("textures/canon.png").toURI().toString(),
            Config.TOWER_WIDTH, Config.TOWER_HEIGHT, false, false);
    Image freezingTowerTexture =  new Image(Getter.getFile("textures/freeze.png").toURI().toString(),
            Config.TOWER_WIDTH, Config.TOWER_HEIGHT, false, false);
    Image sniperTowerTexture =  new Image(Getter.getFile("textures/sniper.png").toURI().toString(),
            Config.TOWER_WIDTH, Config.TOWER_HEIGHT, false, false);
    Image shotgunTowerTexture = new Image(Getter.getFile("textures/shotgun.png").toURI().toString(),
             Config.TOWER_WIDTH, Config.TOWER_HEIGHT, false, false);

    //Arrows
    Image arrowLeft = new Image(Getter.getFile("textures/arrow_left.png").toURI().toString(),
            15,  15, true, false);
    Image arrowRight = new Image(Getter.getFile("textures/arrow_right.png").toURI().toString(),
            15,  15, true, false);

    //Background
    BackgroundImage background_night_main = new BackgroundImage(Getter.getImage("backgrounds/background_main.png"),
            null, null, null,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                    true, true, true, true));
    BackgroundImage background_night_blur = new BackgroundImage(Getter.getImage("backgrounds/background_game.png"),
            null, null, null,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                    true, true, true, true));
    BackgroundImage background_day_main = new BackgroundImage(Getter.getImage("backgrounds/background_day.png"),
            null, null, null,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                    true, true, true, true));
    BackgroundImage background_day_blur = new BackgroundImage(Getter.getImage("backgrounds/background_day_blur.png"),
            null, null, null,
            new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO,
                    true, true, true, true));
}
