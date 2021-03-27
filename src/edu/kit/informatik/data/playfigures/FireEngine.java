package edu.kit.informatik.data.playfigures;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.fields.FireStation;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.FigureAlreadyFilledUpException;
import edu.kit.informatik.data.resources.exceptions.FigureDestroyedException;
import edu.kit.informatik.data.resources.exceptions.NotEnoughActionPointsExceptions;
import edu.kit.informatik.data.resources.exceptions.NotEnoughWaterException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FireEngine{
    public Coordinates position;
    public final String identifier;
    public static String REGEX = "([A-D])([0-9]+)";
    public static final int MAX_MOVEMENT = 2;
    public static final int REP_COST = 5;
    private static final int max_water = 3;
    private static final int max_actionPoints = 3;
    private boolean movable = true;
    public boolean destroyed = false;
    private int water;
    private int actionPoints;

    public FireEngine(int y, int x, String identifier) {
        this.position = new Coordinates(y, x);
        this.water = max_water;
        this.actionPoints = max_actionPoints;
        this.identifier = identifier;
    }

    public void newMove() {
        if(this.destroyed) return;
        this.actionPoints = max_actionPoints;
        this.movable = true;
    }

    public boolean isMovable() throws FigureDestroyedException {
        if(this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (!this.movable) {
            return false;
        } else {
            return this.actionPoints > 0;
        }
    }

    public void move(Coordinates destination) throws FigureDestroyedException {
        if(this.destroyed) throw new FigureDestroyedException(this.identifier);
        if (movable) {
            this.position = destination;
            this.actionPoints--;
            this.movable = false;
        }
    }

    public int extinguish () throws NotEnoughWaterException, NotEnoughActionPointsExceptions, FigureDestroyedException {
        if(this.destroyed) throw new FigureDestroyedException(this.identifier);
        Terminal.printLine(this.showInformation());
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

    public void refill () throws NotEnoughActionPointsExceptions, FigureAlreadyFilledUpException, FigureDestroyedException {
        if(this.destroyed) throw new FigureDestroyedException(this.identifier);
        if(actionPoints > 0 ) {
            if(this.water < max_water) {
                this.water = max_water;
                this.actionPoints--;
                return;
            }
            throw new FigureAlreadyFilledUpException(this.identifier);
        }
        throw new NotEnoughActionPointsExceptions(this.identifier);
    }

    public void destroy () {
        destroyed = true;
    }

    public String toString() {
        if(destroyed) {
            return "";
        }
        return this.identifier;
    }



    public boolean canExtinguish() {
        return water > 0 && actionPoints > 0;
    }


    public String showInformation() {
        return this.identifier +
                "," +
                this.water +
                "," +
                this.actionPoints +
                "," +
                this.position.y +
                "," +
                this.position.x;
    }

    public int getActionPoints() {
        return actionPoints;
    }

    public String getOwner() {
        Pattern fireEnginePatter = Pattern.compile(FireEngine.REGEX);
        Matcher test = fireEnginePatter.matcher(this.identifier);
        test.matches();
        return test.group(1);
    }
}
