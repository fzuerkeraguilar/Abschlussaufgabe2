package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameBoardSizeNotOddExceptions extends GameException {
    private static final String VALUE_NOT_ODD = "game board size should be odd";
    //TODO

    /**
     *
     * @param value
     */
    public GameBoardSizeNotOddExceptions(String value) {
        message = VALUE_NOT_ODD;
    }

    /**
     *
     * @param i
     */
    public GameBoardSizeNotOddExceptions(int i) {
        message = VALUE_NOT_ODD;
    }

}
