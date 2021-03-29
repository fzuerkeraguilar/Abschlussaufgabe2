package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a field is to distant for a fire engine
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldToDistantException extends GameException {
    private static final String FIELD_TO_DISTANT_MESSAGE = "%1$s is to distant";


    /**
     * Exception to be thrown, when a field is to distant for a fire engine
     * @param fieldIdentifier identifier of field
     */
    public FieldToDistantException(String fieldIdentifier) {
        this.message = String.format(FIELD_TO_DISTANT_MESSAGE, fieldIdentifier);
    }
}
