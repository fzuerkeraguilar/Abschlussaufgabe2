package edu.kit.informatik.data.fields;

public class FireStation extends Field{
    public static final String IDENTIFIER_REGEX = "[A-D]";
    public static final double FIRE_ENGINE_SPAWNING_RADIUS = Math.cbrt(2);
    public final String IDENTIFIER;
    private static final boolean CONTAINS_WATER_OVERRIDE = true;

    public FireStation(int y, int x, String identifier) {
        super(y,x);
        this.IDENTIFIER = identifier;
    }


    @Override
    public Field burn() {
        return this;
    }

    @Override
    public Field extinguish() {
        return null;
    }

    @Override
    public String toString() {
        return this.IDENTIFIER;
    }

    @Override
    public boolean containsWater() {
        return CONTAINS_WATER_OVERRIDE;
    }
}
