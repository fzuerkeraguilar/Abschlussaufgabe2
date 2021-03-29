package edu.kit.informatik.io.resources.exceptions;

import edu.kit.informatik.data.resources.Coordinates;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FieldNotExpected extends InputException {
    private static final String FIELD_NOT_EXPECTED_MESSAGE = "\"%1$s\" was not expected at \"%2$s\"";

    /**
     * Exception to be thrown, when argument is not at expected place
     * @param falseField identifier of field that was not expected
     * @param position position of field that was not expected
     */
    public FieldNotExpected(String falseField, Coordinates position) {
        this.message = String.format(FIELD_NOT_EXPECTED_MESSAGE, falseField, position.toString());
    }
}
