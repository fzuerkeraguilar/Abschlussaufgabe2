package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a field is not available to a fire engine
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldNotAvailableException extends GameException {
    private static final String FIELD_NOT_AVAILABLE_MESSAGE = "%1$s is not available";


    /**
     * Exception to be thrown, when a field is not available to a fire engine
     * @param fieldIdentifier identifier of field that could not be accessed
     */
    public FieldNotAvailableException(String fieldIdentifier) {
        this.message = String.format(FIELD_NOT_AVAILABLE_MESSAGE, fieldIdentifier);
    }
}
