package edu.kit.informatik.data.playfigures;

import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.FigureAlreadyFilledUpException;
import edu.kit.informatik.data.resources.exceptions.FigureDestroyedException;
import edu.kit.informatik.data.resources.exceptions.NotEnoughActionPointsExceptions;
import edu.kit.informatik.data.resources.exceptions.NotEnoughWaterException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngine {
    /**
     * regex to match a fire engine identifier
     */
    public static final String REGEX = "([A-D])([0-9]+)";
    /**
     * max fields that a fire engine can move in one turn
     */
    public static final int MAX_MOVEMENT = 2;
    /**
     * reputation cost to buy a fire engine
     */
    public static final int REP_COST = 5;
    private static final int MAX_WATER = 3;
    private static final int MAX_ACTION_POINTS = 3;
    /**
     * position of this fire engine
     */
    public Coordinates position;
    /**
     * identifier of this fire engine
     */
    public final String identifier;
    /**
     * flag for status of this fire engine
     */
    public boolean destroyed = false;
    private boolean movable = true;
    private int water;
    private int actionPoints;

    /**
     * Constuctor for new fire engine
     * @param y starting position
     * @param x starting position
     * @param identifier
     */
    public FireEngine(int y, int x, String identifier) {
        this.position = new Coordinates(y, x);
        this.water = MAX_WATER;
        this.actionPoints = MAX_ACTION_POINTS;
        this.identifier = identifier;
    }

    /**
     * resets this fire engine action points
     */
    public void newMove() {
        if (this.destroyed) return;
        this.actionPoints = MAX_ACTION_POINTS;
        this.movable = true;
    }

    /**
     *
     * @return
     * @throws FigureDestroyedException
     */
    public boolean isMovable() throws FigureDestroyedException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (!this.movable) {
            return false;
        } else {
            return this.actionPoints > 0;
        }
    }

    /**
     *
     * @param destination
     * @throws FigureDestroyedException
     */
    public void move(Coordinates destination) throws FigureDestroyedException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (movable) {
            this.position = destination;
            this.actionPoints--;
        }
    }

    /**
     *
     * @return
     * @throws NotEnoughWaterException
     * @throws NotEnoughActionPointsExceptions
     * @throws FigureDestroyedException
     */
    public int extinguish() throws NotEnoughWaterException, NotEnoughActionPointsExceptions, FigureDestroyedException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (this.water > 0 ) {
            if (this.actionPoints > 0) {
                this.water--;
                this.actionPoints--;
                this.movable = false;
                return this.actionPoints;
            }
            throw new NotEnoughActionPointsExceptions(this.identifier);
        }
        throw new NotEnoughWaterException(this.identifier);
    }

    /**
     *
     * @throws NotEnoughActionPointsExceptions
     * @throws FigureAlreadyFilledUpException
     * @throws FigureDestroyedException
     */
    public void refill() throws NotEnoughActionPointsExceptions,
            FigureAlreadyFilledUpException, FigureDestroyedException {
        if (this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (actionPoints > 0 ) {
            if (this.water < MAX_WATER) {
                this.water = MAX_WATER;
                this.actionPoints--;
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
     *
     * @return
     */
    public String toString() {
        if (destroyed) {
            return "";
        }
        return this.identifier;
    }

    /**
     *
     * @return
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
     *
     * @return
     */
    public int getActionPoints() {
        return actionPoints;
    }

    /**
     *
     * @return
     */
    public String getOwner() {
        Pattern fireEnginePatter = Pattern.compile(FireEngine.REGEX);
        Matcher test = fireEnginePatter.matcher(this.identifier);
        test.matches();
        return test.group(1);
    }

    /**
     *
     * @return
     */
    public int getIndex() {
        Pattern fireEnginePatter = Pattern.compile(FireEngine.REGEX);
        Matcher test = fireEnginePatter.matcher(this.identifier);
        test.matches();
        return Integer.parseInt(test.group(2));
    }
}
