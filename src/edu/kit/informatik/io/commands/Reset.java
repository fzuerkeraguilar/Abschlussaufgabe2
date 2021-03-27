package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

public class Reset extends Command{
    public static final String IDENTIFIER = "reset";
    public static final int PARAMETER_NUM = 0;
    private static final String SUCCESSFUL_EXECUTION_MESSAGE = "OK";

    public Reset(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS","");

    }

    @Override
    public String execute(Game game) throws GameException {
        game = game.reset();
        return SUCCESSFUL_EXECUTION_MESSAGE;
    }
}
