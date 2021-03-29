package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.Session;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;
import edu.kit.informatik.io.resources.exceptions.ValueOutOfRangeException;
import edu.kit.informatik.io.resources.exceptions.WrongNumberOfArgumentsException;

import java.util.List;

/**
 * Class to manage the "fire-to-roll" command
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class FireToRoll extends Command {
    /**
     * identifier of this command
     */
    public static final String IDENTIFIER = "fire-to-roll";
    /**
     * number of parameters required
     */
    public static final int PARAMETER_NUM = 1;
    private static final int MIN_ROLL = 1;
    private static final int MAX_ROLL = 6;
    private static final int ROLL_POS = 0;
    private static final String GAME_LOST_MESSAGE = "lose";
    private static final String GAME_NOT_LOST_MESSAGE = "OK";
    private final int roll;

    /**
     * constructor of fire-to-roll command object
     * checks inputs
     * @param args parameters of this command
     * @throws ValueOutOfRangeException - if number of parameters does not match expected number
     * @throws WrongNumberOfArgumentsException - if unexpected amount of parameters are given
     * @throws FalseFormattingException - if roll could not be parsed
     */
    public FireToRoll(List<String> args) throws ValueOutOfRangeException,
            WrongNumberOfArgumentsException, FalseFormattingException {
        if (args.size() != PARAMETER_NUM ) throw new WrongNumberOfArgumentsException(PARAMETER_NUM);
        try {
            this.roll = Integer.parseInt(args.get(ROLL_POS));
        } catch (NumberFormatException e) {
            throw new FalseFormattingException(e.getMessage());
        }
        if (roll < MIN_ROLL || roll > MAX_ROLL) throw new ValueOutOfRangeException(roll, MIN_ROLL, MAX_ROLL);
    }


    @Override
    public String execute(Session session) throws GameException {
        Player result = session.game.fireToRoll(roll);
        if (result == null) return GAME_NOT_LOST_MESSAGE;
        if (result.alive) {
            return result.identifier;
        }
        return GAME_LOST_MESSAGE;
    }
}
