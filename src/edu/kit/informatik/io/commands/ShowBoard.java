package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

public class ShowBoard extends Command{
    public static final String IDENTIFIER = "show-board";
    private static final int PARAMETER_NUM = 0;

    public ShowBoard(String[] args) throws FalseFormattingException {
        super(args);
        if(this.arguments.length != PARAMETER_NUM) {
            throw new FalseFormattingException("false number of arguments", "");
        }
    }


    @Override
    public String execute(Game game) throws GameException {
        return game.toString();
    }
}
