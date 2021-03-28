package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.List;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class BuyFireEngine extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "buy-fire-engine";
    /**
     * number of parameters required
     */
    public static final int PARAMETER_NUM = 2;
    private static final int Y_POS = 0;
    private static final int X_POS = 1;
    private final Coordinates position;

    /**
     *
     * @param args parameters of this command
     * @throws FalseFormattingException
     */
    public BuyFireEngine(List<String> args) throws FalseFormattingException {
        if (args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS", "");
        try {
            position = new Coordinates(Integer.parseInt(args.get(Y_POS)) , Integer.parseInt(args.get(X_POS)));
        } catch (NumberFormatException e) {
            throw new FalseFormattingException(e.getMessage(), "");
        }


    }

    @Override
    public String execute(Session session) throws GameException {
        return String.valueOf(session.game.buyFireEngine(this.position));
    }
}
