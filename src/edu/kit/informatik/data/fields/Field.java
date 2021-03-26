package edu.kit.informatik.data.fields;


import edu.kit.informatik.Terminal;
import edu.kit.informatik.data.playfigures.Figure;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.ArrayList;

public abstract class Field {
    public static final String IDENTIFIER = null;
    public final Coordinates coordinates;
    public Field[] adjFields;
    public Field[] diagFields;
    protected ArrayList<FireEngine> figuresOnField = new ArrayList<>();
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_DEFAULT = false;
    private static final boolean PASSABLE_TO_FIRE_ENGINE_DEFAULT = false;
    private static final boolean BURNABLE_DEFAULT = false;
    private static final boolean BURNS_DEFAULT = false;
    private static final boolean EXTINGUISHABLE_DEFAULT = false;
    private static final boolean CONTAINS_WATER = false;


    public Field(final int y, final int x) {
        coordinates = new Coordinates(y, x);
    }

    public boolean isAvailableToFireEngine() {
        return AVAILABLE_TO_FIRE_ENGINE_DEFAULT;
    }

    public boolean isPassableToFireEngine() {
        return PASSABLE_TO_FIRE_ENGINE_DEFAULT;
    }

    public boolean isBurnable() {
        return BURNABLE_DEFAULT;
    }

    public boolean burns() {
        return BURNABLE_DEFAULT;
    }

    public boolean isExtinguishable() {
        return EXTINGUISHABLE_DEFAULT;
    }


    public boolean containsWater() {
        return CONTAINS_WATER;
    }

    abstract public Field burn();

    abstract public Field extinguish();

    abstract public String toString();

    public void addFigure(final FireEngine figure) {
        this.figuresOnField.add(figure);
    }

    public int figureNum() {
        return figuresOnField.size();
    }

    public void moveFigure(String identifier, Field destination) {
        for(FireEngine f : figuresOnField) {
            if(f.identifier.equals(identifier)) {
                destination.addFigure(f);
                this.removeFigure(identifier);
            }
        }
    }

    public void removeFigure(String identifier) {
        figuresOnField.removeIf(f -> f.identifier.equals(identifier));
    }

    public ArrayList<FireEngine> getFiguresOnField() {
        return figuresOnField;
    }

    public FireEngine getFigure(String IDENTIFIER) throws FalseFormattingException {
        for (FireEngine f : figuresOnField) {
            if(f.identifier.equals(IDENTIFIER)) {
                return f;
            }
        }
        throw new FalseFormattingException("IDENTIFIER NOT FOUND", "");
    }

    public String showInformation() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if(!o.getClass().equals(this.getClass())) {
            return false;
        }
        Field f = (Field) o;
        return this.coordinates.equals(f.coordinates);
    }
}
