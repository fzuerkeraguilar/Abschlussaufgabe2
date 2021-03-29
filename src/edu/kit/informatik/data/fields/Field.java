package edu.kit.informatik.data.fields;


import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;

import java.util.ArrayList;
import java.util.Objects;

/**
 * abstract class to model a generic field in a fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class Field {

    private static final String REPRESENTATION = "x";
    private static final boolean BURNS_ALONE = false;
    private static final boolean AVAILABLE_TO_FIRE_ENGINE_DEFAULT = false;
    private static final boolean PASSABLE_TO_FIRE_ENGINE_DEFAULT = false;
    private static final boolean BURNABLE_DEFAULT = false;
    private static final boolean BURNS_OTHER_DEFAULT = false;
    private static final boolean BURNS_DEFAULT = false;
    private static final boolean EXTINGUISHABLE_DEFAULT = false;
    private static final boolean CONTAINS_WATER = false;
    /**
     * coordinates of this field
     */
    public final Coordinates coordinates;
    /**
     * List of all fire engines on this field
     */
    protected final ArrayList<FireEngine> fireEngineList = new ArrayList<>();
    /**
     * flag, if this field war already burned in this round
     */
    protected boolean burned = false;



    /**
     * Constructor for a new field
     * @param y y coordinate of this field
     * @param x x coordinate of this field
     */
    public Field(final int y, final int x) {
        coordinates = new Coordinates(y, x);
    }


    /**
     * Checks if this field is available to fire engine
     * @return if this field is available to fire engine
     */
    public boolean isAvailableToFireEngine() {
        return AVAILABLE_TO_FIRE_ENGINE_DEFAULT;
    }

    /**
     *
     * @return if this field is passable to fire engine
     */
    public boolean isPassableToFireEngine() {
        return PASSABLE_TO_FIRE_ENGINE_DEFAULT;
    }

    /**
     *
     * @return if this field is burnable
     */
    public boolean isBurnable() {
        return !burned && BURNABLE_DEFAULT;
    }

    /**
     *
     * @return if this field can burn other fields
     */
    public boolean burnsOther() {
        return BURNS_OTHER_DEFAULT;
    }

    /**
     *
     * @return if this field is burning
     */
    public boolean burns() {
        return BURNS_DEFAULT;
    }

    /**
     *
     * @return if this field can be extinguished
     */
    public boolean isExtinguishable() {
        return EXTINGUISHABLE_DEFAULT;
    }

    /**
     *
     * @return if field burns alone
     */
    public boolean burnsAlone() {
        return !burned && BURNS_ALONE;
    }

    /**
     *
     * @return if field contains water
     */
    public boolean containsWater() {
        return CONTAINS_WATER;
    }

    /**
     * Resets the flag that this field was burned in this round
     */
    public void resetBurned() {
        this.burned = false;
    }

    /**
     * @return a string representation of this field
     */
    public String toString() {
        return REPRESENTATION;
    }

    /**
     * Adds a fire engine to this field
     * @param figure fire engine to be added
     */
    public void addFigure(final FireEngine figure) {
        this.fireEngineList.add(figure);
    }


    /**
     * Removes all fire engines on this field if their identifier matches the given identifier
     * @param identifier identifier of the fire engine to be removed
     */
    public void removeFigure(String identifier) {
        this.fireEngineList.removeIf(fireEngine -> fireEngine.identifier.equals(identifier));
    }

    /**
     * @return a list of all fire engines on this field
     */
    public ArrayList<FireEngine> getFireEngineList() {
        return fireEngineList;
    }


    /**
     * Returns a more detailed representation of this field and the fire engines on it
     * @return a more detailed representation of this field and the fire engines on it
     */
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

    /**
     * Burns this field
     * @return a burned version of this field
     */
    public Field burn() {
        return this;
    }

    /**
     * Getter for identifier of this field
     * @return the identifier of this field
     */
    public String getIdentifier() {
        return this.toString();
    }

    /**
     * Extinguishes this field
     * @return an extinguished version of this field
     */
    public Field extinguish() {
        return this;
    }

    /**
     * Getter for the burned flag of a field
     * @return if this field was burned during this round
     */
    public boolean isBurned() {
        return this.burned;
    }

    @Override
    public Field clone() {
        return null;
    }
}
