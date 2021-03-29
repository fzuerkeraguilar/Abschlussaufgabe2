package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a command was entered at the wrong time
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class WrongCommandSequenceException extends GameException {
    private static final String WRONG_COMMAND_SEQUENCE = "wrong command order";

    /**
     * Exception to be thrown, when a command was entered at the wrong time
     */
    public WrongCommandSequenceException() {
        this.message = WRONG_COMMAND_SEQUENCE;
    }
}
