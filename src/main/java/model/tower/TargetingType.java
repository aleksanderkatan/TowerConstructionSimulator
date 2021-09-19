package model.tower;

public enum TargetingType {
    FIRST, LAST, STRONG, RANDOM;

    private static final TargetingType[] val = values();
    public TargetingType next() {
        return val[(this.ordinal() + 1) % val.length];
    }

    public TargetingType prev() {
        return val[(this.ordinal() - 1 + val.length) % val.length];
    }
}
