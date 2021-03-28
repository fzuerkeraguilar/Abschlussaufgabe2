package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public abstract class GameException extends Exception {
    /**
     *
     */
    protected String message;

    @Override
    public String getMessage() {
        return message;
    }
}
