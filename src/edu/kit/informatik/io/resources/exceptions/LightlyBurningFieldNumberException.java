package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class LightlyBurningFieldNumberException extends InputException {
    private static final String MESSAGE = "did not find min. %d burning fields";

    /**
     * Exception to be thrown, when number of lightly burning field does not match expected minimum
     * @param expectedNum minimum number of lightly burning fields
     */
    public LightlyBurningFieldNumberException(int expectedNum) {
        this.message = String.format(MESSAGE, expectedNum);
    }
}
