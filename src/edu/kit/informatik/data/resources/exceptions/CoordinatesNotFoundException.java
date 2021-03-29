package edu.kit.informatik.data.resources.exceptions;

import edu.kit.informatik.data.resources.Coordinates;

/**
 * Exception to be thrown, when coordinates could not be found in board
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class CoordinatesNotFoundException extends GameException {
    private static final String COORDINATES_NOT_FOUND = "%s not a valid coordinate";

    /**
     * Exception to be thrown, when coordinates could not be found in board
     * @param coordinates coordinates that could not be found
     */
    public CoordinatesNotFoundException(Coordinates coordinates) {
        this.message = String.format(COORDINATES_NOT_FOUND, coordinates.toString());
    }
}
