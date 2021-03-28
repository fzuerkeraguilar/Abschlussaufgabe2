package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldNotReachableException extends GameException {
    private static final String FIELD_NOT_REACHABLE = "%1$s is not reachable for %2$s";

    /**
     *
     * @param fieldIdentifier
     * @param figureIdentifier
     */
    public FieldNotReachableException(String fieldIdentifier, String figureIdentifier) {
        this.message = String.format(FIELD_NOT_REACHABLE, fieldIdentifier, figureIdentifier);
    }
}
