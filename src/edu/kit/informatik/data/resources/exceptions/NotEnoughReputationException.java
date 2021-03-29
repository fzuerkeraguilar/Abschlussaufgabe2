package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a player does not have enough reputation points
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class NotEnoughReputationException extends GameException {
    private static final String NOT_ENOUGH_REPUTATION = "%1$s has not enough reputation";

    /**
     * Exception to be thrown, when a player does not have enough reputation points
     * @param playerIdentifier identifier of figure
     */
    public NotEnoughReputationException(String playerIdentifier) {
        this.message = String.format(NOT_ENOUGH_REPUTATION, playerIdentifier);
    }
}
