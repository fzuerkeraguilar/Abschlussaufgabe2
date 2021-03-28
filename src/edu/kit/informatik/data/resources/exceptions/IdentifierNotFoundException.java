package edu.kit.informatik.data.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class IdentifierNotFoundException extends GameException {

    private static final String IDENTIFIER_NOT_FOUND = "%s not found";

    /**
     *
     * @param identifier
     */
    public IdentifierNotFoundException(String identifier) {
        this.message =  String.format(IDENTIFIER_NOT_FOUND, identifier);
    }
}
