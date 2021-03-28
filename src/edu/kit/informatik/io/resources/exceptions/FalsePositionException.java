package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FalsePositionException extends InputException {
    private static final String IDENTIFIER_AT_FALSE_POSITION = "\"%1$s\" is not at expected place";

    /**
     *
     * @param falseField
     */
    public FalsePositionException(String falseField) {
        this.message = String.format(IDENTIFIER_AT_FALSE_POSITION, falseField);
    }
}
