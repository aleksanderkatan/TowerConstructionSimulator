package model.grid;

import javafx.util.Pair;
import model.enemy.EnemyFactory;
import model.enemy.EnemyType;
import model.grid.enemyGenerator.EnemyGenerator;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class Defaults {
    public static EnemyGenerator getDefaultGenerator() {
        List<Pair<Integer, List<EnemyType>>> waveTypes = new ArrayList<>();

        waveTypes.add(new Pair<>(10, Collections.singletonList(EnemyType.REGULAR)));
        waveTypes.add(new Pair<>(5, Collections.singletonList(EnemyType.FAST)));
        waveTypes.add(new Pair<>(50, Collections.singletonList(EnemyType.SWARMING)));
        waveTypes.add(new Pair<>(15, Collections.singletonList(EnemyType.ICE)));
        waveTypes.add(new Pair<>(15, Collections.singletonList(EnemyType.TANK)));
        waveTypes.add(new Pair<>(10, Arrays.asList(EnemyType.TANK, EnemyType.REGULAR)));
        waveTypes.add(new Pair<>(20, Arrays.asList(EnemyType.ICE, EnemyType.REGULAR)));
        waveTypes.add(new Pair<>(20, Arrays.asList(EnemyType.REGULAR, EnemyType.TANK)));
        waveTypes.add(new Pair<>(20, Arrays.asList(EnemyType.SWARMING, EnemyType.ICE, EnemyType.FAST)));

        return new EnemyGenerator(waveTypes, new EnemyFactory(getDefaultRandom()), 1, 0);
    }
    public static Random getDefaultRandom(){
        Random random = mock(Random.class);
        when(random.nextDouble()).thenReturn(0.0);
        when(random.nextInt()).thenReturn(0);
        return random;
    }
}
