package edu.kit.informatik.data.resources.exceptions;

public class FigureDestroyedException extends GameException{
    private static final String FIGURE_DESTROYED = "%1$s is destroyed";

    public FigureDestroyedException(String figureIdentifier) {
        this.message = String.format(FIGURE_DESTROYED, figureIdentifier);
    }
}
