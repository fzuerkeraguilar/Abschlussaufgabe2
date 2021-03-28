package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FigureNotMovableException extends GameException {
    private static final String FIGURE_NOT_MOVABLE = "%1$s cannot move";

    /**
     *
     * @param figureException
     */
    public FigureNotMovableException(String figureException) {
        this.message = String.format(FIGURE_NOT_MOVABLE, figureException);
    }
}
