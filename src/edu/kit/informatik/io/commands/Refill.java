package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

public class Refill extends Command{
    public static final String IDENTIFIER = "refill";
    public static final int PARAMETER_NUM = 1;
    private final String fireEngineIdentifier;
    private static final int ID_POS = 0;

    public Refill(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS","");
        this.fireEngineIdentifier = args.get(ID_POS);
    }

    @Override
    public String execute(Game game) throws GameException {
        return String.valueOf(game.refill(fireEngineIdentifier));
    }
}
