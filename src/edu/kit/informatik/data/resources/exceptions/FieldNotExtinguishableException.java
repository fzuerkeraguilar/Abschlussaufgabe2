package edu.kit.informatik.data.resources.exceptions;

public class FieldNotExtinguishableException extends GameException{
    private static final String FIELD_NOT_EXTINGUISHABLE = "%1$s is not extinguishable";

    public FieldNotExtinguishableException(String fieldIdentifier) {
        this.message = String.format(FIELD_NOT_EXTINGUISHABLE, fieldIdentifier);
    }
}
