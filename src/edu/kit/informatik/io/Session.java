package edu.kit.informatik.io;

import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.fields.*;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.commands.Command;
import edu.kit.informatik.io.commands.Quit;
import edu.kit.informatik.io.input.CommandParser;
import edu.kit.informatik.io.input.Input;
import edu.kit.informatik.io.output.Output;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;
import edu.kit.informatik.io.resources.exceptions.FalsePositionException;
import edu.kit.informatik.io.resources.exceptions.InputException;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Session {
    private static final String EXIT_COMMAND = "quit";
    private static final int START_PARAMETER_POS = 0;

    private static final String START_PARAMETER_SPLIT = ",";
    private static final int Y_SIZE_POSITION = 0;
    private static final int X_SIZE_POSITION = 1;

    private static final int FIELD_START_POSITION = 2;
    private static final String[] PLAYER_IDENTIFIERS = {"A", "B", "C", "D"};
    private static final int STARTING_FIRE_ENGINES = 4;
    private static int foundFireEngines = 0;
    private static int foundPlayers = 0;
    private static final int PLAYER_NUM = 4;
    private static int foundPonds = 0;
    private static final int POND_NUM = 4;
    private static boolean lightlyBurningFieldFound = false;
    private static boolean stronglyBurningFieldFound = false;

    public Game game;
    private final Output outputHandler = new Output();
    private final CommandParser commandParser = new CommandParser();
    private final Input inputHandler = new Input();
    private boolean quit = false;

    /**
     * Constructor of a new session of fire breaker;
     * @param args starting arguments
     */
    public Session(String[] args) {
        try {
            //TODO
            String[] arguments = args[START_PARAMETER_POS].split(START_PARAMETER_SPLIT);

            Coordinates size = new Coordinates(arguments[Y_SIZE_POSITION], arguments[X_SIZE_POSITION]);
            arguments = Arrays.copyOfRange(arguments, FIELD_START_POSITION, arguments.length);

            if (arguments.length != size.x * size.y) throw new FalseFormattingException("test", "test");
            String[][] argumentField = new String[size.y][size.x];
            for (int y = 0; y < size.y; y++) {
                if (size.x >= 0) System.arraycopy(arguments, y * size.y, argumentField[y], 0, size.x);
            }

            Field[][] fields = new Field[size.y][size.x];
            for (int y = 0; y < size.y; y++) {
                for (int x = 0; x < size.x; x++) {
                    fields[y][x] = this.interpretFieldIdentifier(argumentField[y][x], y, x);
                }
            }
            if (foundFireEngines != STARTING_FIRE_ENGINES)
                throw new FalseFormattingException("to many fire engines", "");
            if (foundPlayers != PLAYER_NUM) throw new FalseFormattingException("to many players", "");
            if (foundPonds != POND_NUM) throw new FalseFormattingException("to many ponds", "");

            ArrayList<Coordinates> pondCoordinates = new ArrayList<>();
            pondCoordinates.add(new Coordinates(0, (size.x / 2)));
            pondCoordinates.add(new Coordinates((size.y / 2) , size.x - 1));
            pondCoordinates.add(new Coordinates(size.y - 1, (size.x / 2) ));
            pondCoordinates.add(new Coordinates((size.y / 2), 0 ));
            for (Coordinates c : pondCoordinates) {
                if (!fields[c.y][c.x].getIdentifier().equals(Pond.IDENTIFIER)) {
                    throw new FalsePositionException(fields[c.y][c.x].getIdentifier());
                }
            }

            ArrayList<Coordinates> playerNameCoordinates = new ArrayList<>();
            playerNameCoordinates.add(new Coordinates(0, 0));
            playerNameCoordinates.add(new Coordinates(size.y - 1, size.x - 1));
            playerNameCoordinates.add(new Coordinates(size.y - 1, 0));
            playerNameCoordinates.add(new Coordinates(0, size.x - 1));
            Player[] players = new Player[PLAYER_NUM];
            for (int i = 0; i < PLAYER_NUM; i++) {
                if (fields[playerNameCoordinates.get(i).y][playerNameCoordinates.get(i).x]
                        .getIdentifier().equals(PLAYER_IDENTIFIERS[i])) {
                    players[i] = new Player(PLAYER_IDENTIFIERS[i], playerNameCoordinates.get(i));
                } else {
                    throw new FalseFormattingException("Fire station not at specified pos", String.valueOf(i));
                }
            }

            ArrayList<Coordinates> playerFigureCoordinates = new ArrayList<>();
            playerFigureCoordinates.add(new Coordinates(1, 1));
            playerFigureCoordinates.add(new Coordinates(size.y - 2, size.x - 2));
            playerFigureCoordinates.add(new Coordinates(size.y - 2, 1));
            playerFigureCoordinates.add(new Coordinates(1, size.x - 2));

            for (int i = 0; i < PLAYER_NUM; i++) {
                if (fields[playerFigureCoordinates.get(i).y][playerFigureCoordinates.get(i).x]
                        .getFireEngineList().get(0).getOwner().matches(players[i].identifier)) {
                    players[i].addFigure(fields[playerFigureCoordinates.get(i).y]
                            [playerFigureCoordinates.get(i).x].getFireEngineList().get(0));
                    //TODO check if FireEngine.getOwner matches PlayerNames
                    //TODO check if FireEngine.getIndex is 0
                }
            }

            if (!(lightlyBurningFieldFound && stronglyBurningFieldFound)) {
                throw new FalseFormattingException("burning Field", "dsjiodlsjd");
            }

            game = new Game(size, fields, players);
        } catch (GameException | FalseFormattingException | FalsePositionException e) {
            outputHandler.printError(e.getMessage());
            this.setQuit();
        }
    }

    /**
     *
     */
    public void run() {
        //TODO quit über Command Klasse machen
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

    public void setQuit() {
        quit = true;
    }

    private Field interpretFieldIdentifier(String identifier, int y, int x) throws FalseFormattingException {
        switch (identifier) {
            case DryForest.IDENTIFIER: return new DryForest(y, x);
            case WetForest.IDENTIFIER: return new WetForest(y, x);
            case LightlyBurningForest.IDENTIFIER:
                lightlyBurningFieldFound = true;
                return new LightlyBurningForest(y, x);
            case StronglyBurningForest.IDENTIFIER:
                stronglyBurningFieldFound = true;
                return new StronglyBurningForest(y, x);
            case Pond.IDENTIFIER:
                foundPonds++;
                return new Pond(y, x);
            default:
                break;
        }
        if (identifier.matches(FireStation.IDENTIFIER_REGEX) ) {
            foundPlayers++;
            return new FireStation(y, x, identifier);
        }
        if (identifier.matches(FireEngine.REGEX)) {
            DryForest dryForest = new DryForest(y, x);
            FireEngine fireEngine = new FireEngine(y, x, identifier);
            dryForest.addFigure(fireEngine);
            foundFireEngines++;
            return dryForest;
        }
        throw new FalseFormattingException(identifier, "<Field>");
    }
}
