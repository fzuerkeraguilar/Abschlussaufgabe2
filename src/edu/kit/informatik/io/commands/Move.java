package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;

public class Move extends Command{
    public static final String IDENTIFIER = "move";
    public static final int PARAMETER_NUM = 3;
    private static final String SUCCESSFUL_EXECUTION_MESSAGE = "OK";

    public Move(String[] args) {
        super(args);
    }

    @Override
    public String execute(Game game) throws GameException {
        return null;
    }
}
