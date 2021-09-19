package model.grid;

import controller.Config;
import model.enemy.Enemy;
import model.grid.tiles.EndTile;
import model.projectile.Projectile;
import model.grid.collisionManager.CollisionManager;
import model.grid.moneyManager.InsufficientMoneyException;
import model.grid.moneyManager.MoneyManager;
import model.grid.pathway.InvalidGridStructureException;
import model.grid.pathway.Pathway;
import model.grid.pathway.PathwayFactory;
import model.grid.pathway.TileIndex;
import model.grid.tiles.NotAPlaceableTileException;
import model.projectile.Explosion;
import model.tower.Tower;
import model.grid.tiles.StartTile;
import model.grid.tiles.Tile;
import model.tower.*;

import java.util.ArrayList;
import java.util.List;

public class Grid implements Config {
    private final List<List<Tile>> tiles;
    private final Enemies enemies;
    private final List<Projectile> projectiles;
    private final List<Tower> towers;
    private Pathway pathway;
    private TowerFactory towerFactory;
    private final MoneyManager moneyManager;
    private final List<Explosion> explosions;

    Grid(List<List<Tile>> tiles, Enemies enemies, List<Projectile> projectiles, List<Tower> towers,
         List<Explosion> explosions, MoneyManager moneyManager) {
        this.tiles = tiles;
        this.enemies = enemies;
        this.projectiles = projectiles;
        this.towers = towers;
        this.explosions = explosions;
        this.moneyManager = moneyManager;
    }

    public void setPathway() throws InvalidGridStructureException {
        this.pathway = PathwayFactory.produce(tiles);
    }

    public Pathway getPathway(){
        return pathway;
    }

    public List<List<Tile>> getTiles(){
        return tiles;
    }
    public List<Enemy> getEnemiesList(){
        return enemies.getEnemies();
    }
    public Enemies getEnemies(){
        return enemies;
    }
    public List<Projectile> getProjectiles() {
        return projectiles;
    }
    public List<Tower> getTowers() {
        return towers;
    }
    public List<Explosion> getExplosions() {
        return explosions;
    }
    public MoneyManager getMoneyManager() {
        return moneyManager;
    }
    public double getCurrentCoolDown() { return pathway.getStartTile().getCoolDown(); }

    private boolean checkIfEndReached(Enemy enemy) {
        return enemy.getDistance() > pathway.getLength();
    }

    public void setTowerFactory(TowerFactory towerFactory){
        this.towerFactory = towerFactory;
    }

    public Tower findTowerByTileIndex(TileIndex index) {
        for (Tower tower : towers)
            if (tower.getTileIndex().equals(index))
                return tower;
        return null;
    }

    public void upgradeTower(Tower tower) {
        if (! moneyManager.canIUpgradeTower(tower)) {
            throw new InsufficientMoneyException();
        }
        moneyManager.upgradeTower(tower);
        tower.upgrade();
    }

    public void placeTower(TileIndex tileIndex, TowerType towerType) {
        if (! moneyManager.canIBuyTower(towerType)) {
            throw new InsufficientMoneyException();
        }

        Tile tile = getTile(tileIndex);
        if(tile.getType() != Tile.TileType.PLACEABLE){
            throw new NotAPlaceableTileException();
        }
        moneyManager.buyTower(towerType);
        Tower tower = towerFactory.produceTower(towerType, tileIndex);
        towers.add(tower);
    }

    public Tile getTile(int x, int y){
        return tiles.get(y).get(x);
    }
    public Tile getTile(TileIndex index){
        return tiles.get(index.getY()).get(index.getX());
    }

    public void step() throws EndOfGameException {
        StartTile startTile = pathway.getStartTile();
        EndTile endTile = pathway.getEndTile();

        if (getEnemiesList().size() == 0 && startTile.finishedSummoning()) {  // game won
            throw new EndOfGameException();
        }
        if (endTile.getRemainingHP() <= 0) {
            throw new EndOfGameException();
        }


        List<Enemy> enemiesInEnd = new ArrayList<>();
        List<Enemy> enemiesRemaining = new ArrayList<>();
        for (Enemy enemy : getEnemiesList()) {
            enemy.step();
            if (checkIfEndReached(enemy)) {
                enemiesInEnd.add(enemy);
            } else {
                enemiesRemaining.add(enemy);
            }
        }
        enemies.setEnemies(enemiesRemaining);   //update enemies list with list only containing enemies still on path

        pathway.getEndTile().handleIncoming(enemiesInEnd); // throws EndOfGameException

        projectiles.removeIf(projectile -> CollisionManager.checkIfHitAndReact(projectile, enemies, pathway, explosions));
        projectiles.removeIf(projectile -> CollisionManager.checkIfOutsideAndReact(projectile, tiles, 1));

        enemiesRemaining = new ArrayList<>();
        for(Enemy enemy : getEnemiesList()) {
            if(enemy.getHP() <= 0) {
                moneyManager.addLoot(enemy);
            } else {
                enemiesRemaining.add(enemy);
            }
        }
        enemies.setEnemies(enemiesRemaining);

        explosions.removeIf(Explosion::tick);

        Enemy new_enemy = startTile.step();
        if (new_enemy != null)
            getEnemiesList().add(new_enemy);

        for(Tower tower : towers) {
            List<Projectile> new_projectiles = tower.step();
            projectiles.addAll(new_projectiles);
        }
    }

}
