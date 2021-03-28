package edu.kit.informatik.data;

import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.*;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Game class to model a game FireBreaker
 * @author Fabian Manuel Zürker Aguilar
 * @version 1.0
 */
public class Game {
    private static Coordinates fieldSizeBackup;
    private static Field[][] fieldsBackup;
    private static Player[] playersBackup;
    private GameBoard gameBoard;
    private ArrayList<Player> players = new ArrayList<>();
    private boolean gameLost = false;
    private boolean gameWon = false;
    private boolean firstPlayerTurn = false;
    private Player currentPlayer;


    /**
     * Constructor of a game of FireBreaker
     * @param fieldSize size of wanted game board
     * @param fields Fields that represent the game board; a Field[] of fields represents a horizontal line
     * @param players Players, that already have their FireEngines assigned in their starting order
     * @throws ValueOutOfRangeException - if fields does not have the dimensions given in fieldSize
     * @throws GameBoardSizeNotOddExceptions - if game board size not odd
     */
    public Game(Coordinates fieldSize, Field[][] fields, Player[] players) throws ValueOutOfRangeException,
            GameBoardSizeNotOddExceptions {
        fieldSizeBackup = fieldSize;
        fieldsBackup = fields;
        playersBackup = players;
        this.gameBoard = new GameBoard(fieldSize, fields);
        Collections.addAll(this.players, players);
        this.currentPlayer = this.getFirstPlayer();
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
    public String showField(Coordinates coordinates) throws CoordinatesNotFoundException, IdentifierNotFoundException {
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
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (this.firstPlayerTurn) throw new WrongCommandSequenceException();
        if (destination.y > this.gameBoard.dimensions.y || destination.x > this.gameBoard.dimensions.x) {
            throw new IdentifierNotFoundException(destination.toString());
        }

        FireEngine movingFigure = this.currentPlayer.getFireEngine(identifier);
        if (movingFigure.destroyed) {
            throw new FigureDestroyedException(identifier);
        }
        if (!movingFigure.isMovable()) {
            throw new FigureNotMovableException(identifier);
        }
        this.gameBoard.moveFireEngine(movingFigure, destination);
    }

    /**
     * Extinguishes a given field with a given fire engine
     * @param identifier identifier of fire engine
     * @param coordinates zero indexed coordinates of field to be extinguished
     * @return [<new state of field>, <water remaining in given fire engine>]; if game is won, both values are null;
     * @throws FigureDestroyedException - if given fire engine is already destroyed
     * @throws FieldNotExtinguishableException - if given field is not extinguishable
     * @throws IdentifierNotFoundException - if given fire engine does not belong to current player
     * @throws NotEnoughActionPointsExceptions - if given fire engine does not have enough action points
     * @throws NotEnoughWaterException - if given fire engine does not contain enough water
     * @throws FieldNotReachableException - if field ist not reachable from position of given fire engine
     * @throws CoordinatesNotFoundException - if coordinates are not within game board
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     * @throws GameWonException - if this game is already won
     * @throws GameLostException - if this game is already lost
     */
    public String[] extinguish(String identifier, Coordinates coordinates) throws FigureDestroyedException,
            FieldNotExtinguishableException, IdentifierNotFoundException,
            NotEnoughActionPointsExceptions, NotEnoughWaterException, WrongCommandSequenceException,
            FieldNotReachableException, CoordinatesNotFoundException, GameWonException, GameLostException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (this.firstPlayerTurn) throw new WrongCommandSequenceException();
        if (coordinates.y > this.gameBoard.dimensions.y || coordinates.x > this.gameBoard.dimensions.x) {
            throw new IdentifierNotFoundException(coordinates.toString());
        }

        String[] output = new String[2];

        FireEngine fireEngine = this.currentPlayer.getFireEngine(identifier);
        if (fireEngine.destroyed) {
            throw new FigureDestroyedException(this.currentPlayer.identifier);
        }
        if (!this.gameBoard.getField(coordinates).isExtinguishable()) {
            throw new FieldNotExtinguishableException(this.gameBoard.getField(coordinates).toString());
        }
        boolean distanceCheck = false;
        for (Field f : this.gameBoard.getAdjFields(coordinates)) {
            for (FireEngine fe : f.getFireEngineList()) {
                if (fe.equals(fireEngine)) {
                    distanceCheck = true;
                    break;
                }
            }
        }

        if (!distanceCheck) throw new FieldNotReachableException(coordinates.toString(), fireEngine.toString());

        this.currentPlayer.gainRep(Player.EXTINGUISH_REP_GAIN);

        output[0] = this.gameBoard.extinguishField(coordinates).getIdentifier();
        output[1] = String.valueOf(fireEngine.extinguish());

        if (this.gameBoard.containsBurningFields()) {
            return output;
        }
        this.gameWon = true;
        output[0] = null;
        output[1] = null;
        return output;
    }

