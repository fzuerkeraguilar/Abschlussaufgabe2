package edu.kit.informatik.data.resources.exceptions;

public class NotEnoughReputationException extends GameException{
    private static final String NOT_ENOUGH_REPUTATION = "%1$s has not enough reputation";

    public NotEnoughReputationException(String playerIdentifier) {
        this.message = String.format(NOT_ENOUGH_REPUTATION, playerIdentifier);
    }
}
