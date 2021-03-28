package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class DryForest extends Forest {
    /**
     *
     */
    public static final String IDENTIFIER = "d";
    //private static final String REPRESENTATION = "x";

    /**
     *
     * @param y
     * @param x
     */
    public DryForest(int y, int x) {
        super(y, x);
    }

    @Override
    public Field burn() {
        LightlyBurningForest lightlyBurningForest = new LightlyBurningForest(coordinates.y, coordinates.x);
        for (FireEngine f : fireEngineList) {
            lightlyBurningForest.addFigure(f);
        }
        lightlyBurningForest.burned = true;
        return lightlyBurningForest;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Field extinguish() {
        return new WetForest(coordinates.y, coordinates.x);
    }



}
