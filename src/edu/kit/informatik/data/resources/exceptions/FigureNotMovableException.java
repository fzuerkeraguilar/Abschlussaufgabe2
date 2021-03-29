package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a fire engine cannot be moved
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FigureNotMovableException extends GameException {
    private static final String FIGURE_NOT_MOVABLE = "%1$s cannot move";

    /**
     * Exception to be thrown, when a fire engine cannot be moved
     * @param figureException identifier of figure
     */
    public FigureNotMovableException(String figureException) {
        this.message = String.format(FIGURE_NOT_MOVABLE, figureException);
    }
}
