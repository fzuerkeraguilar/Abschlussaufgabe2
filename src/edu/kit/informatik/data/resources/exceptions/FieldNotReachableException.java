package edu.kit.informatik.data.resources.exceptions;

public class FieldNotReachableException extends GameException{
    private static final String FIELD_NOT_REACHABLE = "%1$s is not reachable for %2$s";

    public FieldNotReachableException(String fieldIdentifier, String figureIdentifier) {
        this.message = String.format(FIELD_NOT_REACHABLE, fieldIdentifier, figureIdentifier);
    }
}
