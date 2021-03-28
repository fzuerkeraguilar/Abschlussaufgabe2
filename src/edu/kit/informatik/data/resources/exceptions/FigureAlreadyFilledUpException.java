package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FigureAlreadyFilledUpException extends GameException {
    private static final String FIGURE_DESTROYED = "%1$s is already filled up";

    /**
     *
     * @param figureIdentifier
     */
    public FigureAlreadyFilledUpException(String figureIdentifier) {
        this.message = String.format(FIGURE_DESTROYED, figureIdentifier);
    }
}
