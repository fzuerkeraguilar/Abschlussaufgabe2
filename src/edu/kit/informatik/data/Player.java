package edu.kit.informatik.data;

import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.IdentifierNotFoundException;
import edu.kit.informatik.data.resources.exceptions.NotEnoughReputationException;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Class to model a player of fire breaker
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Player {
    /**
     * Number of players at the start
     */
    public static final int PLAYER_NUM = 4;
    /**
     * Identifiers of staring players
     */
    public static final String[] PLAYER_IDENTIFIERS = {"A", "B", "C", "D"};

    private static final int EXTINGUISH_REP_GAIN_DEFAULT = 1;
    /**
     * identifier of this player
     */
    public final String identifier;
    /**
     * flag, if this player is still alive
     */
    public boolean alive;
    /**
     * position of this player's fire station
     */
    public final Coordinates fireStationPos;
    private int reputation;
    private final ArrayList<FireEngine> fireEngines = new ArrayList<>();

    /**
     * Constructor of new Player object
     * @param identifier identifier of new Player
     * @param fireStation position of this players fire station
     */
    public Player(String identifier, Coordinates fireStation) {
        this.identifier = identifier;
        this.fireStationPos = fireStation;
        alive = true;
    }

    /**
     * Adds new fire engine to this player
     * @param fireEngine fire engine to be added
     */
    public void addFireEngine(FireEngine fireEngine) {
        this.fireEngines.add(fireEngine);
    }

    /**
     * Getter for a fire engine of this player
     * @param identifier identifier of wanted fire engine
     * @return fire engine with given identifier
     * @throws IdentifierNotFoundException - if this player has no fire station with given identifier
     */
    public FireEngine getFireEngine(String identifier) throws IdentifierNotFoundException {
        for (FireEngine f : fireEngines) {
            if (f.identifier.equals(identifier)) {
                return f;
            }
        }
        throw new IdentifierNotFoundException(identifier);
    }


    /**
     * Player buys new fire engine
     * @param fireEnginePos position of this new fire engine
     * @return the new fire engine that was added
     * @throws NotEnoughReputationException - if this player doesn't have enough reputation for a new fire engine
     */
    public FireEngine buyFireEngine(Coordinates fireEnginePos) throws NotEnoughReputationException {
        if (reputation < FireEngine.REP_COST ) {
            throw new NotEnoughReputationException(this.identifier);
        }
        FireEngine newFireEngine = new FireEngine(fireEnginePos, identifier + fireEngines.size());
        fireEngines.add(newFireEngine);
        reputation -= FireEngine.REP_COST;
        return newFireEngine;
    }


    /**
     * Checks if this player still has fire engines remaining
     * @return true - if there are fire engine remaining; false - if all fire engines are destroyed
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

    /**
     * Getter for this player's reputation points
     * @return this player's reputation points
     */
    public int getReputation() {
        return reputation;
    }

    /**
     * resets all fire engines of this players for new turn
     */
    public void endTurn() {
        for (FireEngine f: fireEngines) {
            f.newMove();
        }
    }

    /**
     * increases this player's reputation points by default amount
     */
    public void gainRep() {
        this.reputation += Player.EXTINGUISH_REP_GAIN_DEFAULT;
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

    @Override
    public Player clone() {
        Player newPlayer = new Player(this.identifier, this.fireStationPos.clone());
        for (FireEngine fireEngine: this.fireEngines) {
            newPlayer.addFireEngine(fireEngine.clone());
        }
        return newPlayer;
    }
}
