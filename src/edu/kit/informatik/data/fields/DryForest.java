package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

public class DryForest extends Forest {
    public static final String IDENTIFIER = "d";

    public DryForest(int y, int x) {
        super(y, x);
    }

    @Override
    public Field burn() {
        LightlyBurningForest lightlyBurningForest = new LightlyBurningForest(coordinates.y, coordinates.x);
        for (FireEngine f : figuresOnField) {
            lightlyBurningForest.addFigure(f);
        }
        return lightlyBurningForest;
    }

    @Override
    public Field extinguish() {
        return new WetForest(coordinates.y, coordinates.x);
    }

    @Override
    public String toString() {
        return IDENTIFIER;
    }


}
