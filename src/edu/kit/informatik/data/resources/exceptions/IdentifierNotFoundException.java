package edu.kit.informatik.data.resources.exceptions;

public class IdentifierNotFoundException extends GameException{
    private static final String IdentifierNotFound = "%s not found";

    public IdentifierNotFoundException(String identifier) {
        this.message =  String.format(IdentifierNotFound, identifier);
    }
}
