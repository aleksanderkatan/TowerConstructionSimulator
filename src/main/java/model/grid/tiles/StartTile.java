package model.grid.tiles;

import controller.Config;
import javafx.util.Pair;
import model.enemy.Enemy;
import model.enemy.EnemyFactory;
import model.enemy.EnemyType;
import model.grid.enemyGenerator.EnemyGenerator;
import model.grid.enemyGenerator.EnemyGeneratorFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;



public class StartTile implements Tile {
    private final EnemyGenerator generator;
    public final int waveDelay = Config.WAVE_DELAY;
    public final int enemyDelay = Config.FRAME_RATE;

    private Queue<Enemy> currentWave;
    private int waveIndex;
    private int currentDelay;
    private int delay;
    private boolean waitingForNextWave;


    public StartTile(EnemyGenerator generator) {
        this.generator = generator;
        delay = 0;
        waveIndex = 0;
        currentDelay = 0;
        waitingForNextWave = false;
        this.currentWave = new LinkedList<>();
    }
    
    public double getCoolDown() {
        if (finishedSummoning()) return 0;
        if (currentDelay == 0) return 0;
        return (currentDelay - delay) * 1.0 / currentDelay;
    }

    @Override
    public TileType getType() {
        return TileType.START;
    }

    public boolean finishedSummoning() {
        return (generator.getWavesAmount() <= waveIndex) && (currentWave.size() == 0);
    }

    public Enemy step() {
        Enemy result = null;

        if (finishedSummoning()) return null;

        if (delay <= 0) {       //timer has reached 0
            if (waitingForNextWave) {        // just increasing an index
                waitingForNextWave = false;
                waveIndex++;
            }

            if (currentWave.isEmpty()) {        // either wave has just ended
                delay = waveDelay;
                currentWave = generator.generateWave(waveIndex);
                waitingForNextWave = true;
            } else {        // or it's time for deploying another enemy
                result = currentWave.remove();
                delay = (int)(enemyDelay * result.getSummoningSickness());
            }
            currentDelay = delay;
        } else {
            delay--;
        }

        return result;
    }

    public int getWaveIndex() { return waveIndex; }
    public int getNextWaveIndex() { return getWaveIndex() + 1; }
    public List<Pair<Integer, List<EnemyType>>> getWaveTypes(){
        return generator.getWaveTypes();
    }
    public int getTotalWavesAmount() { return getWaveTypes().size(); }
}
