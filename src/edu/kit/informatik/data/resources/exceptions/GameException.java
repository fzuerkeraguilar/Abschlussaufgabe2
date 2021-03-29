package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when problem in a game is found
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class GameException extends Exception {
    /**
     * message that should be displayed
     */
    protected String message;

    @Override
    public String getMessage() {
        return message;
    }
}
