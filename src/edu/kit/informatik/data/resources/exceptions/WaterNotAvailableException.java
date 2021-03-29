package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when a water is not available for a fire engine
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class WaterNotAvailableException extends GameException {
    private static final String WATER_NOT_AVAILABLE = "water not available";

    /**
     * Exception to be thrown, when a water is not available for a fire engine
     */
    public WaterNotAvailableException() {
        this.message = WATER_NOT_AVAILABLE;
    }
}
