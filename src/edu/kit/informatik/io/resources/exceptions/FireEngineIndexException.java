package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngineIndexException extends InputException {
    private static final String MESSAGE = "%1$s was expected to have index: %2$d";

    /**
     * Exception to be thrown, when a fire engine's index does not match expected value
     * @param identifier identifier of fire engine
     * @param i expected index
     */
    public FireEngineIndexException(String identifier, int i) {
        this.message = String.format(MESSAGE, identifier, i);
    }
}
