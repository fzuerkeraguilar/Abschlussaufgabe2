package edu.kit.informatik.io.resources.exceptions;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class WrongNumberOfArgumentsException extends InputException {
    private static final String ONE_ARGUMENT_EXPECTED_MESSAGE = "expected one argument";
    private static final String WRONG_NUMBER_OF_ARGUMENTS_MESSAGE = "expected %d arguments";

    /**
     * Exception to be thrown, when wrong number of arguments are given
     * @param argumentNum number of expected arguments
     */
    public WrongNumberOfArgumentsException(int argumentNum)  {
        if (argumentNum == 1) {
            this.message = ONE_ARGUMENT_EXPECTED_MESSAGE;
        } else {
            this.message = String.format(WRONG_NUMBER_OF_ARGUMENTS_MESSAGE, argumentNum);
        }
    }
}
