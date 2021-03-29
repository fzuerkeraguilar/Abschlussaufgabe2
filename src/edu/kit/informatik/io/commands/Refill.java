package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "refill" command
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Refill extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "refill";
    /**
     * number of parameters required
     */
    public static final int PARAMETER_NUM = 1;
    private static final int ID_POS = 0;
    private final String fireEngineIdentifier;


    /**
     * Constructor of refill command
     * checks inputs
     * @param args parameters of this command
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public Refill(List<String> args) throws WrongNumberOfArgumentsException {
        if (args.size() != PARAMETER_NUM ) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
        this.fireEngineIdentifier = args.get(ID_POS);
    }

    @Override
    public String execute(Session session) throws GameException {
        return String.valueOf(session.game.refill(fireEngineIdentifier));
    }
}
