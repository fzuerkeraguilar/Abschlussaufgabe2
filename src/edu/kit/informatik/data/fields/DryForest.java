package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

/**
 * Class to model a dry forest in a fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class DryForest extends Forest {
    /**
     * identifier of this field type
     */
    public static final String IDENTIFIER = "d";

    /**
     * Constructor of a dry forest
     * @param y y coordinate of this field
     * @param x x coordinate of this field
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

    @Override
    public DryForest clone() {
        DryForest newDryForest = new DryForest(this.coordinates.y, this.coordinates.x);
        for (FireEngine fireEngine : this.fireEngineList) {
            newDryForest.addFigure(fireEngine.clone());
        }
        return newDryForest;
    }

}
