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
public class ShowField extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "show-field";
    /**
     * number of parameters required
     */
    private static final int PARAMETER_NUM = 2;
    private static final int Y_POS = 0;
    private static final int X_POS = 1;
    private final Coordinates position;

    /**
     *
     * @param args parameters of this command
     * @throws FalseFormattingException
     */
    public ShowField(List<String> args) throws FalseFormattingException {
        if (args.size() != PARAMETER_NUM) {
            throw new FalseFormattingException("false number of arguments", "");
        }
        this.position = new Coordinates(Integer.parseInt(args.get(Y_POS)), Integer.parseInt(args.get(X_POS)));
    }

    @Override
    public String execute(Session session) throws GameException {
        return session.game.showField(this.position);
    }
}
