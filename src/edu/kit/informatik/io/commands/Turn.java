package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;

public class Turn extends Command{
    public static final String IDENTIFIER = "turn";

    public Turn(String[] args) {
        super(args);
    }

    @Override
    public String execute(Game game) throws GameException {
        return null;
    }
}
