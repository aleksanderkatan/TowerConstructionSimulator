package model.grid.moneyManager;


import controller.Config;
import model.enemy.Enemy;
import model.tower.Tower;
import model.tower.TowerType;

import static java.lang.Math.pow;

public class MoneyManager {
    private int gold;

    public MoneyManager(int gold) {
        this.gold = gold;
    }

    public int getGold() { return gold; }

    public void setGold(int gold) { this.gold = gold; }

    public static int getTowerCost(TowerType towerType) {
        int result = 0;
        //noinspection EnhancedSwitchMigration
        switch (towerType) {
            case BASIC:
                result = 100;
                break;
            case CANNON:
                result = 150;
                break;
            case FREEZING:
                result = 75;
                break;
            case SNIPER:
                result = 250;
                break;
            case SHOTGUN:
                result = 500;
                break;
        }
        return result;
    }

    public boolean canIBuyTower(TowerType towerType) {
        int cost = getTowerCost(towerType);
        return gold >= cost;
    }
    public void buyTower(TowerType towerType) {
        int cost = getTowerCost(towerType);
        gold -= cost;
    }

    public static int getUpgradeCost(Tower tower) {
        return getUpgradeCost(tower.getTowerType(), tower.getLevel());
    }

    public static int getUpgradeCost(TowerType type, int currentLevel) {
        double baseCost = Config.UPGRADE_BASE_COST * getTowerCost(type);
        double multiplier = pow(Config.UPGRADE_COST_MULTIPLIER, currentLevel);
        return (int)(baseCost * multiplier);
    }

    public boolean canIUpgradeTower(TowerType type, int currentLevel) {
        int cost = getUpgradeCost(type, currentLevel);
        return gold >= cost;
    }

    public boolean canIUpgradeTower(Tower tower) {
        return canIUpgradeTower(tower.getTowerType(), tower.getLevel());
    }

    public void upgradeTower(TowerType type, int currentLevel) {
        int cost = getUpgradeCost(type, currentLevel);
        gold -= cost;
    }

    public void upgradeTower(Tower tower) {
        upgradeTower(tower.getTowerType(), tower.getLevel());
    }

    public void addLoot(Enemy enemy) {
        gold += enemy.getGold();
    }
}
