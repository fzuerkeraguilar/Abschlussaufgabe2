package edu.kit.informatik.data;

import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.*;

import java.util.*;

/**
 *
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class GameBoard {
    private static Field[][] gameBoard;
    private static final Coordinates MIN_DIM = new Coordinates(5, 5);
    /**
     * dimensions of this game board
     */
    public final Coordinates dimensions;


    /**
     * Constructor of a game board for a fire breaker game
     * @param size dimensions of board
     * @param fields fields of the new board
     * @throws ValueOutOfRangeException - if board dimensions to small
     * @throws GameBoardSizeNotOddExceptions - if board dimensions are not odd
     */
    public GameBoard(Coordinates size, Field[][] fields) throws ValueOutOfRangeException,
            GameBoardSizeNotOddExceptions {
        if (size.y < MIN_DIM.y) throw new ValueOutOfRangeException(MIN_DIM.y, Integer.MAX_VALUE);
        if (size.x < MIN_DIM.x) throw new ValueOutOfRangeException(MIN_DIM.x, Integer.MAX_VALUE);
        if (size.y % 2 != 1) throw new GameBoardSizeNotOddExceptions(size.y);
        if (size.x % 2 != 1) throw new GameBoardSizeNotOddExceptions(size.x);
        dimensions = new Coordinates(size.y - 1,  size.x - 1);
        gameBoard = fields;
    }

    /**
     * Moves fireEngine to new position, if path is available
     * @param fireEngine fire engine to be moved
     * @param destination wished destination
     * @throws FigureDestroyedException - if fire engine is already destroyed
     * @throws FieldNotReachableException - if destination is not reachable from starting position
     * @throws FieldNotAvailableException - if destination is not available for a fire engine
     * @throws CoordinatesNotFoundException - if
     */
    public void moveFireEngine(FireEngine fireEngine, Coordinates destination) throws FigureDestroyedException,
            FieldNotReachableException, FieldNotAvailableException, CoordinatesNotFoundException {
        if (this.findFireEnginePath(fireEngine.position, destination)) {
            fireEngine.move(destination);
            this.getField(fireEngine.position).removeFigure(fireEngine.identifier);
            this.getField(destination).addFigure(fireEngine);
        } else {
            throw new FieldNotReachableException(fireEngine.toString(), destination.toString());
        }
    }

    /**
     * ds
     * @return String representation of all fields
     */
    public String toString() {
        StringBuilder board = new StringBuilder();
        for (Field[] l : gameBoard) {
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
        return gameBoard[coordinates.y][coordinates.x];
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
        gameBoard[coordinates.y][coordinates.x] = gameBoard[coordinates.y][coordinates.x].extinguish();
        return gameBoard[coordinates.y][coordinates.x];
    }

    /**
     * Check if there are burning fields are remaining
     * @return true - if there are burning fields are remaining; false - if not;
     */
    public boolean containsBurningFields() {
        for (Field[] fields : gameBoard) {
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
    public void fireToRoll(int roll) throws CoordinatesNotFoundException {
        switch (roll) {
            case 1:
                for (int y = 0; y <= dimensions.y; y++) {
                    for (int x = 0; x <= dimensions.x; x++) {
                        if (gameBoard[y][x].isBurnable()) {
                            for (Field adjField : this.getAdjFields(new Coordinates(y, x))) {
                                if (adjField.burnsOther()) {
                                    gameBoard[y][x] = gameBoard[y][x].burn();
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 2:
                for (int y = 0; y <= dimensions.y; y++) {
                    for (int x = 0; x <= dimensions.x; x++) {
                        if (gameBoard[y][x].isBurnable()) {
                            if (this.getAdjFields(new Coordinates(y, x))[2].burnsOther()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                            }
                        }
                    }
                }
                break;
            case 3:
                for (int y = 0; y <= dimensions.y; y++) {
                    for (int x = 0; x <= dimensions.x; x++) {
                        if (gameBoard[y][x].isBurnable()) {
                            if (this.getAdjFields(new Coordinates(y, x))[3].burnsOther()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                            }
                        }
                    }
                }
                break;
            case 4:
                for (int y = 0; y <= dimensions.y; y++) {
                    for (int x = 0; x <= dimensions.x; x++) {
                        if (gameBoard[y][x].isBurnable()) {
                            if (this.getAdjFields(new Coordinates(y, x))[0].burnsOther()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                            }
                        }
                    }
                }
                break;
            case 5:
                for (int y = 0; y <= dimensions.y; y++) {
                    for (int x = 0; x <= dimensions.x; x++) {
                        if (gameBoard[y][x].isBurnable()) {
                            if (this.getAdjFields(new Coordinates(y, x))[1].burnsOther()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                            }
                        }
                    }
                }
                break;
            case 6:
                return;
            default:
        }
        for (int y = 0; y <= dimensions.y; y++) {
            for (int x = 0; x <= dimensions.x; x++) {
                if (gameBoard[y][x].burnsAlone()) {
                    gameBoard[y][x] = gameBoard[y][x].burn();
                }
            }
        }
        for (int y = 0; y <= dimensions.y; y++) {
            for (int x = 0; x <= dimensions.x; x++) {
                gameBoard[y][x].resetBurned();
            }
        }
    }

    /**
     *
     * @param coordinates
     * @return starting at the field north of given coordinates, all fields
     * @throws CoordinatesNotFoundException
     */
    public Field[] getAdjFields(Coordinates coordinates) throws CoordinatesNotFoundException {
        Field[] fields = new Field[4];
        fields[0] = this.getField(coordinates.north(0));
        fields[1] = this.getField(coordinates.east(dimensions.x));
        fields[2] = this.getField(coordinates.south(dimensions.y));
        fields[3] = this.getField(coordinates.west(0));
        return fields;
    }

    /**
     *
     * @param coordinates
     * @return
     * @throws CoordinatesNotFoundException
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
     *
     * @param fireEngine
     * @param position
     */
    void placeFireEngine(FireEngine fireEngine, Coordinates position) {
        gameBoard[position.y][position.x].addFigure(fireEngine);
    }

    /**
     *
     * @param start
     * @param destination
     * @return
     * @throws FieldNotAvailableException
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
