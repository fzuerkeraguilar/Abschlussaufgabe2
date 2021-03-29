package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a field cannot be extinguished
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldNotExtinguishableException extends GameException {
    private static final String FIELD_NOT_EXTINGUISHABLE = "%1$s is not extinguishable";

    /**
     * Exception to be thrown, when a field cannot be extinguished
     * @param fieldIdentifier identifier of field
     */
    public FieldNotExtinguishableException(String fieldIdentifier) {
        this.message = String.format(FIELD_NOT_EXTINGUISHABLE, fieldIdentifier);
    }
}
