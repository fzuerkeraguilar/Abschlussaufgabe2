package edu.kit.informatik.data.resources.exceptions;

public abstract class GameException extends Exception{
    protected String message;

    @Override
    public String getMessage(){
        return message;
    }
}
