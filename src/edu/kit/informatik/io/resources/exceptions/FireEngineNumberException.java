package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngineNumberException extends InputException {
    private static final String WRONG_FIRE_ENGINE_NUMBER_MESSAGE = "expected %d fire engines";

    /**
     * Exception to be thrown, when the number of fire engines does not match the expected number
     * @param fireEngineNumber expected number of fire engines
     */
    public FireEngineNumberException(int fireEngineNumber) {
        this.message = String.format(WRONG_FIRE_ENGINE_NUMBER_MESSAGE, fireEngineNumber);
    }
}
