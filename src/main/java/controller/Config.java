package controller;

public interface Config {
    // Controller
    int FRAME_RATE = 60;

    // Model
    int STARTING_HEALTH = 15;

    double ENEMY_SPEED = 1.5;
    double FREEZE_TIME = 2;
    double EXPLOSION_TIME = 0.2;
    double EXPLOSION_TOTAL_TICKS = EXPLOSION_TIME * FRAME_RATE;
    int WAVE_DELAY = 5 * FRAME_RATE;    // steps before another wave is released
    int ENEMY_DELAY = FRAME_RATE;    // default steps before another wave is released
    int MAX_TOWER_LEVEL = 10;

    double UPGRADE_RANGE_MULTIPLIER = 1.05;
    double UPGRADE_FIRE_RATE_MULTIPLIER = 1.1;
    double UPGRADE_DAMAGE_MULTIPLIER = 1.2;
    double UPGRADE_VELOCITY_MULTIPLIER = 1.1;
    double UPGRADE_BASE_COST = 0.5;
    double UPGRADE_COST_MULTIPLIER = 1.3;
    double UPGRADE_MAX_FREEZING_POWER = 0.7;
    double UPGRADE_MIN_PROJECT_OFFSET = 0.2;
    double UPGRADE_EXPLOSION_RANGE_MULTIPLIER = 1.02;

    double PROJECTILE_BASE_VELOCITY = 7.5;
    double PROJECTILE_BASE_DAMAGE = 25;
    double PROJECTILE_BASE_FREEZING_POWER = 0.3;        // Freezing
    double PROJECTILE_BASE_EXPLOSION_RANGE = 1;         // Explosive
    double PROJECTILE_BASE_OFFSET = 0.4;                // Pellet
    double PROJECTILE_BASE_VELOCITY_OFFSET = 0.1;       // Pellet

    double PRECISION = 0.00001;
    // View
    double ENEMY_HEIGHT = 35;
    double ENEMY_WIDTH = 35;
    double TOWER_HEIGHT = 50;
    double TOWER_WIDTH = 50;
    double TILE_SIZE = 70;
    double SCREEN_HEIGHT = 750;
    double SCREEN_WIDTH = 1300;
    double PROJECTILE_HEIGHT = 10;
    double PROJECTILE_WIDTH = 10;

    // Game
    int STARTING_GOLD = 250;

}
