package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

public class BuyFireEngine extends Command{
    public static final String IDENTIFIER = "buy-fire-engine";
    public static final int PARAMETER_NUM = 2;
    private final Coordinates position;
    private static final int Y_POS = 0;
    private static final int X_POS = 1;

    public BuyFireEngine(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS","");
        position = new Coordinates(Integer.parseInt(args.get(Y_POS)) , Integer.parseInt(args.get(X_POS)));

    }

    @Override
    public String execute(Game game) throws GameException {
        return String.valueOf(game.buyFireEngine(this.position));
    }
}
