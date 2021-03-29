package edu.kit.informatik.data;

import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.*;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;

/**
 * Class to model the game board of a fire breaker game
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameBoard {
    /**
     * minimum dimensions of a game board
     */
    public static final Coordinates MIN_DIM = new Coordinates(5, 5);
    /**
     * dimensions of this game board
     */
    public final Coordinates dimensions;
    private final Field[][] gameBoard;


    /**
     * Constructor of a game board for a fire breaker game
     * @param size dimensions of board
     * @param fields fields of the new board
     */
    public GameBoard(Coordinates size, Field[][] fields) throws GameBoardSizeNotOddExceptions {
        if (size.x % 2 != 0  || size.y % 2 != 0) throw new GameBoardSizeNotOddExceptions();
        this.dimensions = size;
        this.gameBoard = fields;
    }

    /**
     * Moves fireEngine to new position, if path is available
     * @param fireEngine fire engine to be moved
     * @param destination wished destination
     * @throws FigureDestroyedException - if fire engine is already destroyed
     * @throws FieldNotReachableException - if destination is not reachable from starting position
     * @throws FieldNotAvailableException - if destination is not available for a fire engine
     * @throws CoordinatesNotFoundException - if
     * @throws FigureNotMovableException - if fire engine cannot move
     */
    public void moveFireEngine(FireEngine fireEngine, Coordinates destination) throws FigureDestroyedException,
            FieldNotReachableException, FieldNotAvailableException,
            CoordinatesNotFoundException, FigureNotMovableException {
        if (this.findFireEnginePath(fireEngine.position, destination)) {
            Coordinates origin = fireEngine.position.clone();
            fireEngine.move(destination);
            this.getField(origin).removeFigure(fireEngine.identifier);
            this.getField(destination).addFigure(fireEngine);
        } else {
            throw new FieldNotReachableException(destination.toString(), fireEngine.toString());
        }
    }

    /**
     * ds
     * @return String representation of all fields
     */
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (Field[] l : this.gameBoard) {
            for (Field f : l) {
                board.append(f.toString());
                board.append(",");
            }
            board.deleteCharAt(board.length() - 1);
            board.append(System.lineSeparator());
        }
        board.deleteCharAt(board.length() - 1);
        return board.toString();
    }

    /**
     * Returns get field at given coordinates
     * @param coordinates zero indexed coordinates of wanted field
     * @return Field at given coordinates
     * @throws CoordinatesNotFoundException - if given coordinates are not within board
     */
    public Field getField(Coordinates coordinates) throws CoordinatesNotFoundException {
        if (coordinates.y > this.dimensions.y || coordinates.x > this.dimensions.x) {
            throw new CoordinatesNotFoundException(coordinates);
        }
        return this.gameBoard[coordinates.y][coordinates.x];
    }

    /**
     * Extinguishes field at given position
     * @param coordinates Coordinates of field to be extinguished
     * @return the extinguished version of field
     * @throws FieldNotExtinguishableException - if field cannot be extinguished
     * @throws CoordinatesNotFoundException - if coordinates are not within board
     */
    public Field extinguishField(Coordinates coordinates) throws FieldNotExtinguishableException,
            CoordinatesNotFoundException {
        if (!this.getField(coordinates).isExtinguishable()) {
            throw new FieldNotExtinguishableException(coordinates.toString());
        }
        this.gameBoard[coordinates.y][coordinates.x] = this.gameBoard[coordinates.y][coordinates.x].extinguish();
        return this.gameBoard[coordinates.y][coordinates.x];
    }

    /**
     * Check if there are burning fields are remaining
     * @return true - if there are burning fields are remaining; false - if not;
     */
    public boolean containsBurningFields() {
        for (Field[] fields : this.gameBoard) {
            for (Field field : fields) {
                if (field.burns()) {
                    return true;
                }
            }
        }
        return false;

    }

    /**
     * Increases fire level of all fields according to roll
     * @param roll - given roll of dice
     */
    public void fireToRoll(int roll) throws CoordinatesNotFoundException, ValueOutOfRangeException {
        if (roll < 1 || roll > 6) throw new ValueOutOfRangeException(1, 6);
        if (roll == 6) return;
        if (roll == 1) {
            for (int y = 0; y <= dimensions.y; y++) {
                for (int x = 0; x <= dimensions.x; x++) {
                    if (this.gameBoard[y][x].burnsOther() && !this.gameBoard[y][x].isBurned()) {
                        for (Field field : this.getAdjFields(new Coordinates(y, x))) {
                            if (field.isBurnable()) {
                                this.gameBoard[field.coordinates.y][field.coordinates.x] = field.burn();
                            }
                        }
                    }
                }
            }
        } else {
            for (int y = 0; y <= dimensions.y; y++) {
                for (int x = 0; x <= dimensions.x; x++) {
                    if (this.gameBoard[y][x].isBurnable()) {
                        if (this.getAdjFields(new Coordinates(y, x))[roll - 2].burnsOther()) {
                            this.gameBoard[y][x] = this.gameBoard[y][x].burn();
                        }
                    }
                }
            }
        }
        for (int y = 0; y <= dimensions.y; y++) {
            for (int x = 0; x <= dimensions.x; x++) {
                if (this.gameBoard[y][x].burnsAlone()) {
                    this.gameBoard[y][x] = this.gameBoard[y][x].burn();
                }
            }
        }
        for (int y = 0; y <= dimensions.y; y++) {
            for (int x = 0; x <= dimensions.x; x++) {
                this.gameBoard[y][x].resetBurned();
            }
        }
    }

    /**
     *
     * @param coordinates field that
     * @return starting at the field south of given coordinates, all fields orthogonal to given field
     * in clockwise direction
     * @throws CoordinatesNotFoundException - if coordinates are not within this game board
     */
    public Field[] getAdjFields(Coordinates coordinates) throws CoordinatesNotFoundException {
        Field[] fields = new Field[4];
        fields[0] = this.getField(coordinates.south(dimensions.y));
        fields[1] = this.getField(coordinates.west(0));
        fields[2] = this.getField(coordinates.north(0));
        fields[3] = this.getField(coordinates.east(dimensions.x));
        return fields;
    }

    /**
     *
     * @param coordinates coordinates
     * @return starting at the field north of given coordinates, all fields orthogonal to given field
     * in clockwise direction
     * @throws CoordinatesNotFoundException - if coordinates are not within this game board
     */
    public Field[] getDiagFields(Coordinates coordinates) throws CoordinatesNotFoundException {
        Field[] fields = new Field[4];
        fields[0] = this.getField(coordinates.northEast(0, dimensions.x));
        fields[1] = this.getField(coordinates.southEast(dimensions.y, dimensions.x));
        fields[2] = this.getField(coordinates.southWest(dimensions.y, 0));
        fields[3] = this.getField(coordinates.northWest(0, 0));
        return fields;
    }

    /**
     * Places a fire engine at specified location
     * @param fireEngine fire engine to be placed
     * @param position position at which the fire engine should be placed
     */
    void placeFireEngine(FireEngine fireEngine, Coordinates position) {
        gameBoard[position.y][position.x].addFigure(fireEngine);
    }

    /**
     * Searches for path for fire engine to a destination
     * @param start start of path
     * @param destination end of path
     * @return true - if path exists, that the fire engine can take, false - if no such path exists
     * @throws FieldNotAvailableException - if any coordinates are not within this game board
     */
    private boolean findFireEnginePath(Coordinates start, Coordinates destination) throws
            FieldNotAvailableException, CoordinatesNotFoundException {

        if (!this.getField(destination).isAvailableToFireEngine()) {
            throw new FieldNotAvailableException(destination.toString());
        }
        if (start.equals(destination)) {
            return false;
        }
        boolean pathFound = false;
        ArrayList<Field> visitedFields = new ArrayList<>();
        Queue<Field> nextField = new ArrayDeque<>();

        for (Field field = this.getField(start);
             field != null && field.coordinates.distance(start) <= FireEngine.MAX_MOVEMENT;
             field = nextField.poll()) {
            if (field.equals(this.getField(destination)) && field.isAvailableToFireEngine()) {
                pathFound = true;
                break;
            } else {
                if (field.isPassableToFireEngine()) {
                    for (Field f : this.getAdjFields(field.coordinates)) {
                        if (!field.equals(f)) {
                            if (!visitedFields.contains(f)) {
                                nextField.add(f);
                                visitedFields.add(f);
                            }
                        }
                    }
                }
            }
        }
        return pathFound;
    }

}
