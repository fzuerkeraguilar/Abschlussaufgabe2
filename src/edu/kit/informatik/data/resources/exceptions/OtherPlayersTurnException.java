package edu.kit.informatik.data.resources.exceptions;

public class OtherPlayersTurnException extends GameException{
    private static final String OTHER_PLAYERS_TURN = "current player is: %1$s";

    public OtherPlayersTurnException(String currentPlayerIdentifier) {
        this.message = String.format(OTHER_PLAYERS_TURN, currentPlayerIdentifier);
    }
}
