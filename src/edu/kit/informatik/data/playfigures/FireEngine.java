package edu.kit.informatik.data.playfigures;

import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.fields.FireStation;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

public class FireEngine{
    public Coordinates position;
    public final String identifier;
    public int index;
    public static String REGEX = "([A-D])([0-9]+)";
    public static final double MAX_MOVEMENT = 2;
    public static final int REP_COST = 5;
    private static final int max_water = 3;
    private static final int max_actionPoints = 3;
    private boolean movable = true;
    public boolean destroyed = false;
    private static int water;
    private static int actionPoints;

    public FireEngine(int y, int x, String identifier) {
        position = new Coordinates(y, x);
        actionPoints = max_actionPoints;
        this.identifier = identifier;
    }

    public void newMove() {
        actionPoints = max_actionPoints;
    }

    public boolean isMovable() {
        if (!this.movable) {
            return false;
        } else {
            return actionPoints > 0;
        }
    }

    public void move(Coordinates desination) {
        if (movable) {
            this.position = desination;
            actionPoints--;
        }
    }

    public boolean extinguish() {
        if(water > 0 ) {
            water--;
            movable = false;
            return true;
        } else {
            return false;
        }
    }

    public boolean refill() throws FalseFormattingException {
        if(actionPoints > 0 ) {
            water = max_water;
            return true;
        }
        throw new FalseFormattingException("cannot refill", "");
    }

    public void setDestroyed() {
        destroyed = true;
    }

    public String toString() {
        if(destroyed) {
            return "";
        }
        return this.identifier;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public boolean canExtinguish() {
        return water > 0 && actionPoints > 0;
    }

    public boolean canRefill() {
        return actionPoints > 0 && water < max_water;
    }

    public String showInformation() {
        StringBuilder builder = new StringBuilder();
        builder.append(identifier);
        builder.append(",");
        builder.append(water);
        builder.append(",");
        builder.append(actionPoints);
        builder.append(",");
        builder.append(position.y);
        builder.append(",");
        builder.append(position.x);
        return builder.toString();
    }
}
