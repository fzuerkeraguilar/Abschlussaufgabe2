package edu.kit.informatik.io.output;

import edu.kit.informatik.Terminal;

/**
 * Class to be used to interface with Terminal
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Output {
    /**
     * method to print message to user
     * @param message message to be shown
     */
    public void print(String message) {
        Terminal.printLine(message);
    }

    /**
     * method to print error message to user
     * @param message error message to be shown
     */
    public void printError(String message) {
        Terminal.printError(message);
    }
}
