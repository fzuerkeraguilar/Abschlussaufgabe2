package edu.kit.informatik;

import edu.kit.informatik.io.Session;

/**
 * Entry point for FireBreaker Application
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Application {
    /**
     * Entry point
     * @param args start argumentes, to initiate the field
     */
    public static void main(String[] args) {
        final Session session1 = new Session(args);
        session1.run();
    }
}
