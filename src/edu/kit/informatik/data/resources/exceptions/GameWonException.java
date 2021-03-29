package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when problem in a game is already won
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameWonException extends GameException {
    private static final String GAME_WON_MESSAGE = "game is won";

    /**
     * Exception to be thrown, when game is already won
     */
    public GameWonException() {
        this.message = GAME_WON_MESSAGE;
    }
}
