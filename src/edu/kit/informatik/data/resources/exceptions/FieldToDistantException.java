package edu.kit.informatik.data.resources.exceptions;

public class FieldToDistantException extends GameException{
    private static final String FIELD_TO_DISTANT = "%1$s is to distant for %2$s";
    private static final String FIELD_TO_DISTANT_1 = "%1$s is to distant";

    public FieldToDistantException(String fieldIdentifier, String figureIdentifier) {
        this.message = String.format(FIELD_TO_DISTANT, fieldIdentifier, figureIdentifier);
    }

    public FieldToDistantException(String fieldIdentifier) {
        this.message = String.format(FIELD_TO_DISTANT_1, fieldIdentifier);
    }
}
