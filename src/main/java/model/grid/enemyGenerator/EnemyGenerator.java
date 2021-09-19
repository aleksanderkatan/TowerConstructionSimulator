package model.grid.enemyGenerator;

import javafx.util.Pair;
import model.enemy.Enemy;
import model.enemy.EnemyFactory;
import model.enemy.EnemyType;

import java.util.*;

import static java.lang.Math.pow;

public class EnemyGenerator {
    //Enemy Generator differs from EnemyFactory - factory is for creating instances, generator produces series
    //of enemies for Start to deploy them onto grid

    private final List<Pair<Integer, List<EnemyType>>> waveTypes;       // for each wave there is amount of enemies and
                                                                        // possible types which will alter
    private final EnemyFactory enemyFactory;

    private final double difficultyScale;
    private final double randomScale;

    public EnemyGenerator(List<Pair<Integer, List<EnemyType>>> waveTypes, EnemyFactory enemyFactory, double difficultyScale, double randomScale) {
        this.waveTypes = waveTypes;
        this.enemyFactory = enemyFactory;
        this.difficultyScale = difficultyScale;
        this.randomScale = randomScale;
    }

    public Queue<Enemy> generateWave(int level) {
        Queue<Enemy> enemyWave = new LinkedList<>();

        if (level >= waveTypes.size())
            return enemyWave;

        double difficulty = pow(difficultyScale, level - 1);

        int enemyAmount = waveTypes.get(level).getKey();
        List<EnemyType> types = waveTypes.get(level).getValue();
        int currentTypeIndex = 0;
        for (int i = 0; i< enemyAmount; i++) {
            EnemyType type = types.get(currentTypeIndex);

            Enemy enemy = null;
            //noinspection EnhancedSwitchMigration
            switch (type) {
                case SWARMING:
                    enemy = enemyFactory.produceSwarmingEnemy(difficulty, randomScale);
                    break;
                case TANK:
                    enemy = enemyFactory.produceTankEnemy(difficulty, randomScale);
                    break;
                case FAST:
                    enemy = enemyFactory.produceFastEnemy(difficulty, randomScale);
                    break;
                case ICE:
                    enemy = enemyFactory.produceIceEnemy(difficulty, randomScale);
                    break;
                case BOSS:
                    enemy = enemyFactory.produceBossEnemy(difficulty, randomScale);
                    break;
                case REGULAR:
                    enemy = enemyFactory.produceRegularEnemy(difficulty, randomScale);
                    break;
            }
            if (enemy != null)
                enemyWave.add(enemy);

            currentTypeIndex = (currentTypeIndex + 1) % types.size();
        }

        return enemyWave;
    }

    public List<Pair<Integer, List<EnemyType>>> getWaveTypes() {
        return waveTypes;
    }

    public int getWavesAmount() { return waveTypes.size(); }
}
