package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

public class Turn extends Command{
    public static final String IDENTIFIER = "turn";
    private static final int PARAMETER_NUM = 0;

    public Turn(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM) {
            throw new FalseFormattingException("false number of arguments", "");
        }
    }

    @Override
    public String execute(Game game) throws GameException {
        return game.turn();
    }
}
