package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a fire engine is already full of water
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FigureAlreadyFilledUpException extends GameException {
    private static final String FIGURE_DESTROYED = "%1$s is already filled up";

    /**
     * Exception to be thrown, when a fire engine is already full of water
     * @param figureIdentifier identifier of fire engine
     */
    public FigureAlreadyFilledUpException(String figureIdentifier) {
        this.message = String.format(FIGURE_DESTROYED, figureIdentifier);
    }
}
