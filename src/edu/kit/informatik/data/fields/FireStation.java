package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.resources.Coordinates;

/**
 * Class to model a fire station in a fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireStation extends Field {
    /**
     * Regex to identify a fire station
     */
    public static final String IDENTIFIER_REGEX = "[A-D]";
    /**
     * number of fire station needed for the game to start
     */
    public static final int STARTING_NUM = Player.PLAYER_NUM;
    private static final boolean CONTAINS_WATER_OVERRIDE = true;
    /**
     * identifier of this field
     */
    public final String identifier;

    /**
     * Constructor of fire station field
     * @param y y coordinate of this field
     * @param x x coordinate of this field
     * @param identifier identifier of this fire station
     */
    public FireStation(int y, int x, String identifier) {
        super(y, x);
        this.identifier = identifier;
    }

    /**
     * Returns the positions of all fire stations on the board according to the player order
     * @param fieldSize size of the game board
     * @return the positions of all fire stations on the board according to the player order
     */
    public static Coordinates[] getStartingPos(Coordinates fieldSize) {
        Coordinates[] startingPos = new Coordinates[4];
        startingPos[0] = new Coordinates(0, 0);
        startingPos[1] = new Coordinates(fieldSize.y, fieldSize.x);
        startingPos[2] = new Coordinates(fieldSize.y, 0);
        startingPos[3] = new Coordinates(0, fieldSize.x);
        return startingPos;
    }

    @Override
    public Field burn() {
        return this;
    }

    @Override
    public String getIdentifier() {
        return identifier;
    }

    @Override
    public Field extinguish() {
        return null;
    }

    @Override
    public String showInformation() {
        return this.identifier;
    }

    @Override
    public boolean containsWater() {
        return CONTAINS_WATER_OVERRIDE;
    }

    @Override
    public FireStation clone() {
        return new FireStation(this.coordinates.y, this.coordinates.x, this.identifier);
    }
}
