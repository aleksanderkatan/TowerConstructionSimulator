package model.enemy;


import controller.Config;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;


public class Enemy {
    private double HP;
    private final double maxHP;
    private double distance;
    private final double speed;
    private final double summoningSickness;
    private final int gold;
    private final int damage;
    private final EnemyType enemyType;
    private final List<Freeze> freezes = new ArrayList<>();

    public Enemy(EnemyType enemyType, double maxHP, double summoningSickness, double speed, int gold, int damage){
        this.HP = maxHP;
        this.maxHP = maxHP;
        this.summoningSickness = summoningSickness;
        this.distance = 0;
        this.speed = speed * Config.ENEMY_SPEED;
        this.enemyType = enemyType;
        this.gold = gold;
        this.damage = damage;
    }

    public void step() {
        double actualSpeed = speed;
        freezes.removeIf(Freeze::tick);

        if(!freezes.isEmpty()) {
            double max = 0;
            for(Freeze freeze : freezes)
                if(freeze.getFreezingPower()>max)
                    max = freeze.getFreezingPower();
            actualSpeed *= (1-max);
        }

        distance += actualSpeed/Config.FRAME_RATE;
    }

    public void setHP(double HP) {
        this.HP = HP;
    }

    public double getHP() {
        return this.HP;
    }

    public double getMaxHP() {
        return this.maxHP;
    }

    public double getDistance() {
        return this.distance;
    }

    public double getSpeed() {
        return this.speed;
    }

    public int getDamage() { return this.damage; }

    public double getSummoningSickness() {
        return summoningSickness;
    }

    public EnemyType getEnemyType() {
        return enemyType;
    }

    public int getGold() {
        return gold;
    }

    public void freeze(double freezingPower) {
        freezes.add(new Freeze(Config.FREEZE_TIME*Config.FRAME_RATE, freezingPower));
    }
}
