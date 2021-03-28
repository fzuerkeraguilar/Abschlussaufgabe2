package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class WetForest extends Forest {
    /**
     *
     */
    public static final String IDENTIFIER = "w";
    private static final boolean EXTINGUISHABLE_OVERRIDE = false;
    private static final String REPRESENTATION = "x";

    /**
     *
     * @param y
     * @param x
     */
    public WetForest(int y, int x) {
        super(y, x);
    }

    @Override
    public Field burn() {
        DryForest dryForest = new DryForest(coordinates.y, coordinates.x);
        for (FireEngine f : fireEngineList) {
            dryForest.addFigure(f);
        }
        dryForest.burned = true;
        return dryForest;
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
    public boolean isExtinguishable() {
        return EXTINGUISHABLE_OVERRIDE;
    }



}
