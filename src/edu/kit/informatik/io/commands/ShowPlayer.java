package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "show-player" command
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class ShowPlayer extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "show-player";
    /**
     * number of parameters required
     */
    private static final int PARAMETER_NUM = 0;

    /**
     * Constructor of show-player command
     * checks inputs
     * @param args parameters of this command
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public ShowPlayer(List<String> args) throws WrongNumberOfArgumentsException {
        if (args.size() != PARAMETER_NUM ) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
    }

    @Override
    public String execute(Session session) throws GameException {
        return session.game.showPlayer();
    }
}
