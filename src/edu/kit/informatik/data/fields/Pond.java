package edu.kit.informatik.data.fields;

public class Pond extends Field{
    public static final String IDENTIFIER = "L";
    private static final boolean CONTAINS_WATER_OVERRIDE = true;

    public Pond(final int y, final int x) {
        super(y, x);

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
        return IDENTIFIER;
    }

    @Override
    public boolean containsWater() {
        return CONTAINS_WATER_OVERRIDE;
    }

}