    /**
     * Spread the fire on the game board according to a given dice roll
     * @param roll dice roll, which determines the direction of spreading
     * @return Array of all lost players
     * @throws WrongCommandSequenceException - if fire-to-roll needs to be executed first
     * @throws GameWonException - if this game is already won
     * @throws GameLostException - if this game is already lost
     */
    public Player[] fireToRoll(int roll) throws WrongCommandSequenceException,
            CoordinatesNotFoundException,
            GameWonException,
            GameLostException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (!this.firstPlayerTurn) throw new WrongCommandSequenceException();
        boolean playerStillAlive = false;
        Player[] lostPlayers = new Player[this.players.size()];
        this.gameBoard.fireToRoll(roll);

        this.firstPlayerTurn = false;

        for (Player p : this.players) {
            if (p.alive) {
                if (p.checkIfAlive()) {
                    playerStillAlive = true;
                } else {
                    lostPlayers[this.players.indexOf(p)] = p;
                }
            }
        }
        if (playerStillAlive) return lostPlayers;
        this.gameLost = true;
        return null;
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
        if (this.firstPlayerTurn) throw new WrongCommandSequenceException();

        this.currentPlayer.endTurn();

        if (this.currentPlayer.equals(this.getLastPlayer())) {
            this.firstPlayerTurn = true;
            players.add(players.remove(0));
            this.currentPlayer = this.getFirstPlayer();
        } else {
            this.currentPlayer = this.getNextPlayer();
        }

        return this.currentPlayer.identifier;
    }

    /**
     *
     * @param identifier
     * @return
     * @throws NotEnoughActionPointsExceptions
     * @throws FigureAlreadyFilledUpException
     * @throws FigureDestroyedException
     * @throws IdentifierNotFoundException
     * @throws WaterNotAvailableException
     * @throws WrongCommandSequenceException
     * @throws CoordinatesNotFoundException
     */
    public int refill(String identifier) throws
            NotEnoughActionPointsExceptions,
            FigureAlreadyFilledUpException,
            FigureDestroyedException,
            IdentifierNotFoundException,
            WaterNotAvailableException, WrongCommandSequenceException, CoordinatesNotFoundException {
        if (this.firstPlayerTurn) throw new WrongCommandSequenceException();

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
     *
     * @param position
     * @return
     * @throws FieldNotAvailableException
     * @throws FieldToDistantException
     * @throws NotEnoughReputationException
     * @throws WrongCommandSequenceException
     * @throws IdentifierNotFoundException
     * @throws CoordinatesNotFoundException
     */
    public int buyFireEngine(Coordinates position) throws FieldNotAvailableException,
            FieldToDistantException,
            NotEnoughReputationException,
            WrongCommandSequenceException,
            IdentifierNotFoundException,
            CoordinatesNotFoundException,
            GameWonException, GameLostException {
        if (this.gameWon) throw new GameWonException();
        if (this.gameLost) throw new GameLostException();
        if (this.firstPlayerTurn) throw new WrongCommandSequenceException();
        if (position.y > this.gameBoard.dimensions.y || position.x > this.gameBoard.dimensions.x) {
            throw new IdentifierNotFoundException(position.toString());
        }

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
        FireEngine newFireEngine = this.currentPlayer.addFireEngine(position.y, position.x);
        this.gameBoard.placeFireEngine(newFireEngine, position);
        return this.currentPlayer.getReputation();
    }

    /**
     *
     * @return
     * @throws ValueOutOfRangeException
     * @throws GameBoardSizeNotOddExceptions
     * @throws IdentifierNotFoundException
     */
    public void reset() throws ValueOutOfRangeException, GameBoardSizeNotOddExceptions, IdentifierNotFoundException {
        Game newGame =  new Game(fieldSizeBackup, fieldsBackup, playersBackup);
        this.gameLost = false;
        this.gameBoard = newGame.gameBoard;
        this.players = newGame.players;
        this.currentPlayer = this.getFirstPlayer();
    }

    /**
     *
     * @return
     * @throws IdentifierNotFoundException
     * @throws GameLostException - if this game is lost
     */
    public String showPlayer() throws GameLostException {
        if (this.gameLost) throw new GameLostException();
        return this.currentPlayer.toString();
    }


    private Player getNextPlayer() {
        for (int i = 0; i < this.players.size(); i++) {
            if (currentPlayer.equals(this.players.get(i))) {
                for (int j = this.players.indexOf(currentPlayer) + 1; j < players.size(); j++) {
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

}
