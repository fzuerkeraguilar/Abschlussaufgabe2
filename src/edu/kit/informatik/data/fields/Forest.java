package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

import java.util.Collections;

/**
 * abstract class to model a generic forest in fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class Forest extends Field {
    private static final boolean BURNABLE_OVERRIDE = true;
    private static final boolean PASSABLE_TO_FIRE_ENGINE_OVERRIDE = true;
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = true;
    private static final boolean EXTINGUISHABLE_OVERRIDE = true;

    /**
     * Constructor of a forest field
     * @param y y coordinate of this field
     * @param x x coordinate of this field
     */
    public Forest(int y, int x) {
        super(y, x);
    }


    @Override
    public boolean isPassableToFireEngine() {
        return PASSABLE_TO_FIRE_ENGINE_OVERRIDE;
    }

    @Override
    public boolean isBurnable() {
        return !this.burned && BURNABLE_OVERRIDE;
    }

    @Override
    public boolean isExtinguishable() {
        return EXTINGUISHABLE_OVERRIDE;
    }

    @Override
    public boolean isAvailableToFireEngine() {
        return AVAILABLE_TO_FIRE_ENGINE_OVERRIDE;
    }

    @Override
    public String showInformation() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.getIdentifier());
        builder.append(",");
        Collections.sort(this.fireEngineList);
        for (FireEngine f : this.fireEngineList) {
            if (!f.destroyed) {
                builder.append(f.identifier);
                builder.append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }
}
