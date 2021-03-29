package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "move" command
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Move extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "move";
    /**
     * number of parameters required
     */
    public static final int PARAMETER_NUM = 3;
    private static final int Y_POS = 1;
    private static final int X_POS = 2;
    private static final int ID_POS = 0;
    private static final String SUCCESSFUL_EXECUTION_MESSAGE = "OK";
    private final String fireEngineIdentifier;
    private final Coordinates position;


    /**
     * Constructor of move command
     * checks inputs
     * @param args parameters of this command
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public Move(List<String> args) throws WrongNumberOfArgumentsException,
            ValueOutOfRangeException {
        if (args.size() != PARAMETER_NUM ) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
        this.fireEngineIdentifier = args.get(ID_POS);
        this.position = new Coordinates(args.get(Y_POS), args.get(X_POS));
    }

    @Override
    public String execute(Session session) throws GameException {
        session.game.checkGameStateAndPosition(this.position);
        session.game.move(this.fireEngineIdentifier, this.position);
        return SUCCESSFUL_EXECUTION_MESSAGE;
    }
}
