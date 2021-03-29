package edu.kit.informatik.io.resources.exceptions;

import edu.kit.informatik.data.resources.Coordinates;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngineExpectedException extends InputException {
    private static final String FIRE_ENGINE_EXPECTED_MESSAGE = "expected one fire engine at %s";

    /**
     * Exception to be thrown, when a fire engine was expected
     * @param coordinates coordinates at which a fire engine was expected
     */
    public FireEngineExpectedException(Coordinates coordinates) {
        this.message = String.format(FIRE_ENGINE_EXPECTED_MESSAGE, coordinates.toString());
    }
}
