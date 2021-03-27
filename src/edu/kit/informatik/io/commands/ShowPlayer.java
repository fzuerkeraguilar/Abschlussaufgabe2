package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

public class ShowPlayer extends Command{
    public static final String IDENTIFIER = "show-player";
    private static final int PARAMETER_NUM = 0;
    private static final int ID_POS = 0;
    //private final String playerIdentifier;

    public ShowPlayer(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS","");
        //playerIdentifier = args.get(ID_POS);
    }

    @Override
    public String execute(Game game) throws GameException {
        return game.showPlayer();
    }
}
