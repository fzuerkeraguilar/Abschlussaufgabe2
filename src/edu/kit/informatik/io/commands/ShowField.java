package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "show-field" command
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
     * Constructor of show-field command
     * checks inputs
     * @param args parameters of this command
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public ShowField(List<String> args) throws WrongNumberOfArgumentsException,
            ValueOutOfRangeException {
        if (args.size() != PARAMETER_NUM) {
            throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
        }
        this.position = new Coordinates(args.get(Y_POS), args.get(X_POS));
    }

    @Override
    public String execute(Session session) throws GameException {
        return session.game.showField(this.position);
    }
}
