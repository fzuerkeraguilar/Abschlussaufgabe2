package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldToDistantException extends GameException {
    private static final String FIELD_TO_DISTANT = "%1$s is to distant for %2$s";
    private static final String FIELD_TO_DISTANT_1 = "%1$s is to distant";

    /**
     *
     * @param fieldIdentifier
     * @param figureIdentifier
     */
    public FieldToDistantException(String fieldIdentifier, String figureIdentifier) {
        this.message = String.format(FIELD_TO_DISTANT, fieldIdentifier, figureIdentifier);
    }

    /**
     *
     * @param fieldIdentifier
     */
    public FieldToDistantException(String fieldIdentifier) {
        this.message = String.format(FIELD_TO_DISTANT_1, fieldIdentifier);
    }
}
