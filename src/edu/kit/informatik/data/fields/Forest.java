package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class Forest extends Field {
    private static final boolean BURNABLE_OVERRIDE = true;
    private static final boolean PASSABLE_TO_FIRE_ENGINE_OVERRIDE = true;
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = true;
    private static final boolean EXTINGUISHABLE_OVERRIDE = true;

    /**
     *
     * @param y
     * @param x
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
        for (FireEngine f : fireEngineList) {
            if (!f.destroyed) {
                builder.append(f.identifier);
                builder.append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }
}
