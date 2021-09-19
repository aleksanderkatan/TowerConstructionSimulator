package model.grid.tiles;

import model.enemy.Enemy;
import model.grid.EndOfGameException;

import java.util.List;

import static java.lang.Math.exp;
import static java.lang.Math.max;

public class EndTile implements Tile {
    private final int maxHP;
    private int remainingHP;

    public EndTile(int maxHP) {
        this.maxHP = maxHP;
        remainingHP = maxHP;
    }

    public void handleIncoming(List<Enemy> incoming) {
        int totalDamage = 0;
        for (Enemy enemy : incoming) {
            totalDamage += enemy.getDamage();
        }

        remainingHP = max(remainingHP - totalDamage, 0);
    }

    public int getRemainingHP() { return remainingHP; }
    public int getMaxHP() { return maxHP; }

    public void setRemainingHP(int HP) { this.remainingHP = HP; }

    @Override
    public TileType getType() {
        return TileType.END;
    }
}
