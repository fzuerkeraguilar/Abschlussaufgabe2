package edu.kit.informatik.data.resources.exceptions;

public class FigureNotMovableException extends GameException{
    private static final String FIGURE_NOT_MOVABLE = "%1$s cannot move";

    public FigureNotMovableException(String figureException) {
        this.message = String.format(FIGURE_NOT_MOVABLE, figureException);
    }
}
