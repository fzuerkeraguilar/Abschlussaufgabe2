package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;

/**
 * Abstract Command class that provides the execute method and some useful REGEX for subclasses
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class Command {

    /**
     * Executes the command on given database
     * @param session session on which the command should be executed
     * @return message of successful execution if command executed correctly
     * @throws GameException if the database recognizes a problem with the input
     */
    public abstract String execute(Session session) throws GameException;
}
