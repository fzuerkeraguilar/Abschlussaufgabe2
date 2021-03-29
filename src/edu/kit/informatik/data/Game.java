package edu.kit.informatik.data;

import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.FireEngineExtinguishedFieldAlreadyException;
import edu.kit.informatik.data.resources.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Game class to model a game FireBreaker
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Game {
    private final GameBoard gameBoard;
    private final ArrayList<Player> players = new ArrayList<>();
    private boolean gameLost = false;
    private boolean gameWon = false;
    private boolean fireToRollNeeded = false;
    private Player currentPlayer;
    private final Coordinates fieldSizeBackup;
    private final Field[][] fieldsBackup;
    private final Player[] playersBackup;

    /**
     * Constructor of a game of FireBreaker
     * @param fieldSize size of wanted game board
     * @param fields Fields that represent the game board; a Field[] of fields represents a horizontal line
     * @param players Players, that already have their FireEngines assigned in their starting order
     * @throws GameBoardSizeNotOddExceptions - if game board size not odd
     */
    public Game(Coordinates fieldSize, Field[][] fields, Player[] players) throws
            GameBoardSizeNotOddExceptions {
        this.gameBoard = new GameBoard(fieldSize, fields);
        Collections.addAll(this.players, players);
        this.currentPlayer = this.getFirstPlayer();
        this.fieldSizeBackup = fieldSize.clone();
        this.fieldsBackup = new Field[fieldSize.y + 1][fieldSize.x + 1];
        for (int i = 0; i <= fieldSize.y; i++) {
            for (int j = 0; j <= fieldSize.x; j++) {
                this.fieldsBackup[i][j] = fields[i][j].clone();
            }
        }
        this.playersBackup = new Player[players.length];
        for (int k = 0; k < players.length; k++) {
            this.playersBackup[k] = players[k].clone();
        }
    }

    @Override
    public String toString() {
        return this.gameBoard.toString();
    }

    /**
     * Returns a detailed representation of a field with all its fire engines
     * @param coordinates - zero indexed coordinates of field on game board
     * @return representation of a field with all its fire engines
     * @throws CoordinatesNotFoundException - if coordinates are not found
     */
    public String showField(Coordinates coordinates) throws CoordinatesNotFoundException {
        if (coordinates.y > this.gameBoard.dimensions.y || coordinates.x > this.gameBoard.dimensions.x) {
            throw new CoordinatesNotFoundException(coordinates);
        }
        return this.gameBoard.getField(coordinates).showInformation();
    }

    /**
     * moves fire engine to specified destination
     * @param identifier identifier of fire engine to be moved
     * @param destination zero indexed coordinates of destination field
     * @throws FigureDestroyedException - if given fire engine is already destroyed
     * @throws IdentifierNotFoundException - if given fire engine does not belong to current player
     * @throws FigureNotMovableException - if fire engine cannot move during the remainder of this turn
     * @throws FieldNotReachableException - if destination is not reachable for fire engine
     * @throws FieldNotAvailableException - if destination is not available for fire engine
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     */
    public void move(String identifier, Coordinates destination) throws FigureDestroyedException,
            IdentifierNotFoundException,
            FigureNotMovableException,
            FieldNotReachableException,
            FieldNotAvailableException,
            WrongCommandSequenceException, CoordinatesNotFoundException, GameWonException, GameLostException {
        this.checkGameStateAndPosition(destination);

        FireEngine movingFigure = this.currentPlayer.getFireEngine(identifier);
        if (movingFigure.destroyed) {
            throw new FigureDestroyedException(identifier);
        }
        this.gameBoard.moveFireEngine(movingFigure, destination);
    }



    /**
     * Extinguishes a given field with a given fire engine
     * @param identifier identifier of fire engine
     * @param coordinates zero indexed coordinates of field to be extinguished
     * @return ["new state of field", "water remaining in given fire engine"]; if game is won, both values are null;
     * @throws FigureDestroyedException - if given fire engine is already destroyed
     * @throws FieldNotExtinguishableException - if given field is not extinguishable
     * @throws IdentifierNotFoundException - if given fire engine does not belong to current player
     * @throws NotEnoughActionPointsExceptions - if given fire engine does not have enough action points
     * @throws NotEnoughWaterException - if given fire engine does not contain enough water
     * @throws FieldNotReachableException - if field ist not reachable from position of given fire engine
     * @throws CoordinatesNotFoundException - if coordinates are not within board
     */
    public String[] extinguish(String identifier, Coordinates coordinates) throws FigureDestroyedException,
            FieldNotExtinguishableException, IdentifierNotFoundException,
            NotEnoughActionPointsExceptions, NotEnoughWaterException,
            FieldNotReachableException,
            FireEngineExtinguishedFieldAlreadyException, CoordinatesNotFoundException {

        String[] output = new String[2];

        FireEngine fireEngine = this.currentPlayer.getFireEngine(identifier);
        if (fireEngine.destroyed) {
            throw new FigureDestroyedException(this.currentPlayer.identifier);
        }
        if (!this.gameBoard.getField(coordinates).isExtinguishable()) {
            throw new FieldNotExtinguishableException(this.gameBoard.getField(coordinates).getIdentifier());
        }
        boolean distanceCheck = false;
        for (Field f : this.gameBoard.getAdjFields(fireEngine.position)) {
            if (f.coordinates.equals(coordinates)) {
                distanceCheck = true;
                break;
            }

        }
        if (!distanceCheck) throw new FieldNotReachableException(coordinates.toString(), fireEngine.toString());

        if (this.gameBoard.getField(coordinates).burns()) {
            this.currentPlayer.gainRep();
        }
        output[1] = String.valueOf(fireEngine.extinguish(coordinates));
        output[0] = this.gameBoard.extinguishField(coordinates).getIdentifier();


        if (this.gameBoard.containsBurningFields()) {
            return output;
        }
        this.gameWon = true;
        output[1] = null;
        output[0] = null;
        return output;
    }

    /**
     * Spread the fire on the game board according to a given dice roll
     * @param roll dice roll, which determines the direction of spreading
     * @return null if no player was lost; the next player if at least one player was lost
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     * @throws GameWonException - if this game is already won
     * @throws GameLostException - if this game is already lost
     * @throws ValueOutOfRangeException - if value is not a valid dice roll
     */
    public Player fireToRoll(int roll) throws WrongCommandSequenceException,
            CoordinatesNotFoundException,
            GameWonException,
            GameLostException, ValueOutOfRangeException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (!this.fireToRollNeeded) throw new WrongCommandSequenceException();
        boolean playerStillAlive = false;
        boolean playerLost = false;
        this.gameBoard.fireToRoll(roll);

        this.fireToRollNeeded = false;

        for (Player p : this.players) {
            if (p.alive) {
                if (p.checkIfAlive()) {
                    playerStillAlive = true;
                } else {
                    playerLost = true;
                }
            }
        }
        if (playerStillAlive) {
            if (playerLost) {
                if (this.currentPlayer.alive) {
                    return this.currentPlayer;
                }
                return this.getNextPlayer();
            }
            return null;
        }
        this.gameLost = true;
        return this.currentPlayer;
    }

    /**
     * Ends the current players turn
     * @return the identifier of the the next player
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     * @throws GameWonException - if this game is already won
     * @throws GameLostException - if this game is already lost
     */
    public String turn() throws WrongCommandSequenceException, GameWonException, GameLostException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (this.fireToRollNeeded) throw new WrongCommandSequenceException();

        this.currentPlayer.endTurn();

        if (this.currentPlayer.equals(this.getLastPlayer())) {
            this.fireToRollNeeded = true;
            players.add(players.remove(0));
            this.currentPlayer = this.getFirstPlayer();
        } else {
            this.currentPlayer = this.getNextPlayer();
        }

        return this.currentPlayer.identifier;
    }

    /**
     *
     * @param identifier identifier of fire engine that should refill
     * @return the remaining action points of given fire engine
     * @throws NotEnoughActionPointsExceptions - if given fire engine does not have enough action points
     * @throws FigureAlreadyFilledUpException - if given fire engine is already filled up
     * @throws FigureDestroyedException - if given fire engine is already destroyed
     * @throws IdentifierNotFoundException - if given fire engine does not belong to current player
     * @throws WaterNotAvailableException - if given fire engine is not surrounded by field with water
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     * @throws CoordinatesNotFoundException - if coordinates are not within game board
     * @throws GameWonException - if this game is already won
     * @throws GameLostException - if this game is already lost
     */
    public int refill(String identifier) throws
            NotEnoughActionPointsExceptions,
            FigureAlreadyFilledUpException,
            FigureDestroyedException,
            IdentifierNotFoundException,
            WaterNotAvailableException,
            WrongCommandSequenceException, CoordinatesNotFoundException, GameWonException, GameLostException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (this.fireToRollNeeded) throw new WrongCommandSequenceException();

        FireEngine toRefill = this.currentPlayer.getFireEngine(identifier);

        for (Field field : this.gameBoard.getAdjFields(toRefill.position)) {
            if (field.containsWater()) {
                toRefill.refill();
                return toRefill.getActionPoints();
            }
        }

        for (Field field : this.gameBoard.getDiagFields(toRefill.position)) {
            if (field.containsWater()) {
                toRefill.refill();
                return toRefill.getActionPoints();
            }
        }
        throw new WaterNotAvailableException();
    }

    /**
     * Buys new fire engine for current player and places it on given position
     * @param position position that the fire engine should spawn
     * @return remaining reputation points of current player
     * @throws FieldNotAvailableException - if given field is not available for a fire engine
     * @throws FieldToDistantException - if given field is to distant from the current player's fire station
     * @throws NotEnoughReputationException - if the current player does not have the required amount of reputation
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     * @throws CoordinatesNotFoundException - if coordinates are not within game board
     * @throws GameWonException - if this game is already won
     * @throws GameLostException - if this game is already lost
     */
    public int buyFireEngine(Coordinates position) throws FieldNotAvailableException,
            FieldToDistantException,
            NotEnoughReputationException,
            WrongCommandSequenceException,
            CoordinatesNotFoundException,
            GameWonException, GameLostException {
        this.checkGameStateAndPosition(position);

        if (!this.gameBoard.getField(position).isAvailableToFireEngine()) {
            throw new FieldNotAvailableException(this.gameBoard.getField(position).toString());
        }

        boolean adjFieldFound = false;
        for (Field f : this.gameBoard.getAdjFields(this.currentPlayer.fireStationPos)) {
            if (f.equals(this.gameBoard.getField(position))) {
                adjFieldFound = true;
                break;
            }
        }
        if (!adjFieldFound) {
            for (Field f : this.gameBoard.getDiagFields(this.currentPlayer.fireStationPos)) {
                if (f.equals(this.gameBoard.getField(position))) {
                    adjFieldFound = true;
                    break;
                }
            }
        }
        if (!adjFieldFound) {
            throw new FieldToDistantException(position.toString());
        }
        FireEngine newFireEngine = this.currentPlayer.buyFireEngine(position);
        this.gameBoard.placeFireEngine(newFireEngine, position);
        return this.currentPlayer.getReputation();
    }


    /**
     * Show all information of current player
     * @return String representation of the current player's status
     * @throws GameLostException - if this game is lost
     */
    public String showPlayer() throws GameLostException {
        if (this.gameLost) throw new GameLostException();
        return this.currentPlayer.toString();
    }

    /**
     * Check for game state and coordinates on the board
     * @param coordinates coordinates to be checked
     * @throws GameWonException - if game is already won
     * @throws GameLostException - if game is already lost
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed
     * @throws CoordinatesNotFoundException - if coordinates are not on board
     */
    public void checkGameStateAndPosition(Coordinates coordinates) throws GameWonException,
            GameLostException, WrongCommandSequenceException, CoordinatesNotFoundException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (this.fireToRollNeeded) throw new WrongCommandSequenceException();
        if (coordinates.y > this.gameBoard.dimensions.y || coordinates.x > this.gameBoard.dimensions.x) {
            throw new CoordinatesNotFoundException(coordinates);
        }
        if (coordinates.y < 0 || coordinates.x < 0) {
            throw new CoordinatesNotFoundException(coordinates);
        }
    }


    private Player getNextPlayer() {
        for (int i = 0; i < this.players.size(); i++) {
            if (this.currentPlayer.equals(this.players.get(i))) {
                for (int j = this.players.indexOf(this.currentPlayer) + 1; j < players.size(); j++) {
                    if (this.players.get(j).alive) {
                        return this.players.get(j);
                    }
                }
            }
        }
        return this.getFirstPlayer();
    }

    private Player getFirstPlayer() {
        for (Player p : this.players) {
            if (p.alive) {
                return p;
            }
        }
        return this.players.get(0);
    }

    private Player getLastPlayer() {
        for (int i = this.players.size() - 1; i > 0; i--) {
            if (this.players.get(i).alive) {
                return this.players.get(i);
            }
        }
        return this.players.get(this.players.size() - 1);
    }

    @Override
    public Game clone() {
        try {
            return new Game(this.fieldSizeBackup, this.fieldsBackup, this.playersBackup);
        } catch (GameBoardSizeNotOddExceptions gameBoardSizeNotOddExceptions) {
            return null;
        }
    }

}
