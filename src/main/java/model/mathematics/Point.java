package model.mathematics;

import controller.Config;

public class Point implements Config {
    final double x, y;
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    public double getX() { return x; }
    public double getY() { return y; }
    public double length() { return Geometry.distanceBetween(new Point(0, 0), this); }

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Point)) return false;
        Point other = (Point) obj;
        return Geometry.close(other.getX(), getX()) && Geometry.close(other.getY(), getY());
    }
}
