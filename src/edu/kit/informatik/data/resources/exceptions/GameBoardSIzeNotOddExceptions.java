package edu.kit.informatik.data.resources.exceptions;

public class GameBoardSIzeNotOddExceptions extends GameException {
    private static final String VALUE_NOT_ODD = "game board size should be odd";

    public GameBoardSIzeNotOddExceptions(String value) {
        message = VALUE_NOT_ODD;
    }

    public GameBoardSIzeNotOddExceptions(int i) {
        message = VALUE_NOT_ODD;
    }

}
