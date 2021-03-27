package edu.kit.informatik.io.commands;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;
import edu.kit.informatik.io.resources.exceptions.ValueOutOfRangeException;

import java.util.List;

public class FireToRoll extends Command{
    public static final String IDENTIFIER = "fire-to-roll";
    public static final int PARAMETER_NUM = 1;
    private static final int MIN_ROLL = 1;
    private static final int MAX_ROLL = 6;
    private static final int ROLL_POS = 0;
    private final int roll;
    private static final String GAME_LOST_MESSAGE = "lose";
    private static final String GAME_NOT_LOST_MESSAGE = "OK";

    public FireToRoll(List<String> args) throws FalseFormattingException, ValueOutOfRangeException {
        if(args.size() != PARAMETER_NUM ) throw new FalseFormattingException("NOT THE RIGHT AMOUNT OF PARAMETERS","");
        roll = Integer.parseInt(args.get(ROLL_POS));
        if(roll < MIN_ROLL || roll > MAX_ROLL) throw new ValueOutOfRangeException(roll, MIN_ROLL, MAX_ROLL);
    }

    @Override
    public String execute(Game game) throws GameException {
        Player[] result = game.fireToRoll(roll);
        if(result == null) return GAME_LOST_MESSAGE;
        for(Player player : result) {
            if(player != null) {
                return GAME_LOST_MESSAGE + System.lineSeparator() + player.toString();
            }
        }
        return GAME_NOT_LOST_MESSAGE;
    }
}
