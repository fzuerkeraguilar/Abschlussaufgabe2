package edu.kit.informatik.io.resources.exceptions;

/**
 * Custom exception to be thrown when certain value is out of given range
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class ValueOutOfRangeException extends InputException {
    private static final String VALUE_OUT_OF_EXPECTED_RANGE = "%1$s should be between %2$d and %3$d";

    /**
     * Exception to be thrown, when a value is out of range
     * Formats given values into a meaningful error message
     * @param value the value, that is not in given range
     * @param lowerBound the minimum value a given input should be
     * @param upperBound the maximum value a given input should be
     */
    public ValueOutOfRangeException(String value, int lowerBound, int upperBound) {
        this.message = String.format(VALUE_OUT_OF_EXPECTED_RANGE, value, lowerBound, upperBound);
    }

    /**
     * Exception to be thrown, when a value is out of range
     * @param value value, that is not in range
     * @param lowerBound the minimum value a given input should be
     * @param upperBound the maximum value a given input should be
     */
    public ValueOutOfRangeException(int value, int lowerBound, int upperBound) {
        this.message = String.format(VALUE_OUT_OF_EXPECTED_RANGE, value, lowerBound, upperBound);
    }

    /**
     * For use with NumberFormatException, while parsing int
     * @param message Message of NumberFormatException
     */
    public ValueOutOfRangeException(String message) {
        this.message = message;
    }
}
