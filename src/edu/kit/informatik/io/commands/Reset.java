package edu.kit.informatik.io.commands;

import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "reset" command
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
     * Constructor of reset command
     * checks inputs
     * @param args parameters of this command
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public Reset(List<String> args) throws WrongNumberOfArgumentsException {
        if (args.size() != PARAMETER_NUM ) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);

    }

    @Override
    public String execute(Session session) {
        session.reset();
        return SUCCESSFUL_EXECUTION_MESSAGE;
    }
}
