package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class ShowPlayer extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "show-player";
    /**
     * number of parameters required
     */
    private static final int PARAMETER_NUM = 0;
    private static final int ID_POS = 0;
    //private final String playerIdentifier;

    /**
     *
     * @param args parameters of this command
     * @throws FalseFormattingException
     */
    public ShowPlayer(List<String> args) throws FalseFormattingException {
        if (args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS", "");
        //playerIdentifier = args.get(ID_POS);
    }

    @Override
    public String execute(Session session) throws GameException {
        return session.game.showPlayer();
    }
}
