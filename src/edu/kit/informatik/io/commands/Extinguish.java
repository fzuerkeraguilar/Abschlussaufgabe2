package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;

public class Extinguish extends Command{
    public static final String IDENTIFIER = "extinguish";
    public static final int PARAMETER_NUM = 3;
    private static final String GAME_WON = "win";

    public Extinguish(String[] args) {
        super(args);
    }

    @Override
    public String execute(Game game) throws GameException {
        return null;
    }
}
