package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class OnlyOneFieldException extends InputException {
    private static final String ONLY_ONE_FIELD_ALLOWED_MESSAGE = "only one game board allowed";

    /**
     * Exception to be thrown when more than one game board is added
     */
    public OnlyOneFieldException() {
        this.message = ONLY_ONE_FIELD_ALLOWED_MESSAGE;
    }
}
