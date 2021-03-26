package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;

public class Refill extends Command{
    public static final String IDENTIFIER = "refill";
    public static final int PARAMETER_NUM = 1;

    public Refill(String[] args) {
        super(args);
    }

    @Override
    public String execute(Game game) throws GameException {
        return null;
    }
}
