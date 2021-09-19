package model.enemy;

public class Freeze {
    double ticksToEnd;
    double freezingPower;
    public Freeze(double ticksToEnd, double freezingPower) {
        this.ticksToEnd = ticksToEnd;
        this.freezingPower = freezingPower;
    }

    public boolean tick() {
        return --ticksToEnd<=0;
    }
    public double getFreezingPower() {
        return freezingPower;
    }
}
