package edu.kit.informatik.data.fields;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class StronglyBurningForest extends Forest {
    /**
     *
     */
    public static final String IDENTIFIER = "*";
    private static final boolean PASSABLE_TO_FIRE_ENGINE_OVERRIDE = false;
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = false;
    private static final boolean BURNS_OTHER_OVERRIDE = true;
    private static final boolean BURNS_OVERRIDE = true;

    /**
     *
     * @param y
     * @param x
     */
    public StronglyBurningForest(int y, int x) {
        super(y, x);
    }

    @Override
    public Field burn() {
        return this;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public boolean burnsOther() {
        return BURNS_OTHER_OVERRIDE;
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
