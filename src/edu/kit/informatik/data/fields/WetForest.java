package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

public class WetForest extends Forest{
    public static final String IDENTIFIER = "w";
    private static final boolean EXTINGUISHABLE_OVERRIDE = false;

    public WetForest(int y, int x) {
        super(y, x);
    }

    @Override
    public Field burn() {
        DryForest dryForest = new DryForest(coordinates.y, coordinates.x);
        for (FireEngine f : figuresOnField) {
            dryForest.addFigure(f);
        }
        return dryForest;
    }

    @Override
    public Field extinguish() {
        return null;
    }

    @Override
    public boolean isExtinguishable() {
        return EXTINGUISHABLE_OVERRIDE;
    }

    @Override
    public String toString() {
        return IDENTIFIER;
    }


}
