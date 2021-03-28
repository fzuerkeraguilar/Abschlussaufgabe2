package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Quit extends Command {
    public static final String IDENTIFIER = "quit";

    public Quit() {
    }

    @Override
    public String execute(Session session) throws GameException {
        session.setQuit();
        return "";
    }
}
