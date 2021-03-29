package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class StronglyBurningForestNumberException extends InputException {
    private static final String MESSAGE = "did not find %d strongly burning forests";

    /**
     * Exception to be thrown, when number of strongly burning forests does not match expected number
     * @param expectedNum expected number of strongly burning forests
     */
    public StronglyBurningForestNumberException(int expectedNum) {
        this.message = String.format(MESSAGE, expectedNum);

    }
}
