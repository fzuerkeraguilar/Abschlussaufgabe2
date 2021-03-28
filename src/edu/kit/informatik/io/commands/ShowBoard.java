package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class ShowBoard extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "show-board";
    /**
     * number of parameters required
     */
    private static final int PARAMETER_NUM = 0;

    /**
     *
     * @param args parameters of this command
     * @throws FalseFormattingException
     */
    public ShowBoard(List<String> args) throws FalseFormattingException {
        if (args.size() != PARAMETER_NUM) {
            throw new FalseFormattingException("false number of arguments", "");
        }
    }


    @Override
    public String execute(Session session) throws GameException {
        return session.game.toString();
    }
}
