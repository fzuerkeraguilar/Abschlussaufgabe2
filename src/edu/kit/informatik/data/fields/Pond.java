package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.resources.Coordinates;

/**
 * Class to model a pond in a fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Pond extends Field {
    /**
     * Identifier of ponds
     */
    public static final String IDENTIFIER = "L";
    /**
     * Amount of ponds on board
     */
    public static final int STARTING_NUM = 4;
    private static final boolean CONTAINS_WATER_OVERRIDE = true;

    /**
     * Constructor of a pond field
     * @param y y coordinate of this field
     * @param x x coordinate of this field
     */
    public Pond(final int y, final int x) {
        super(y, x);

    }

    /**
     * Returns the positions of all ponds on the board in clock wise order
     * @param fieldSize size of the game board
     * @return the positions of all ponds on the board in clock wise order
     */
    public static Coordinates[] startingPos(Coordinates fieldSize) {
        Coordinates[] startingPos = new Coordinates[4];
        startingPos[0] = new Coordinates(0, fieldSize.x / 2);
        startingPos[1] = new Coordinates(fieldSize.y  / 2 , fieldSize.x);
        startingPos[2] = new Coordinates(fieldSize.y, fieldSize.x / 2 );
        startingPos[3] = new Coordinates(fieldSize.y  / 2, 0);
        return startingPos;
    }

    @Override
    public Field burn() {
        return this;
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public Field extinguish() {
        return null;
    }

    @Override
    public String showInformation() {
        return IDENTIFIER;
    }

    @Override
    public boolean containsWater() {
        return CONTAINS_WATER_OVERRIDE;
    }

    @Override
    public Pond clone() {
        return new Pond(this.coordinates.y, this.coordinates.x);
    }
}
