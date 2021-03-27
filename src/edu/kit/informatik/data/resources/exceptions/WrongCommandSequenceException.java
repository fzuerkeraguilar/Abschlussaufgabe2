package edu.kit.informatik.data.resources.exceptions;

public class WrongCommandSequenceException extends GameException{
    private static final String WRONG_COMMAND_SEQUENCE = "wrong command order";

    public WrongCommandSequenceException() {
        this.message = WRONG_COMMAND_SEQUENCE;
    }
}
