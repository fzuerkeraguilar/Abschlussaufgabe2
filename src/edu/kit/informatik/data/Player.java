package edu.kit.informatik.data;

import edu.kit.informatik.Application;
import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.Figure;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.ArrayList;

public class Player {
    public final String identifier;
    public boolean alive;
    public final Coordinates fireStationPos;
    private static int reputation;
    private static final ArrayList<FireEngine> fireEngines = new ArrayList<>();


    public Player(String identifier, Coordinates fireStation) {
        this.identifier = identifier;
        this.fireStationPos = fireStation;
        alive = true;
    }

    public void addFigure (FireEngine f) {
        f.setIndex(fireEngines.size());
        fireEngines.add(f);
    }

    public FireEngine getFireEngine(String Identifier) throws FalseFormattingException {
        for(FireEngine f : fireEngines) {
            if(f.identifier.equals(Identifier)) {
                return f;
            }
        }
        throw new FalseFormattingException("identifier not found", "");
    }

    public FireEngine addFireEngine(int y, int x) throws FalseFormattingException {
        if(reputation < 5 ) {
            throw new FalseFormattingException("not enough reputation", "");
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

    public boolean isAlive() {
        if(!alive) return false;
        for(FireEngine fireEngine : fireEngines) {
            if (!fireEngine.destroyed) {
                return true;
            }
        }
        this.alive = false;
        return false;
    }
}
