package model.grid.enemyGenerator;

import javafx.util.Pair;
import model.enemy.EnemyFactory;
import model.enemy.EnemyType;

import java.util.*;

public class EnemyGeneratorFactory {

    public EnemyGenerator produce(String generatorString) throws UnsupportedEnemyGeneratorStringException {
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();
        double difficultyScale;
        double randomScale;

        String[] s = generatorString.split("\n");

        if (s.length < 3) throw new UnsupportedEnemyGeneratorStringException();
        try {
            difficultyScale = Double.parseDouble(s[0]);
            randomScale = Double.parseDouble(s[1]);
        } catch (Exception e) {
            throw new UnsupportedEnemyGeneratorStringException();
        }

        for (int i = 2; i< s.length; i++) {
            if (s[i].equals("")) break;
            List<EnemyType> list = new ArrayList<>();
            int amount;

            String[] wave = s[i].split(" ");

            if (wave.length < 2) throw new UnsupportedEnemyGeneratorStringException();
            try {
                amount = Integer.parseInt(wave[0]);
            } catch (Exception e) {
                throw new UnsupportedEnemyGeneratorStringException();
            }

            if (amount < 0)
                throw new UnsupportedEnemyGeneratorStringException();

            for (int j = 1; j< wave.length; j++) {
                //noinspection EnhancedSwitchMigration
                switch (wave[j]) {
                case "REGULAR":
                    list.add(EnemyType.REGULAR);
                    break;
                case "FAST":
                    list.add(EnemyType.FAST);
                    break;
                case "TANK":
                    list.add(EnemyType.TANK);
                    break;
                case "SWARMING":
                    list.add(EnemyType.SWARMING);
                    break;
                case "ICE":
                    list.add(EnemyType.ICE);
                    break;
                case "BOSS":
                    list.add(EnemyType.BOSS);
                    break;
                default:
                    throw new UnsupportedEnemyGeneratorStringException();
                }
            }
            waveTypes.add(new Pair<>(amount, list));
        }

        return produce(waveTypes, new EnemyFactory(new Random()), difficultyScale, randomScale);
    }

    public EnemyGenerator produce(List<Pair<Integer, List<EnemyType>>> waveTypes, EnemyFactory enemyFactory, double difficultyScale, double randomScale) {
        return new EnemyGenerator(waveTypes, enemyFactory, difficultyScale, randomScale);
    }
}
