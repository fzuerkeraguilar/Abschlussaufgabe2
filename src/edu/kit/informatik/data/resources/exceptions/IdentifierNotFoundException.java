package edu.kit.informatik.data.resources.exceptions;

/**
 * Exception to be thrown, when an identifier could not be found
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class IdentifierNotFoundException extends GameException {

    private static final String IDENTIFIER_NOT_FOUND = "%s not found with current player";

    /**
     * Exception to be thrown, when an identifier could not be found
     * @param identifier identifier of fire engine, which could not be found
     */
    public IdentifierNotFoundException(String identifier) {
        this.message =  String.format(IDENTIFIER_NOT_FOUND, identifier);
    }
}
