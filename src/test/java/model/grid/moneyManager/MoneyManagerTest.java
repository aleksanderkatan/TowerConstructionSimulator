package model.grid.moneyManager;

import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.grid.moneyManager.MoneyManager;
import model.tower.Tower;
import model.tower.TowerType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MoneyManagerTest {
    @Test
    void testEnemy() {
        MoneyManager manager = new MoneyManager(1000);
        Enemy enemy = new Enemy(EnemyType.REGULAR, 10, 10, 10, 100, 1);

        manager.addLoot(enemy);

        assertEquals(1000+100, manager.getGold());
    }

    @Test
    void testCanIBuyTower() {
        int cost = MoneyManager.getTowerCost(TowerType.SHOTGUN);
        MoneyManager manager1 = new MoneyManager(0);
        MoneyManager manager2 = new MoneyManager(cost + 10);

        boolean canIBuy1 = manager1.canIBuyTower(TowerType.SHOTGUN);
        boolean canIBuy2 = manager2.canIBuyTower(TowerType.SHOTGUN);

        assertFalse(canIBuy1);
        assertTrue(canIBuy2);
    }

    @Test
    void testBuyingTower() {
        int cost = MoneyManager.getTowerCost(TowerType.BASIC);
        MoneyManager manager = new MoneyManager(10+cost);

        manager.buyTower(TowerType.BASIC);
        int money = manager.getGold();

        assertEquals(10, money);
    }

    @Test
    void testCanIUpgradeTower() {
        int cost = MoneyManager.getUpgradeCost(TowerType.BASIC, 1);
        MoneyManager manager1 = new MoneyManager(0);
        MoneyManager manager2 = new MoneyManager(cost + 10);

        boolean canIBuy1 = manager1.canIUpgradeTower(TowerType.BASIC, 1);
        boolean canIBuy2 = manager2.canIUpgradeTower(TowerType.BASIC, 1);

        assertFalse(canIBuy1);
        assertTrue(canIBuy2);
    }

    @Test
    void testCanIUpgradeTowerByReference() {
        Tower tower = mock(Tower.class);
        when(tower.getTowerType()).thenReturn(TowerType.CANNON);
        when(tower.getLevel()).thenReturn(1);
        int cost = MoneyManager.getUpgradeCost(tower);
        MoneyManager manager1 = new MoneyManager(0);
        MoneyManager manager2 = new MoneyManager(cost + 10);

        boolean canIBuy1 = manager1.canIUpgradeTower(tower);
        boolean canIBuy2 = manager2.canIUpgradeTower(tower);

        assertFalse(canIBuy1);
        assertTrue(canIBuy2);
    }

    @Test
    void testUpgradeTower() {
        int cost = MoneyManager.getUpgradeCost(TowerType.BASIC, 1);
        MoneyManager manager = new MoneyManager(10 + cost);

        manager.upgradeTower(TowerType.BASIC, 1);
        int money = manager.getGold();

        assertEquals(10, money);
    }

    @Test
    void testUpgradeTowerByReference() {
        Tower tower = mock(Tower.class);
        when(tower.getTowerType()).thenReturn(TowerType.SNIPER);
        when(tower.getLevel()).thenReturn(1);
        doNothing().when(tower).upgrade();
        int cost = MoneyManager.getUpgradeCost(tower);
        MoneyManager manager = new MoneyManager(10 + cost);

        manager.upgradeTower(tower);
        int money = manager.getGold();

        assertEquals(10, money);
    }

    @Test
    void testSetGold() {
        MoneyManager manager = new MoneyManager(10);

        int gold1 = manager.getGold();
        manager.setGold(20);
        int gold2 = manager.getGold();

        assertEquals(10, gold1);
        assertEquals(20, gold2);
    }
}
