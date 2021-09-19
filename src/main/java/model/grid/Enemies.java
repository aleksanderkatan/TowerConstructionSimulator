package model.grid;

import model.enemy.Enemy;

import java.util.ArrayList;
import java.util.List;

public class Enemies {
    private List<Enemy> enemies = new ArrayList<>();

    public List<Enemy> getEnemies() {
        return enemies;
    }
    public void setEnemies(List<Enemy> enemies) {
        this.enemies = enemies;
    }
}
