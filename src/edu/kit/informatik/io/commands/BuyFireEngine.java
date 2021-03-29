package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "buy-fire-engine" command
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class BuyFireEngine extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "buy-fire-engine";
    /**
     * number of parameters required
     */
    public static final int PARAMETER_NUM = 2;
    private static final int Y_POS = 0;
    private static final int X_POS = 1;
    private final Coordinates position;

    /**
     * Constructor for buy-fire-engine command
     * checks inputs
     * @param args parameters of this command
     * @throws ValueOutOfRangeException - if arguments could not be parsed as integers
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public BuyFireEngine(List<String> args) throws WrongNumberOfArgumentsException, ValueOutOfRangeException {
        if (args.size() != PARAMETER_NUM ) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
        this.position = new Coordinates(args.get(Y_POS) , args.get(X_POS));


    }

    @Override
    public String execute(Session session) throws GameException {
        session.game.checkGameStateAndPosition(this.position);
        return String.valueOf(session.game.buyFireEngine(this.position));
    }
}
