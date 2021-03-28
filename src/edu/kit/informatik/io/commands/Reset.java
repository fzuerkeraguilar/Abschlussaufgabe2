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
public class Reset extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "reset";
    /**
     * number of parameters required
     */
    public static final int PARAMETER_NUM = 0;
    private static final String SUCCESSFUL_EXECUTION_MESSAGE = "OK";

    /**
     *
     * @param args parameters of this command
     * @throws FalseFormattingException
     */
    public Reset(List<String> args) throws FalseFormattingException {
        if (args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS", "");

    }

    //TODO anstat Game Sesstion machen
    @Override
    public String execute(Session session) throws GameException {
        session.game.reset();
        return SUCCESSFUL_EXECUTION_MESSAGE;
    }
}
