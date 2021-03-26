package edu.kit.informatik.io.output;

import edu.kit.informatik.Terminal;

public class Output {
    public void print(String message) {
        Terminal.printLine(message);
    }

    public void printError(String message) {
        Terminal.printLine(message);
    }
}
