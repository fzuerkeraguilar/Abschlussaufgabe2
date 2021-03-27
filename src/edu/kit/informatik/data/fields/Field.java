package edu.kit.informatik.data.fields;


import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.IdentifierNotFoundException;

import java.util.ArrayList;
import java.util.Objects;

public abstract class Field {
    private static final String REPRESENTATION = "x";
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

    abstract public String getIdentifier();

    abstract public Field extinguish();

    public String toString() {
        return REPRESENTATION;
    }

    public void addFigure(final FireEngine figure) {
        this.figuresOnField.add(figure);
    }


    public void removeFigure(String identifier) {
        figuresOnField.removeIf(f -> f.identifier.equals(identifier));
    }

    public ArrayList<FireEngine> getFiguresOnField() {
        return figuresOnField;
    }

    public FireEngine getFigure(String IDENTIFIER) throws IdentifierNotFoundException {
        for (FireEngine f : figuresOnField) {
            if(f.identifier.equals(IDENTIFIER)) {
                return f;
            }
        }
        throw new IdentifierNotFoundException(IDENTIFIER);
    }

    public String showInformation() {
        return this.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return coordinates.equals(field.coordinates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(coordinates);
    }
}
