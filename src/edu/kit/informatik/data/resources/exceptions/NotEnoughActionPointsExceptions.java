package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a fire engine does not have enough action points
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class NotEnoughActionPointsExceptions extends GameException {
    private static final String NOT_ENOUGH_ACTION_POINTS = "%1$s has not enough action points";

    /**
     * Exception to be thrown, when a fire engine does not have enough action points
     * @param figureIdentifier identifier of figure
     */
    public NotEnoughActionPointsExceptions(String figureIdentifier) {
        this.message = String.format(NOT_ENOUGH_ACTION_POINTS, figureIdentifier);
    }
}
