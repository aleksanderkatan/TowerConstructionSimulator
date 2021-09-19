package model.tower;

import model.enemy.Enemy;

import java.util.List;
import java.util.Random;

public class TargetFinder {

    private static Enemy findFirst(List<Enemy> enemies) {
        if (enemies.isEmpty()) return null;

        Enemy result = enemies.get(0);
        for (Enemy enemy: enemies) {
            if (enemy.getDistance() > result.getDistance())
                result = enemy;
        }

        return result;
    }

    private static Enemy findLast(List<Enemy> enemies) {
        if (enemies.isEmpty()) return null;

        Enemy result = enemies.get(0);
        for (Enemy enemy: enemies) {
            if (enemy.getDistance() < result.getDistance())
                result = enemy;
        }

        return result;
    }

    private static Enemy findStrong(List<Enemy> enemies) {
        if (enemies.isEmpty()) return null;

        Enemy result = enemies.get(0);
        for (Enemy enemy: enemies) {
            if (enemy.getHP() > result.getHP())
                result = enemy;
        }

        return result;
    }

    private static Enemy findRandom(List<Enemy> enemies, Random random) {
        if (enemies.isEmpty()) return null;
        return enemies.get(random.nextInt(enemies.size()));
    }



    public static Enemy findTarget(List<Enemy> enemies, TargetingType targetingType) {
        Enemy result = null;

        //noinspection EnhancedSwitchMigration
        switch (targetingType) {
            case FIRST:
                result = findFirst(enemies);
                break;
            case LAST:
                result = findLast(enemies);
                break;
            case STRONG:
                result = findStrong(enemies);
                break;
            case RANDOM:
                result = findRandom(enemies, new Random());
                break;
        }

        return result;
    }
}
