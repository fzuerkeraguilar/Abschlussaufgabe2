package edu.kit.informatik.data.fields;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireStation extends Field {
    /**
     *
     */
    public static final String IDENTIFIER_REGEX = "[A-D]";
    private static final boolean CONTAINS_WATER_OVERRIDE = true;
    /**
     *
     */
    public final String identifier;

    /**
     *
     * @param y
     * @param x
     * @param identifier
     */
    public FireStation(int y, int x, String identifier) {
        super(y, x);
        this.identifier = identifier;
    }


    @Override
    public Field burn() {
        return this;
    }

    @Override
    public String getIdentifier() {
        return identifier;
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
