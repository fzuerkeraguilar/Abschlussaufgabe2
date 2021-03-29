package edu.kit.informatik.data.resources;

import edu.kit.informatik.data.resources.exceptions.GameException;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireEngineExtinguishedFieldAlreadyException extends GameException {
    private static final String MESSAGE = "%1$s has already extinguished field";

    /**
     * Exception to be thrown, when field was already extinguished by fire engine
     * @param identifier identifier of field that was already extinguished by fire engine
     */
    public FireEngineExtinguishedFieldAlreadyException(String identifier) {
        this.message = String.format(MESSAGE, identifier);
    }
}
