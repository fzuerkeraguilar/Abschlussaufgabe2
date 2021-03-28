package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class PlayerEliminatedException extends GameException {
    private static final String PLAYER_ELIMINATED = "%1$s is eliminated";

    /**
     *
     * @param playerIdentifier
     */
    public PlayerEliminatedException(String playerIdentifier) {
        this.message = String.format(PLAYER_ELIMINATED, playerIdentifier);
    }
}
