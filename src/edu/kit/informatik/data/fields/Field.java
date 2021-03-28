package edu.kit.informatik.data.fields;


import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.IdentifierNotFoundException;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
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
    protected ArrayList<FireEngine> fireEngineList = new ArrayList<>();
    /**
     * flag, if this field war already burned in this round
     */
    protected boolean burned = false;

    /**
     *
     * @param y
     * @param x
     */
    public Field(final int y, final int x) {
        coordinates = new Coordinates(y, x);
    }

    /**
     *
     * @return
     */
    public boolean isAvailableToFireEngine() {
        return AVAILABLE_TO_FIRE_ENGINE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public boolean isPassableToFireEngine() {
        return PASSABLE_TO_FIRE_ENGINE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public boolean isBurnable() {
        return !burned && BURNABLE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public boolean burnsOther() {
        return BURNS_OTHER_DEFAULT;
    }

    public boolean burns() {
        return BURNS_DEFAULT;
    }
    /**
     *
     * @return
     */
    public boolean isExtinguishable() {
        return EXTINGUISHABLE_DEFAULT;
    }

    /**
     *
     * @return
     */
    public boolean burnsAlone() {
        return !burned && BURNS_ALONE;
    }

    /**
     *
     * @return
     */
    public boolean containsWater() {
        return CONTAINS_WATER;
    }

    /**
     *
     * @return
     */
    public void resetBurned() {
        this.burned = false;
    }

    /**
     *
     * @return
     */
    abstract public Field burn();

    /**
     *
     * @return
     */
    abstract public String getIdentifier();

    /**
     *
     * @return
     */
    abstract public Field extinguish();

    /**
     *
     * @return
     */
    public String toString() {
        return REPRESENTATION;
    }

    /**
     *
     * @param figure
     * @return
     */
    public void addFigure(final FireEngine figure) {
        this.fireEngineList.add(figure);
    }


    /**
     *
     * @param identifier
     * @return
     */
    public void removeFigure(String identifier) {
        fireEngineList.removeIf(f -> f.identifier.equals(identifier));
    }
    /**
     *
     * @return
     */

    public ArrayList<FireEngine> getFireEngineList() {
        return fireEngineList;
    }

    /**
     *
     * @param identifier
     * @return
     * @throws IdentifierNotFoundException
     */
    public FireEngine getFigure(String identifier) throws IdentifierNotFoundException {
        for (FireEngine f : fireEngineList) {
            if (f.identifier.equals(identifier)) {
                return f;
            }
        }
        throw new IdentifierNotFoundException(identifier);
    }

    /**
     *
     * @return
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
}
