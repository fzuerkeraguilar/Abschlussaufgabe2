package edu.kit.informatik.data.resources.exceptions;

public class PlayerEliminatedException extends GameException{
    private static final String PLAYER_ELIMINATED = "%1$s is eliminated";

    public PlayerEliminatedException(String playerIdentifier) {
        this.message = String.format(PLAYER_ELIMINATED, playerIdentifier);
    }
}
