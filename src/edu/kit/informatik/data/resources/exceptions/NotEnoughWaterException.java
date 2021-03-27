package edu.kit.informatik.data.resources.exceptions;

public class NotEnoughWaterException extends GameException{
    private static final String NOT_ENOUGH_WATER = "%1$s has not enough water";

    public NotEnoughWaterException(String figureIdentifier) {
        this.message = String.format(NOT_ENOUGH_WATER, figureIdentifier);
    }
}
