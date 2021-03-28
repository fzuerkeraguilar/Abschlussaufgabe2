package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class LightlyBurningForest extends Forest {
    /**
     *
     */
    public static final String IDENTIFIER = "+";
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = false;
    private static final boolean BURNS_ALONE_OVERRIDE = true;
    private static final boolean BURNS_OVERRIDE = true;

    /**
     *
     * @param y
     * @param x
     */
    public LightlyBurningForest(int y, int x) {
        super(y, x);
    }

    /**
     *
     * @return
     */
    public Field burn() {
        StronglyBurningForest stronglyBurningForest = new StronglyBurningForest(coordinates.y, coordinates.x);
        for (FireEngine f : fireEngineList) {
            stronglyBurningForest.addFigure(f);
            f.destroy();
        }
        stronglyBurningForest.burned = true;
        return stronglyBurningForest;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public boolean burnsAlone() {
        return !this.burned && BURNS_ALONE_OVERRIDE;
    }

    @Override
    public boolean burns() {
        return BURNS_OVERRIDE;
    }

    @Override
    public Field extinguish() {
        WetForest wetForest = new WetForest(coordinates.y, coordinates.x);
        for (FireEngine fireEngine : fireEngineList) {
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
