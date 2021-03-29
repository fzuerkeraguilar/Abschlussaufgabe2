package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when the board size is not odd in at least one dimension
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameBoardSizeNotOddExceptions extends GameException {
    private static final String VALUE_NOT_ODD = "game board size should be odd";


    /**
     * Exception to be thrown, when the board size is not odd in at least one dimension
     */
    public GameBoardSizeNotOddExceptions() {
        message = VALUE_NOT_ODD;
    }

}
