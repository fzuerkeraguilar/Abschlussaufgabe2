package edu.kit.informatik.data.resources.exceptions;

public class WaterNotAvailableException extends GameException{
    private static final String WATER_NOT_AVAILABLE = "water not available";

    public WaterNotAvailableException() {
        this.message = WATER_NOT_AVAILABLE;
    }
}