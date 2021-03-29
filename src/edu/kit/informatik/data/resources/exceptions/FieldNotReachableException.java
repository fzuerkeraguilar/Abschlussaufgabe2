package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a field cannot be reached with a fire engine
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldNotReachableException extends GameException {
    private static final String FIELD_NOT_REACHABLE = "%1$s is not reachable for %2$s";

    /**
     * Exception to be thrown, when a field cannot be reached with a fire engine
     * @param fieldIdentifier identifier of field
     * @param figureIdentifier identifier of fire engine
     */
    public FieldNotReachableException(String fieldIdentifier, String figureIdentifier) {
        this.message = String.format(FIELD_NOT_REACHABLE, fieldIdentifier, figureIdentifier);
    }
}
