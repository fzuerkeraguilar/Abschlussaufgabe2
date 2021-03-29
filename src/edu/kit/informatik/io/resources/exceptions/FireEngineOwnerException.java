package edu.kit.informatik.io.resources.exceptions;

import edu.kit.informatik.data.resources.Coordinates;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngineOwnerException extends InputException {
    private static final String MESSAGE = "%1$s was expected at %2$s";

    /**
     * Exception to be thrown, when fire is not at expected position
     * @param identifier identifier of fire engine at wrong position
     * @param coordinates coordinates of expected position
     */
    public FireEngineOwnerException(String identifier, Coordinates coordinates) {
        this.message = String.format(MESSAGE, identifier, coordinates.toString());
    }
}
