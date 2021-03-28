package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class WrongCommandSequenceException extends GameException {
    private static final String WRONG_COMMAND_SEQUENCE = "wrong command order";

    /**
     *
     */
    public WrongCommandSequenceException() {
        this.message = WRONG_COMMAND_SEQUENCE;
    }
}
