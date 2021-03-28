package edu.kit.informatik.data.resources.exceptions;

import edu.kit.informatik.data.resources.Coordinates;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class CoordinatesNotFoundException extends GameException {
    private static final String COORDINATES_NOT_FOUND = "%s not a valid coordinate";

    /**
     *
     * @param coordinates
     */
    public CoordinatesNotFoundException(Coordinates coordinates) {
        this.message = String.format(COORDINATES_NOT_FOUND, coordinates.toString());
    }
}
