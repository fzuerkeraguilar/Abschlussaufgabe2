package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a fire engine does not have enough water
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class NotEnoughWaterException extends GameException {
    private static final String NOT_ENOUGH_WATER = "%1$s has not enough water";

    /**
     * Exception to be thrown, when a fire engine does not have enough water
     * @param figureIdentifier identifier of fire engine
     */
    public NotEnoughWaterException(String figureIdentifier) {
        this.message = String.format(NOT_ENOUGH_WATER, figureIdentifier);
    }
}
