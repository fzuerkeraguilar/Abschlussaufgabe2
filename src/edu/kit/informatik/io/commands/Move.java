package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

/**
 *
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
     *
     * @param args parameters of this command
     * @throws FalseFormattingException
     */
    public Move(List<String> args) throws FalseFormattingException {
        if (args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS", "");
        this.fireEngineIdentifier = args.get(ID_POS);
        this.position = new Coordinates(Integer.parseInt(args.get(Y_POS)), Integer.parseInt(args.get(X_POS)));
    }

    @Override
    public String execute(Session session) throws GameException {
        session.game.move(this.fireEngineIdentifier, this.position);
        return SUCCESSFUL_EXECUTION_MESSAGE;
    }
}
