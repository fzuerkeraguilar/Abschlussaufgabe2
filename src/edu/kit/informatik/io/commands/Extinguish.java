package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.Arrays;
import java.util.List;

public class Extinguish extends Command{
    public static final String IDENTIFIER = "extinguish";
    public static final int PARAMETER_NUM = 3;
    private static final String GAME_WON = "win";
    private final String fireEngineIdentifier;
    private final Coordinates position;
    private static final int Y_POS = 1;
    private static final int X_POS = 2;
    private static final int ID_POS = 0;

    public Extinguish(List<String> args) throws FalseFormattingException {
        if(args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS","");
        this.fireEngineIdentifier = args.get(ID_POS);
        this.position = new Coordinates(Integer.parseInt(args.get(Y_POS)), Integer.parseInt(args.get(X_POS)));
    }

    @Override
    public String execute(Game game) throws GameException {
        String[] result = game.extinguish(fireEngineIdentifier, position);
        if(result[0] == null) {
            return GAME_WON;
        }
        return Arrays.toString(result);
    }
}
