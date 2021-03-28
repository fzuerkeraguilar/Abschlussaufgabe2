package edu.kit.informatik.data.fields;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Pond extends Field {
    /**
     *
     */
    public static final String IDENTIFIER = "L";
    private static final boolean CONTAINS_WATER_OVERRIDE = true;

    /**
     *
     * @param y
     * @param x
     */
    public Pond(final int y, final int x) {
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
    public Field extinguish() {
        return null;
    }


    @Override
    public boolean containsWater() {
        return CONTAINS_WATER_OVERRIDE;
    }

}
