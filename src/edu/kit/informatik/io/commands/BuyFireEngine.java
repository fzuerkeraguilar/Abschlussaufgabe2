package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;

public class BuyFireEngine extends Command{
    public static final String IDENTIFIER = "buy-fire-engine";
    public static final int PARAMETER_NUM = 2;

    public BuyFireEngine(String[] args) {
        super(args);
    }

    @Override
    public String execute(Game game) throws GameException {
        return null;
    }
}
