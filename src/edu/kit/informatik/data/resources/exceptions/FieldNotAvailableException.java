package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldNotAvailableException extends GameException {
    private static final String FIELD_NOT_AVAILABLE = "%1$s is not available for %2$s";
    private static final String FIELD_NOT_AVAILABLE_2 = "%1$s is not available";

    /**
     *
     * @param fieldIdentifier
     * @param figureIdentifier
     */
    public FieldNotAvailableException(String fieldIdentifier, String figureIdentifier) {
        this.message =  String.format(FIELD_NOT_AVAILABLE, fieldIdentifier, figureIdentifier);
    }

    /**
     *
     * @param fieldIdentifier
     */
    public FieldNotAvailableException(String fieldIdentifier) {
        this.message = String.format(FIELD_NOT_AVAILABLE_2, fieldIdentifier);
    }
}
