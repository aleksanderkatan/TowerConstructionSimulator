package model.projectile;

import model.mathematics.Point;

public class Explosion {
    int ticksToEnd;
    double explosionMaxRange;
    Point point;
    public Explosion(int ticksToEnd, double explosionMaxRange, Point point) {
        this.explosionMaxRange = explosionMaxRange;
        this.ticksToEnd = ticksToEnd;
        this.point = point;
    }

    public double getExplosionMaxRange() {
        return explosionMaxRange;
    }
    public double getTicksToEnd() {
        return ticksToEnd;
    }
    public boolean tick() {
        return --ticksToEnd<=0;
    }
    public Point getPoint() {
        return point;
    }
}
