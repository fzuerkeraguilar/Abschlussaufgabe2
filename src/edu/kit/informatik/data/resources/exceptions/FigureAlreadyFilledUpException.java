package edu.kit.informatik.data.resources.exceptions;

public class FigureAlreadyFilledUpException extends GameException{
    private static final String FIGURE_DESTROYED = "%1$s is already filled up";

    public FigureAlreadyFilledUpException(String figureIdentifier) {
        this.message = String.format(FIGURE_DESTROYED, figureIdentifier);
    }
}
