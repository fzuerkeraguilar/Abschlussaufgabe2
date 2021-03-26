package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

public class LightlyBurningForest extends Forest {
    public static final String IDENTIFIER = "+";
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = false;
    private static final boolean BURNS_OVERRIDE = true;

    public LightlyBurningForest(int y, int x) {
        super(y, x);
    }


    public Field burn() {
        StronglyBurningForest stronglyBurningForest = new StronglyBurningForest(coordinates.y, coordinates.x);
        for(FireEngine f : figuresOnField) {
            stronglyBurningForest.addFigure(f);
            f.setDestroyed();
        }
        return stronglyBurningForest;
    }

    @Override
    public boolean burns() {
        return BURNS_OVERRIDE;
    }

    @Override
    public Field extinguish() {
        WetForest wetForest = new WetForest(coordinates.y, coordinates.x);
        for (FireEngine fireEngine : figuresOnField) {
            wetForest.addFigure(fireEngine);
        }
        return wetForest;
    }

    @Override
    public String toString() {
        return IDENTIFIER;
    }

    @Override
    public boolean isAvailableToFireEngine() {
        return AVAILABLE_TO_FIRE_ENGINE_OVERRIDE;
    }
}
