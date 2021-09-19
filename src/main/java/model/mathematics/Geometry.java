package model.mathematics;

import controller.Config;
import model.grid.pathway.NotASegmentException;

import java.util.Random;

import static java.lang.Math.max;
import static java.lang.Math.min;

public class Geometry {
    public static boolean close(double a, double b) {
        return a-b < Config.PRECISION && b-a < Config.PRECISION;
    }
    public static double distanceBetween(Point a, Point b) {
        double xDiff = a.x-b.x;
        double yDiff = a.y-b.y;
        return Math.sqrt( xDiff*xDiff + yDiff*yDiff );
    }

    // distance from point x to segment ab
    public static double distanceFromSegment(Point x, Point a, Point b) {
        if(a.equals(b))
            return distanceBetween(x, b);
        Point ax = new Point(x.x-a.x, x.y-a.y);
        Point ab = new Point(b.x-a.x, b.y-a.y);
        double projection = (ab.x*ax.x + ab.y*ax.y)/ab.length();
        double distance = min(max(projection, 0), ab.length());
        return distanceBetween(x, getOnSegment(a, b, distance));
    }

    // point in distance 'distance' from start to target
    public static Point getOnSegment(Point start, Point target, double distance) {
        if(start.equals(target))
            throw new NotASegmentException();
        double part = distance / distanceBetween(start, target);
        return new Point(start.x+(target.x-start.x)*part, start.y+(target.y-start.y)*part);
    }

    public static Point getRandomInCircle(Point center, double radius, Random random) {
        double x = center.x;
        double y = center.y;

        double r = radius * Math.sqrt(random.nextDouble());
        double alpha = random.nextDouble() * 2 * Math.PI;

        x += r * Math.cos(alpha);
        y += r * Math.sin(alpha);

        return new Point(x, y);
    }
}
