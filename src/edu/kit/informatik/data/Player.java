package edu.kit.informatik.data;

import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.IdentifierNotFoundException;
import edu.kit.informatik.data.resources.exceptions.NotEnoughReputationException;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Player {
    /**
     *
     */
    public static final int EXTINGUISH_REP_GAIN = 1;
    /**
     *
     */
    public final String identifier;
    /**
     *
     */
    public boolean alive;
    /**
     *
     */
    public final Coordinates fireStationPos;
    private int reputation;
    private final ArrayList<FireEngine> fireEngines = new ArrayList<>();

    /**
     *
     * @param identifier
     * @param fireStation
     */
    public Player(String identifier, Coordinates fireStation) {
        this.identifier = identifier;
        this.fireStationPos = fireStation;
        alive = true;
    }

    /**
     *
     * @param f
     */
    public void addFigure(FireEngine f) {
        this.fireEngines.add(f);
    }

    /**
     *
     * @param identifier
     * @return
     * @throws IdentifierNotFoundException
     */
    public FireEngine getFireEngine(String identifier) throws IdentifierNotFoundException {
        for (FireEngine f : fireEngines) {
            if (f.identifier.equals(identifier)) {
                return f;
            }
        }
        throw new IdentifierNotFoundException(this.identifier);
    }

    /**
     *
     * @param y
     * @param x
     * @return
     * @throws NotEnoughReputationException
     */
    public FireEngine addFireEngine(int y, int x) throws NotEnoughReputationException {
        if (reputation < 5 ) {
            throw new NotEnoughReputationException(this.identifier);
        }
        FireEngine newFireEngine = new FireEngine(y, x, identifier + fireEngines.size());
        fireEngines.add(newFireEngine);
        reputation -= FireEngine.REP_COST;
        return newFireEngine;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(identifier);
        builder.append(",");
        builder.append(reputation);
        builder.append(System.lineSeparator());
        for (FireEngine f: fireEngines) {
            if (!f.destroyed) {
                builder.append(f.showInformation());
                builder.append(System.lineSeparator());
            }
        }
        return builder.substring(0, builder.length() - 1);
    }

    /**
     *
     * @return
     */
    public boolean checkIfAlive() {
        if (!alive) return false;
        for (FireEngine fireEngine : fireEngines) {
            if (!fireEngine.destroyed) {
                return true;
            }
        }
        this.alive = false;
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return Objects.equals(identifier, player.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    /**
     *
     * @return
     */
    public int getReputation() {
        return reputation;
    }

    /**
     *
     */
    public void endTurn() {
        for (FireEngine f: fireEngines) {
            f.newMove();
        }
    }

    /**
     *
     * @param gain
     */
    public void gainRep(int gain) {
        this.reputation += gain;
    }
}
