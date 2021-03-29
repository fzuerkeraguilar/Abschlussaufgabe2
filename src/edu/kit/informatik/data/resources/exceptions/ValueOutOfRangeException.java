package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a value does not fall in a given range
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class ValueOutOfRangeException extends GameException {
    private static final String VALUE_OUT_OF_EXPECTED_RANGE = "expected value between %1$d and %2$d";

    /**
     * Exception to be thrown, when a value does not fall in a given range
     * Formats given values into a meaningful error message
     * @param lowerBound the minimum value a given input should be
     * @param upperBound the maximum value a given input should be
     */
    public ValueOutOfRangeException(int lowerBound, int upperBound) {
        message = String.format(VALUE_OUT_OF_EXPECTED_RANGE, lowerBound, upperBound);
    }

    /**
     * Exception to be thrown, a coordinate could not be parsed as int
     * @param msg message to be shown to the user
     */
    public ValueOutOfRangeException(String msg) {
        message = msg;
    }
}
