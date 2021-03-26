package edu.kit.informatik.data.resources.exceptions;

public class ValueOutOfRangeException extends GameException{
    private static final String VALUE_OUT_OF_EXPECTED_RANGE = "expected value between %1$d and %2$d";

    /**
     * Formats given values into a meaningful error message
     * @param lowerBound the minimum value a given input should be
     * @param upperBound the maximum value a given input should be
     */
    public ValueOutOfRangeException(int lowerBound, int upperBound) {
        message = String.format(VALUE_OUT_OF_EXPECTED_RANGE, lowerBound, upperBound);
    }

    public ValueOutOfRangeException(String msg) {
        message = msg;
    }
}
