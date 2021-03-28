package edu.kit.informatik.io.output;

import edu.kit.informatik.Terminal;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Output {
    /**
     *
     * @param message
     */
    public void print(String message) {
        Terminal.printLine(message);
    }

    /**
     *
     * @param message
     */
    public void printError(String message) {
        Terminal.printError(message);
    }
}
