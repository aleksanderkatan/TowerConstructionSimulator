package model.enemy;

import controller.Config;

import java.util.Random;

public class EnemyFactory {
    //Enemy Generator differs from EnemyFactory - factory is for creating instances, generator produces series
    //of enemies for game purposes

    private final Random random;

    public EnemyFactory(Random random){
        this.random = random;
    }

    private double confidenceInterval(double value, double randomScale) {
        double rangeMin = value * (1-randomScale);
        double rangeMax = value * (1+randomScale);

        return rangeMin + (rangeMax - rangeMin) * random.nextDouble();
    }

    private Enemy scaleEnemy(double difficulty, EnemyType type, double maxHP, double summoningSickness, double speed, int gold, int damage, double randomScale) {
        return new Enemy(
                type,
                confidenceInterval(maxHP, randomScale) * difficulty,
                confidenceInterval(summoningSickness, randomScale),
                speed,
                (int)confidenceInterval(gold, randomScale),
                damage
        );
    }

    public Enemy produceRegularEnemy(double difficulty, double randomScale) {
        return scaleEnemy(difficulty, EnemyType.REGULAR, 100, 1, 1, 15, 1, randomScale);
    }
    public Enemy produceIceEnemy(double difficulty, double randomScale) {
        return scaleEnemy(difficulty, EnemyType.ICE, 150, 1, 1, 20, 1, randomScale);
    }
    public Enemy produceFastEnemy(double difficulty, double randomScale) {
        return scaleEnemy(difficulty, EnemyType.FAST, 40, 0.5, 2, 10, 1, randomScale);
    }
    public Enemy produceTankEnemy(double difficulty, double randomScale) {
        return scaleEnemy(difficulty, EnemyType.TANK, 250, 1.25, 0.7, 40, 2, randomScale);
    }
    public Enemy produceSwarmingEnemy(double difficulty, double randomScale) {
        return scaleEnemy(difficulty, EnemyType.SWARMING, 50, 0.25, 1, 5, 1, randomScale);
    }
    public Enemy produceBossEnemy(double difficulty, double randomScale) {
        return scaleEnemy(difficulty, EnemyType.BOSS, 4000, 5, 0.2, 10000, 10, randomScale);
    }
}
