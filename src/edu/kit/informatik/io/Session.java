package edu.kit.informatik.io;


import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.GameBoard;
import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.fields.*;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.resources.exceptions.*;
import edu.kit.informatik.io.commands.Command;
import edu.kit.informatik.io.commands.Quit;
import edu.kit.informatik.io.input.CommandParser;
import edu.kit.informatik.io.input.Input;
import edu.kit.informatik.io.output.Output;


import java.util.Arrays;

/**
 * Class to model a session of fire breaker
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Session {

    private static final int START_PARAMETER_POS = 0;

    private static final String START_PARAMETER_SPLIT = ",";
    private static final int Y_SIZE_POSITION = 0;
    private static final int X_SIZE_POSITION = 1;

    private static final int FIELD_START_POSITION = 2;
    /**
     * fire breaker game of this session
     */
    public Game game;

    private int foundFireEngines = 0;
    private int foundFireStations = 0;
    private int foundPonds = 0;
    private int lightlyBurningFieldFound = 0;
    private int stronglyBurningFieldFound = 0;

    private final Output outputHandler = new Output();
    private final CommandParser commandParser = new CommandParser();
    private final Input inputHandler = new Input();
    private Game gameBackUp;
    private boolean quit = false;

    /**
     * Constructor of a new session of fire breaker;
     * @param args starting arguments
     */
    public Session(String[] args) {
        try {
            if (args.length != START_PARAMETER_POS + 1) throw new OnlyOneFieldException();
            String[] arguments = args[START_PARAMETER_POS].split(START_PARAMETER_SPLIT);
            Coordinates size = new Coordinates(arguments[Y_SIZE_POSITION], arguments[X_SIZE_POSITION]);
            if (size.x < GameBoard.MIN_DIM.x) {
                throw new ValueOutOfRangeException(size.x, GameBoard.MIN_DIM.x, Integer.MAX_VALUE);
            }
            if (size.y < GameBoard.MIN_DIM.y) {
                throw new ValueOutOfRangeException(size.y, GameBoard.MIN_DIM.y, Integer.MAX_VALUE);
            }
            arguments = Arrays.copyOfRange(arguments, FIELD_START_POSITION, arguments.length);
            if (arguments.length != size.x * size.y) throw new WrongNumberOfArgumentsException(size.x * size.y);

            String[][] argumentField = new String[size.y][size.x];
            for (int y = 0; y < size.y; y++) {
                if (size.x >= 0) System.arraycopy(arguments, y * size.x, argumentField[y], 0, size.x);
            }
            Field[][] fields = new Field[size.y][size.x];
            for (int y = 0; y < size.y; y++) {
                for (int x = 0; x < size.x; x++) {
                    fields[y][x] = this.interpretFieldIdentifier(argumentField[y][x], new Coordinates(y, x));
                }
            }

            size = new Coordinates(size.y - 1, size.x - 1);
            if (this.foundFireEngines != FireEngine.STARTING_NUM) {
                throw new FireEngineNumberException(FireEngine.STARTING_NUM);
            }
            if (this.foundFireStations != FireStation.STARTING_NUM) {
                throw new FireStationNumberException(FireStation.STARTING_NUM);
            }
            if (this.foundPonds != Pond.STARTING_NUM) throw new PondNumberException(Pond.STARTING_NUM);
            if (this.lightlyBurningFieldFound < LightlyBurningForest.MIN_STARTING_NUM ) {
                throw new LightlyBurningFieldNumberException(LightlyBurningForest.MIN_STARTING_NUM);
            }
            if (this.stronglyBurningFieldFound < StronglyBurningForest.MIN_STARTING_NUM) {
                throw new StronglyBurningForestNumberException(StronglyBurningForest.MIN_STARTING_NUM);
            }

            for (Coordinates c : Pond.startingPos(size)) {
                if (!fields[c.y][c.x].getIdentifier().equals(Pond.IDENTIFIER)) {
                    throw new FieldNotExpected(fields[c.y][c.x].getIdentifier(), c);
                }
            }

            Player[] players = new Player[Player.PLAYER_NUM];
            Coordinates[] fireStationStartingPos = FireStation.getStartingPos(size);
            for (int i = 0; i < Player.PLAYER_NUM; i++) {
                Field fireStationLocation = fields[fireStationStartingPos[i].y][fireStationStartingPos[i].x];
                if (fireStationLocation.getIdentifier().equals(Player.PLAYER_IDENTIFIERS[i])) {
                    players[i] = new Player(Player.PLAYER_IDENTIFIERS[i], fireStationStartingPos[i]);
                } else {
                    throw new FieldNotExpected(fireStationLocation.getIdentifier(), fireStationStartingPos[i]);
                }
            }
            Coordinates[] fireEnginePos = FireEngine.startingPos(size);
            for (int i = 0; i < Player.PLAYER_NUM; i++) {
                Field fireEngineLocation = fields[fireEnginePos[i].y][fireEnginePos[i].x];
                if (fireEngineLocation.getFireEngineList().size() != 1) {
                    throw new FireEngineExpectedException(fireEnginePos[i]);
                }
                FireEngine newFireEngine = fireEngineLocation.getFireEngineList().get(0);
                if (newFireEngine.getIndex() != FireEngine.STARTING_INDEX) {
                    throw new FireEngineIndexException(newFireEngine.identifier, FireEngine.STARTING_INDEX);
                }
                if (newFireEngine.getOwner().matches(players[i].identifier)) {
                    players[i].addFireEngine(newFireEngine);
                } else {
                    throw new FireEngineOwnerException(players[i].identifier + FireEngine.STARTING_INDEX,
                            fireEnginePos[i]);
                }
            }
            this.game = new Game(size, fields, players);
            this.gameBackUp = this.game.clone();
        } catch (GameException | InputException e) {
            this.setQuit();
            this.outputHandler.printError(e.getMessage());
        }
    }

    /**
     * Starts the inputHandler to read and execute the users inputs
     */
    public void run() {
        while (!quit) {
            this.inputHandler.readInput();
            try {
                Command nextCommand = this.commandParser.parse(this.inputHandler.getNextInput());
                if (nextCommand.getClass().equals(Quit.class)) return;
                this.outputHandler.print(nextCommand.execute(this));
            } catch (InputException | GameException e) {
                this.outputHandler.printError(e.getMessage());
            }
        }
    }

    /**
     * Quits this session
     */
    public void setQuit() {
        quit = true;
    }

    /**
     * Resets this session's game
     */
    public void reset() {
        this.game = this.gameBackUp.clone();
    }

    private Field interpretFieldIdentifier(String identifier, Coordinates position) throws FalseFormattingException {
        switch (identifier) {
            case DryForest.IDENTIFIER: return new DryForest(position.y, position.x);
            case WetForest.IDENTIFIER: return new WetForest(position.y, position.x);
            case LightlyBurningForest.IDENTIFIER:
                lightlyBurningFieldFound++;
                return new LightlyBurningForest(position.y, position.x);
            case StronglyBurningForest.IDENTIFIER:
                stronglyBurningFieldFound++;
                return new StronglyBurningForest(position.y, position.x);
            case Pond.IDENTIFIER:
                foundPonds++;
                return new Pond(position.y, position.x);
            default:
                break;
        }
        if (identifier.matches(FireStation.IDENTIFIER_REGEX) ) {
            foundFireStations++;
            return new FireStation(position.y, position.x, identifier);
        }
        if (identifier.matches(FireEngine.REGEX)) {
            DryForest dryForest = new DryForest(position.y, position.x);
            FireEngine fireEngine = new FireEngine(position, identifier);
            dryForest.addFigure(fireEngine);
            foundFireEngines++;
            return dryForest;
        }
        throw new FalseFormattingException(identifier, "<Field>");
    }
}
