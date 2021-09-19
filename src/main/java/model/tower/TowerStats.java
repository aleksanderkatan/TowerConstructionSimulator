package model.tower;

import java.util.HashMap;
import java.util.Map;

public class TowerStats {
    public TowerType type;
    public double fireRate;
    public double damage;
    public double range;
    public double projectileVelocity;
    public double freezingPower;
    public double explosionRange;
    public int projectileAmount;
    public double projectileOffset;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TowerStats other = (TowerStats) o;
        return fireRate == other.fireRate
                && damage == other.damage
                && range == other.range
                && projectileVelocity == other.projectileVelocity
                && freezingPower == other.freezingPower
                && explosionRange == other.explosionRange
                && projectileAmount == other.projectileAmount
                && projectileOffset == other.projectileOffset;
    }

}
