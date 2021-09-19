package model.grid.collisionManager;

import controller.Config;
import model.grid.tiles.Tile;
import model.projectile.Projectile;
import model.enemy.Enemy;
import model.enemy.EnemyType;
import model.mathematics.Geometry;
import model.grid.Enemies;
import model.mathematics.Point;
import model.grid.pathway.Pathway;
import model.projectile.Explosion;
import model.projectile.ExplosiveProjectile;
import model.projectile.FreezingProjectile;
import model.projectile.ProjectileType;

import java.util.List;

public class CollisionManager {
    public static boolean checkIfHitAndReact(Projectile projectile, Enemies enemies, Pathway pathway, List<Explosion> explosions) {
        Point prev = projectile.getPosition();
        projectile.step();
        Point now = projectile.getPosition();
        for(int i = 0; i<enemies.getEnemies().size(); i++) {
            Enemy enemy = enemies.getEnemies().get(i);
            Point enemyPoint = pathway.getPosition(enemy.getDistance());

            if(Geometry.distanceFromSegment(enemyPoint, prev, now) <
                    (Config.ENEMY_HEIGHT/2+Config.PROJECTILE_HEIGHT/2)/Config.TILE_SIZE) {
                if(projectile.getType() == ProjectileType.EXPLOSIVE) {
                    applyExplosion(projectile, enemies, pathway);
                    explosions.add(new Explosion((int) (Config.EXPLOSION_TIME*Config.FRAME_RATE), ((ExplosiveProjectile)projectile).getExplosionRange(), now));
                    return true;
                }
                enemy.setHP(enemy.getHP()-projectile.getDamage());
                if(projectile.getType()==ProjectileType.FREEZING && enemy.getEnemyType()!= EnemyType.ICE) {
                    enemy.freeze(((FreezingProjectile)projectile).getFreezingPower());
                }
                return true;
            }
        }
        return false;
    }

    static void applyExplosion(Projectile projectile, Enemies enemies, Pathway pathway) {
        Point now = projectile.getPosition();
        for(int i = 0; i<enemies.getEnemies().size(); i++) {
            Enemy enemy = enemies.getEnemies().get(i);
            Point enemyPoint = pathway.getPosition(enemy.getDistance());

            if(Geometry.distanceBetween(enemyPoint, now) <
                    (Config.ENEMY_HEIGHT/2)/Config.TILE_SIZE+((ExplosiveProjectile)projectile).getExplosionRange()) {
                enemy.setHP(enemy.getHP()-projectile.getDamage());
            }
        }
    }

    public static boolean checkIfOutsideAndReact(Projectile projectile, List<List<Tile>> tiles, double offset) {
        double x = projectile.getPosition().getX();
        double y = projectile.getPosition().getY();

        if (x < -offset || x > tiles.get(0).size() + offset)
            return true;
        return y < -offset || y > tiles.size() + offset;
    }
}
