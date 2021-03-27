package edu.kit.informatik.data;

import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.IdentifierNotFoundException;
import edu.kit.informatik.data.resources.exceptions.NotEnoughReputationException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.ArrayList;
import java.util.Objects;

public class Player {
    public final String identifier;
    public boolean alive;
    public final Coordinates fireStationPos;
    private int reputation;
    private final ArrayList<FireEngine> fireEngines = new ArrayList<>();


    public Player(String identifier, Coordinates fireStation) {
        this.identifier = identifier;
        this.fireStationPos = fireStation;
        alive = true;
    }

    public void addFigure (FireEngine f) {
        this.fireEngines.add(f);
    }

    public FireEngine getFireEngine(String Identifier) throws IdentifierNotFoundException {
        for(FireEngine f : fireEngines) {
            if(f.identifier.equals(Identifier)) {
                return f;
            }
        }
        throw new IdentifierNotFoundException(identifier);
    }

    public FireEngine addFireEngine(int y, int x) throws NotEnoughReputationException {
        if(reputation < 5 ) {
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
        for(FireEngine f: fireEngines) {
            if(!f.destroyed) {
                builder.append(f.showInformation());
                builder.append(System.lineSeparator());
            }
        }
        return builder.substring(0, builder.length() - 1);
    }

    public boolean checkIfAlive() {
        if(!alive) return false;
        for(FireEngine fireEngine : fireEngines) {
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

    public int getReputation() {
        return reputation;
    }

    public void endTurn() {
        for(FireEngine f: fireEngines) {
            f.newMove();
        }
    }
}
