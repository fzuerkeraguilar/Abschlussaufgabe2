package edu.kit.informatik.io;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.data.Game;
import edu.kit.informatik.data.Player;
import edu.kit.informatik.data.fields.*;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameException;
import edu.kit.informatik.io.commands.Command;
import edu.kit.informatik.io.input.CommandParser;
import edu.kit.informatik.io.input.Input;
import edu.kit.informatik.io.output.Output;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;
import edu.kit.informatik.io.resources.exceptions.InputException;

import java.awt.desktop.AppReopenedEvent;
import java.awt.desktop.AppReopenedListener;
import java.util.ArrayList;
import java.util.Arrays;

public class Session {
    private static final String EXIT_COMMAND = "quit";
    private static final int START_PARAMETER_POS = 0;

    private static final String START_PARAMETER_SPLIT = ",";
    private static final int Y_SIZE_POSITION = 0;
    private static final int X_SIZE_POSITION = 1;

    private static final int FIELD_START_POSITION = 2;
    private static final String[] PLAYER_IDENTIFIERS = {"A", "B", "C", "D"};
    private static final int STARTING_FIRE_ENGINES = 4;
    private static int FOUND_FIRE_ENGINES = 0;
    private static int FOUND_PLAYERS = 0;
    private static final int PLAYER_NUM = 4;
    private static int FOUND_PONDS = 0;
    private static final int POND_NUM = 4;
    private static boolean lightlyBurningFieldFound = false;
    private static boolean stronglyBurningFieldFound = false;

    private static Game game1;
    private final Output outputHandler = new Output();
    private final CommandParser commandParser = new CommandParser();
    private final Input inputHandler = new Input();
    private boolean quit = false;

    public Session(String[] args) {
        try{
            //TODO
            String[] arguments = args[START_PARAMETER_POS].split(START_PARAMETER_SPLIT);

            Coordinates size = new Coordinates(arguments[Y_SIZE_POSITION], arguments[X_SIZE_POSITION]);
            arguments = Arrays.copyOfRange(arguments, FIELD_START_POSITION, arguments.length);
            Terminal.printLine(Arrays.toString(arguments));

            if(arguments.length != size.x * size.y) throw new FalseFormattingException("test", "test");
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
            if(FOUND_FIRE_ENGINES != STARTING_FIRE_ENGINES) throw new FalseFormattingException("to many fire engines", "");
            if(FOUND_PLAYERS != PLAYER_NUM) throw new FalseFormattingException("to many players", "");
            if(FOUND_PONDS != POND_NUM) throw new FalseFormattingException("to many ponds", "");

            ArrayList<Coordinates> POND_COORDINATES = new ArrayList<>();
            POND_COORDINATES.add(new Coordinates(0, (size.x / 2)));
            POND_COORDINATES.add(new Coordinates((size.y / 2) , size.x - 1));
            POND_COORDINATES.add(new Coordinates(size.y - 1, (size.x / 2) ));
            POND_COORDINATES.add(new Coordinates((size.y / 2), 0 ));
            for (Coordinates c : POND_COORDINATES) {
                if(!fields[c.y][c.x].toString().equals(Pond.IDENTIFIER)) {
                    throw new FalseFormattingException("pond not at specified pos", "pond");
                }
            }

            ArrayList<Coordinates> PLAYER_NAME_COORDINATES = new ArrayList<>();
            PLAYER_NAME_COORDINATES.add(new Coordinates(0,0));
            PLAYER_NAME_COORDINATES.add(new Coordinates(size.y - 1, size.x - 1));
            PLAYER_NAME_COORDINATES.add(new Coordinates(size.y - 1, 0));
            PLAYER_NAME_COORDINATES.add(new Coordinates(0, size.x - 1));
            Player[] players = new Player[PLAYER_NUM];
            for(int i = 0; i < PLAYER_NUM; i++) {
                if(fields[PLAYER_NAME_COORDINATES.get(i).y][PLAYER_NAME_COORDINATES.get(i).x].toString().equals(PLAYER_IDENTIFIERS[i])) {
                    players[i] = new Player(PLAYER_IDENTIFIERS[i], PLAYER_NAME_COORDINATES.get(i));
                } else {
                    throw new FalseFormattingException("Fire station not at specified pos", "test");
                }
            }


            ArrayList<Coordinates> PLAYER_FIGURE_COORDINATES = new ArrayList<>();
            PLAYER_FIGURE_COORDINATES.add(new Coordinates(1, 1));
            PLAYER_FIGURE_COORDINATES.add(new Coordinates(size.y - 2, size.x - 2));
            PLAYER_FIGURE_COORDINATES.add(new Coordinates(size.y - 2, 1));
            PLAYER_FIGURE_COORDINATES.add(new Coordinates(1, size.x - 2));

            for(int i = 0; i < PLAYER_NUM; i++) {
                if(fields[PLAYER_FIGURE_COORDINATES.get(i).y][PLAYER_FIGURE_COORDINATES.get(i).x].getFiguresOnField().get(0).toString().matches(players[i].identifier)) {
                    players[i].addFigure(fields[PLAYER_FIGURE_COORDINATES.get(i).y][PLAYER_FIGURE_COORDINATES.get(i).x].getFiguresOnField().get(0));
                }
            }

            if(!(lightlyBurningFieldFound && stronglyBurningFieldFound)) {
                throw new FalseFormattingException("burning Field", "dsjiodlsjd");
            }

            game1 = new Game(size, fields, players);
            Terminal.printLine(game1.toString());
        } catch (GameException | FalseFormattingException e) {
            outputHandler.printError(e.getMessage());
            e.printStackTrace();
        }
    }

    public void run() {
        while(!quit) {
            this.inputHandler.readInput();
            if(this.inputHandler.getNextInput().equals(EXIT_COMMAND)) {
                this.quit = true;
                break;
            }
            try {
                Command nextCommand = this.commandParser.parse(this.inputHandler.getNextInput());
                this.outputHandler.print(nextCommand.execute(game1));
            } catch (InputException | GameException e) {
                this.outputHandler.printError(e.getMessage());
            }
        }
    }

    private Field interpretFieldIdentifier(String identifier, int y, int x) throws FalseFormattingException {
        switch (identifier) {
            case DryForest.IDENTIFIER: return new DryForest(y,x);
            case WetForest.IDENTIFIER: return new WetForest(y,x);
            case LightlyBurningForest.IDENTIFIER: lightlyBurningFieldFound = true; return new LightlyBurningForest(y,x);
            case StronglyBurningForest.IDENTIFIER: stronglyBurningFieldFound = true; return new StronglyBurningForest(y,x);
            case Pond.IDENTIFIER: FOUND_PONDS++; return new Pond(y, x);
        }
        if(identifier.matches(FireStation.IDENTIFIER_REGEX) ) {
            FOUND_PLAYERS++;
            return new FireStation(y, x, identifier);
        }
        if(identifier.matches(FireEngine.REGEX)) {
            DryForest dryForest = new DryForest(y, x);
            FireEngine fireEngine = new FireEngine(y, x, identifier);
            dryForest.addFigure(fireEngine);
            FOUND_FIRE_ENGINES++;
            return dryForest;
        }
        throw new FalseFormattingException(identifier, "<Field>");
    }
}
