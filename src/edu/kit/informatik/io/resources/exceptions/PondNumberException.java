package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class PondNumberException extends InputException {
    private static final String WRONG_POND_NUMBER_MESSAGE = "expected %d ponds";

    /**
     * Exception if number of ponds does not match expected number
     * @param expectedNum expected number of ponds
     */
    public PondNumberException(int expectedNum) {
        this.message = String.format(WRONG_POND_NUMBER_MESSAGE, expectedNum);
    }
}
