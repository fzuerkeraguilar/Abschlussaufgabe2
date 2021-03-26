package edu.kit.informatik.io.resources.exceptions;

public class FalsePositionException extends InputException{
    private static final String IDENTIFIER_AT_FALSE_POSITION = "\"%1$s\" is not at expected place";

    public FalsePositionException(String falseField) {
        this.message = String.format(IDENTIFIER_AT_FALSE_POSITION, falseField);
    }
}
