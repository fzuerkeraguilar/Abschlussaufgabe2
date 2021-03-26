package edu.kit.informatik.data;

import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.Figure;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameBoardSIzeNotOddExceptions;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.*;

public class GameBoard {
    private static final Coordinates MIN_DIM = new Coordinates(5,5);
    public final Coordinates DIM;
    private static Field[][] gameBoard;
    private static HashMap<String, Coordinates> coordinatesMap;



    public GameBoard(Coordinates size, Field[][] fields) throws ValueOutOfRangeException, GameBoardSIzeNotOddExceptions {
        if(size.y < MIN_DIM.y) throw new ValueOutOfRangeException(MIN_DIM.y, Integer.MAX_VALUE);
        if(size.x < MIN_DIM.x) throw new ValueOutOfRangeException(MIN_DIM.x, Integer.MAX_VALUE);
        if(size.y % 2 != 1) throw new GameBoardSIzeNotOddExceptions(size.y);
        if(size.x % 2 != 1) throw new GameBoardSIzeNotOddExceptions(size.x);
        DIM = new Coordinates(size.y - 1,  size.x - 1);
        gameBoard = fields;
    }

    public void moveFigure(FireEngine figure, Coordinates destination) throws FalseFormattingException {
        if(this.findFireEnginePath(figure.position, destination, (int) FireEngine.MAX_MOVEMENT)) {
            this.getField(figure.position).removeFigure(figure.identifier);
            this.getField(destination).addFigure(figure);
            figure.move(destination);
        } else {
            throw new FalseFormattingException("path not found", "");
        }
    }


    public String toString(){
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

    public Field getField(Coordinates coordinates) {
        return gameBoard[coordinates.y][coordinates.x];
    }


    public void extinguishField(Coordinates coordinates) throws FalseFormattingException {
        if(!gameBoard[coordinates.y][coordinates.x].isExtinguishable()) {
            throw new FalseFormattingException("not extinguishable", "");
        }
        gameBoard[coordinates.y][coordinates.x] = gameBoard[coordinates.y][coordinates.x].extinguish();
    }

    public void fireToRoll(int roll) throws FalseFormattingException {
        switch (roll) {
            case 1:
                for(int y = 0; y < DIM.y; y++) {
                    for(int x = 0; x < DIM.x; x++) {
                        if(gameBoard[y][x].isBurnable()) {
                            this.addAdjFields(gameBoard[y][x].coordinates, 0);
                            for(Field adjField : gameBoard[y][x].adjFields) {
                                if(adjField.burns()) {
                                    gameBoard[y][x] = gameBoard[y][x].burn();
                                    break;
                                }
                            }
                        }
                    }
                }
                break;
            case 2:
                for(int y = 0; y < DIM.y; y++) {
                    for(int x = 0; x < DIM.x; x++) {
                        if(gameBoard[y][x].isBurnable()) {
                            this.addAdjFields(gameBoard[y][x].coordinates, 0);
                            if(gameBoard[y][x].adjFields[2].burns()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                                break;
                            }
                        }
                    }
                }
                break;
            case 3:
                for(int y = 0; y < DIM.y; y++) {
                    for(int x = 0; x < DIM.x; x++) {
                        if(gameBoard[y][x].isBurnable()) {
                            this.addAdjFields(gameBoard[y][x].coordinates, 0);
                            if(gameBoard[y][x].adjFields[3].burns()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                                break;
                            }
                        }
                    }
                }
                break;
            case 4:
                for(int y = 0; y < DIM.y; y++) {
                    for(int x = 0; x < DIM.x; x++) {
                        if(gameBoard[y][x].isBurnable()) {
                            this.addAdjFields(gameBoard[y][x].coordinates, 0);
                            if(gameBoard[y][x].adjFields[0].burns()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                                break;
                            }
                        }
                    }
                }
                break;
            case 5:
                for(int y = 0; y < DIM.y; y++) {
                    for(int x = 0; x < DIM.x; x++) {
                        if(gameBoard[y][x].isBurnable()) {
                            this.addAdjFields(gameBoard[y][x].coordinates, 0);
                            if(gameBoard[y][x].adjFields[1].burns()) {
                                gameBoard[y][x] = gameBoard[y][x].burn();
                                break;
                            }
                        }
                    }
                }
                break;
            case 6:
                return;
            default: throw new FalseFormattingException("number 1-6", "");
        }
    }

    void addAdjFields(Coordinates coordinates, int depth) {
        //TODO depth implementieren

        this.getField(coordinates).adjFields[0] = this.getField(coordinates.north(0));
        this.getField(coordinates).adjFields[1] = this.getField(coordinates.east(DIM.x));
        this.getField(coordinates).adjFields[2] = this.getField(coordinates.south(DIM.y));
        this.getField(coordinates).adjFields[3] = this.getField(coordinates.west(0));
    }

    Field[] getAdjFields(Coordinates coordinates) {
        Field[] fields = new Field[4];
        fields[0] = this.getField(coordinates.north(0));
        fields[1] = this.getField(coordinates.east(DIM.x));
        fields[2] = this.getField(coordinates.south(DIM.y));
        fields[3] = this.getField(coordinates.west(0));
        return fields;
    }

    void addDiagFields(Coordinates coordinates, int depth) {
        //TODO depth implementieren
        this.getField(coordinates).adjFields[0] = this.getField(coordinates.northEast(0, DIM.x));
        this.getField(coordinates).adjFields[1] = this.getField(coordinates.southEast(DIM.y, DIM.x));
        this.getField(coordinates).adjFields[2] = this.getField(coordinates.southWest(DIM.y, 0));
        this.getField(coordinates).adjFields[3] = this.getField(coordinates.northWest(0, 0));
    }

    void placeFireEngine(FireEngine fireEngine, Coordinates position) {
        gameBoard[position.y][position.x].addFigure(fireEngine);
    }

    private boolean findFireEnginePath(Coordinates start, Coordinates destination, int maxLength) throws FalseFormattingException {
        if(!this.getField(destination).isAvailableToFireEngine()) {
            throw new FalseFormattingException("destination is not available", "");
        }
        if(start.equals(destination)) {
            return true;
        }
        boolean pathFound = false;
        ArrayList<Field> visitedFields = new ArrayList<>();
        Queue<Field> nextField = new ArrayDeque<>();

        for(Field field = this.getField(start); field != null && field.coordinates.distance(start) <= maxLength; field = nextField.poll()) {
            if(field.equals(this.getField(destination)) && field.isAvailableToFireEngine()) {
                pathFound = true;
                break;
            } else {
                if (field.isPassableToFireEngine()) {
                    for(Field f : this.getAdjFields(field.coordinates)) {
                        if(!field.equals(f)) {
                            if(!visitedFields.contains(f)) {
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
