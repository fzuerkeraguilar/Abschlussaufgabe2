package edu.kit.informatik.data.playfigures;


import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.FireEngineExtinguishedFieldAlreadyException;
import edu.kit.informatik.data.resources.exceptions.*;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class to model a fire engine in a fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngine implements Comparable<FireEngine> {
    /**
     * regex to match a fire engine identifier
     */
    public static final String REGEX = "([A-D])(([1-9][0-9]*)|(0))";
    /**
     * max fields that a fire engine can move in one turn
     */
    public static final int MAX_MOVEMENT = 2;
    /**
     * reputation cost to buy a fire engine
     */
    public static final int REP_COST = 5;
    /**
     * number of fire engines at start of game
     */
    public static final int STARTING_NUM = 4;
    /**
     *
     */
    public static final int STARTING_INDEX = 0;
    private static final int MAX_WATER = 3;
    private static final int MAX_ACTION_POINTS = 3;
    /**
     * identifier of this fire engine
     */
    public final String identifier;
    /**
     * flag for status of this fire engine
     */
    public boolean destroyed = false;
    /**
     * position of this fire engine
     */
    public Coordinates position;
    private final ArrayList<Coordinates> extinguishedFields = new ArrayList<>();
    private boolean movable = true;
    private int water;
    private int actionPoints;

    /**
     * Constructor for new fire engine
     * @param position starting position
     * @param identifier identifier this fire engine should have
     */
    public FireEngine(Coordinates position, String identifier) {
        this.position = position;
        this.water = MAX_WATER;
        this.actionPoints = MAX_ACTION_POINTS;
        this.identifier = identifier;
    }

    /**
     * Returns the specified starting position of all fire engines on the game board in order of their respective owners
     * @param fieldSize size of the game board
     * @return the specified starting position of all fire engines on the game board in order of their respective owners
     */
    public static Coordinates[] startingPos(Coordinates fieldSize) {
        Coordinates[] startingPos = new Coordinates[4];
        startingPos[0] = new Coordinates(1, 1);
        startingPos[1] = new Coordinates(fieldSize.y - 1, fieldSize.x - 1);
        startingPos[2] = new Coordinates(fieldSize.y - 1, 1);
        startingPos[3] = new Coordinates(1, fieldSize.x - 1);
        return startingPos;
    }

    /**
     * resets this fire for a new move
     */
    public void newMove() {
        if (this.destroyed) return;
        this.actionPoints = MAX_ACTION_POINTS;
        this.movable = true;
        this.extinguishedFields.clear();
    }


    /**
     * Updates the position of this fire engine
     * @param destination new position of this fire engine
     * @throws FigureDestroyedException - if this fire engine is destroyed
     */
    public void move(Coordinates destination) throws FigureDestroyedException, FigureNotMovableException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (this.actionPoints <= 0) throw new FigureNotMovableException(this.identifier);
        if (!this.movable) throw new FigureNotMovableException(this.identifier);
        this.position = destination;
        this.actionPoints--;
    }

    /**
     * Checks if this fire engine can extinguish and if so updates its resources
     * @param coordinates coordinates of field that should be extinguished
     * @return the remaining action points of this fire engine
     * @throws NotEnoughWaterException - if this fire engine does not have enough water
     * @throws NotEnoughActionPointsExceptions - if this fire engine does not have enough action points
     * @throws FigureDestroyedException - if this fire engine is destroyed
     * @throws FireEngineExtinguishedFieldAlreadyException - given field was already extinguished by this fire engine
     * during this round
     */
    public int extinguish(Coordinates coordinates) throws NotEnoughWaterException,
            NotEnoughActionPointsExceptions, FigureDestroyedException, FireEngineExtinguishedFieldAlreadyException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (this.extinguishedFields.contains(coordinates)) {
            throw new FireEngineExtinguishedFieldAlreadyException(this.identifier);
        }
        if (this.water > 0 ) {
            if (this.actionPoints > 0) {
                this.water--;
                this.actionPoints--;
                this.movable = false;
                this.extinguishedFields.add(coordinates);
                return this.actionPoints;
            }
            throw new NotEnoughActionPointsExceptions(this.identifier);
        }
        throw new NotEnoughWaterException(this.identifier);
    }

    /**
     * Refills the water supply of this fire engine
     * @throws NotEnoughActionPointsExceptions  - if this fire engine does not have enough action points
     * @throws FigureAlreadyFilledUpException  - if this fire engine was already filled up
     * @throws FigureDestroyedException - if this fire engine is destroyed
     */
    public void refill() throws NotEnoughActionPointsExceptions,
            FigureAlreadyFilledUpException, FigureDestroyedException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (actionPoints > 0 ) {
            if (this.water < MAX_WATER) {
                this.water = MAX_WATER;
                this.actionPoints--;
                this.movable = false;
                return;
            }
            throw new FigureAlreadyFilledUpException(this.identifier);
        }
        throw new NotEnoughActionPointsExceptions(this.identifier);
    }

    /**
     *
     */
    public void destroy() {
        destroyed = true;
    }

    /**
     * toString method of a fire engine
     * @return the identifier of this fire engine if it is not destroyed
     */
    public String toString() {
        if (destroyed) {
            return "";
        }
        return this.identifier;
    }

    /**
     * shows the resources of this fire engine
     * @return a detailed view of the resources of this fire engine if it is not destroyed
     */
    public String showInformation() {
        if (this.destroyed) return "";
        return this.identifier
                + ","
                + this.water
                + ","
                + this.actionPoints
                + ","
                + this.position.y
                + ","
                + this.position.x;
    }

    /**
     * Getter for this fire engine's action points
     * @return the remaining action points of this fire engine
     */
    public int getActionPoints() {
        return this.actionPoints;
    }

    /**
     * Getter of the identifier of this fire engine's owner
     * @return the identifier of this fire engine's owner, null - if this fire engine's identifier was not set correctly
     */
    public String getOwner() {
        Pattern fireEnginePatter = Pattern.compile(FireEngine.REGEX);
        Matcher test = fireEnginePatter.matcher(this.identifier);
        if (test.matches()) return test.group(1);
        return null;
    }

    /**
     * Getter for this fire engine's index
     * @return this fire engine's index, 0 - if this fire engine's identifier was not set correctly
     */
    public int getIndex() {
        Pattern fireEnginePatter = Pattern.compile(FireEngine.REGEX);
        Matcher test = fireEnginePatter.matcher(this.identifier);
        if (test.matches()) return Integer.parseInt(test.group(2));
        return 0;
    }

    @Override
    public FireEngine clone() {
        return new FireEngine(this.position.clone(), this.identifier);
    }

    @Override
    public int compareTo(FireEngine o) {
        return this.identifier.compareTo(o.identifier);
    }
}
