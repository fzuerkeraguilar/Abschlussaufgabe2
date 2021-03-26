package edu.kit.informatik.data.fields;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.commands.Command;

public class Quit extends Command {
    public Quit(String[] args) {
        super(args);
    }

    @Override
    public String execute(Game game) throws GameException {
        return null;
    }
}
