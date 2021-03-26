package edu.kit.informatik.data.fields;

public class StronglyBurningForest extends Forest{
    public static final String IDENTIFIER = "*";
    private static final boolean PASSABLE_TO_FIRE_ENGINE_OVERRIDE = false;
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = false;
    private static final boolean BURNS_OVERRIDE = true;

    public StronglyBurningForest(int y, int x) {
        super(y, x);
    }

    @Override
    public Field burn() {
        return this;
    }

    @Override
    public boolean burns() {
        return BURNS_OVERRIDE;
    }

    @Override
    public Field extinguish() {
        return new LightlyBurningForest(coordinates.y, coordinates.x);
    }

    @Override
    public String toString() {
        return IDENTIFIER;
    }

    @Override
    public boolean isPassableToFireEngine() {
        return PASSABLE_TO_FIRE_ENGINE_OVERRIDE;
    }

    @Override
    public boolean isAvailableToFireEngine() {
        return AVAILABLE_TO_FIRE_ENGINE_OVERRIDE;
    }
}
