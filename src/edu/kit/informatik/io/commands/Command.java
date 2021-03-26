package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;

/**
 * Abstract Command class that provides the execute method and some useful REGEX for subclasses
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class Command {
    /**
     * Regex that matches a identifier of an escape network
     */
    protected static final String REGEX_NETWORK_IDENTIFIER = "[A-Z]{1,6}";
    /**
     * Regex that matches a identifier of an escape section
     */
    protected static final String REGEX_NODE_IDENTIFIER = "([a-z]{1,6})";
    /**
     * Regex that matches the capacity of an escape section
     */
    protected static final String REGEX_WHOLE_NUMBER = "([0-9]+)";
    /**
     * Regex that matches the string representation of an escape section
     */
    protected static final String REGEX_EDGE = REGEX_NODE_IDENTIFIER  + REGEX_WHOLE_NUMBER + REGEX_NODE_IDENTIFIER;

    protected final String[] arguments;

    public Command(String[] args) {
        arguments = args;
    }

    /**
     * Executes the command on given database
     * @param game Game on which the command should be executed
     * @return message of successful execution if command executed correctly
     * @throws GameException if the database recognizes a problem with the input
     */
    public abstract String execute(Game game) throws GameException;
}
