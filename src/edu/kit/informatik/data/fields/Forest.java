package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.playfigures.FireEngine;

public abstract class Forest extends Field{
    private static final boolean BURNABLE_OVERRIDE = true;
    private static final boolean PASSABLE_TO_FIRE_ENGINE_OVERRIDE = true;
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_OVERRIDE = true;
    private static final boolean EXTINGUISHABLE_OVERRIDE = true;

    public Forest(int y, int x) {
        super(y, x);
    }

    @Override
    public boolean isPassableToFireEngine() {
        return PASSABLE_TO_FIRE_ENGINE_OVERRIDE;
    }

    @Override
    public boolean isBurnable() {
        return BURNABLE_OVERRIDE;
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
        builder.append(this.toString());
        builder.append(",");
        for(FireEngine f : figuresOnField) {
            if(!f.destroyed) {
                builder.append(f.identifier);
                builder.append(",");
            }
        }
        return builder.substring(0, builder.length() - 1);
    }
}
