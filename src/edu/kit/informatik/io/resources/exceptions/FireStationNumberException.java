package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireStationNumberException extends InputException {
    private static final String WRONG_FIRE_STATION_NUMBER_MESSAGE = "expected %d fire station";

    /**
     * Exception to be thrown, when number of fire engines does not match expected number of fire engines
     * @param expectedNumber expected number of fire engines
     */
    public FireStationNumberException(int expectedNumber) {
        this.message = String.format(WRONG_FIRE_STATION_NUMBER_MESSAGE, expectedNumber);
    }
}
