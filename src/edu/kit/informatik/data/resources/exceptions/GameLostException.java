package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameLostException extends GameException {
    private static final String GAME_LOST_MESSAGE = "game is lost";

    public GameLostException() {
        this.message = GAME_LOST_MESSAGE;
    }
}
