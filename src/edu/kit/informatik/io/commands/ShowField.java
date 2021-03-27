package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

public class ShowField extends Command{
    public static final String IDENTIFIER = "show-field";
    private static final int PARAMETER_NUM = 2;
    private final Coordinates position;
    private static final int Y_POS = 0;
    private static final int X_POS = 1;

    public ShowField(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM) {
            throw new FalseFormattingException("false number of arguments", "");
        }
        this.position = new Coordinates(Integer.parseInt(args.get(Y_POS)), Integer.parseInt(args.get(X_POS)));
    }

    @Override
    public String execute(Game game) throws GameException {
        return game.showField(this.position);
    }
}
