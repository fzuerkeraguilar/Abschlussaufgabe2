package edu.kit.informatik.io.commands;

import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "quit" command
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Quit extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "quit";
    private static final int PARAMETER_NUM = 0;


    /**
     * Constructor of quit command
     * checks inputs
     * @param args parameters of this command
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     */
    public Quit(List<String> args) throws WrongNumberOfArgumentsException {
        if (args.size() != PARAMETER_NUM) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
    }

    @Override
    public String execute(Session session) {
        session.setQuit();
        return "";
    }
}
