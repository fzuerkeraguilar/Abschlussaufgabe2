package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class OtherPlayersTurnException extends GameException {
    private static final String OTHER_PLAYERS_TURN = "current player is: %1$s";

    /**
     *
     * @param currentPlayerIdentifier
     */
    public OtherPlayersTurnException(String currentPlayerIdentifier) {
        this.message = String.format(OTHER_PLAYERS_TURN, currentPlayerIdentifier);
    }
}
