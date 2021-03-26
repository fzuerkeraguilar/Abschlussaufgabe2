package edu.kit.informatik;

import edu.kit.informatik.io.Session;

import java.util.Arrays;


public class Application {

    public static void main(String[] args) {
        final Session session1 = new Session(args);
        session1.run();
    }
}
