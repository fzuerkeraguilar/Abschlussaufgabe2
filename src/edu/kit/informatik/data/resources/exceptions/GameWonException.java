package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameWonException extends GameException {
    private static final String GAME_WON_MESSAGE = "game is won";

    public GameWonException() {
        this.message = GAME_WON_MESSAGE;
    }
}
