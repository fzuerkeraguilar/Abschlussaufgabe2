package edu.kit.informatik.data;

import edu.kit.informatik.Terminal;
import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.*;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.ArrayList;
import java.util.Collections;

public class Game {
    private final GameBoard board;
    private final ArrayList<Player> PLAYER_LIST = new ArrayList<>();
    private boolean lastPlayerTurn = false;
    private static final String STARTING_PLAYER = "A";
    private Player currentPlayer;
    private static Coordinates fieldSizeBackup;
    private static Field[][] fieldsBackup;
    private static Player[] playersBackup;


    public Game(Coordinates fieldSize, Field[][] fields, Player[] players) throws ValueOutOfRangeException, GameBoardSIzeNotOddExceptions, IdentifierNotFoundException {
        fieldSizeBackup = fieldSize;
        fieldsBackup = fields;
        playersBackup = players;
        this.board = new GameBoard(fieldSize, fields);
        Collections.addAll(this.PLAYER_LIST, players);
        this.currentPlayer = this.getFirstPlayer();
    }

    @Override
    public String toString() {
        return this.board.toString();
    }

    //Coordinates zero indexed
    public String showField(Coordinates coordinates) throws IdentifierNotFoundException {
        if(coordinates.y > this.board.DIM.y || coordinates.x > this.board.DIM.x) {
            throw new IdentifierNotFoundException(coordinates.toString());
        }
        return this.board.getField(coordinates).showInformation();
    }

    //Coordinates zero indexed
    public void move(String identifier, Coordinates destination) throws FigureDestroyedException, IdentifierNotFoundException, OtherPlayersTurnException, FigureNotMovableException, FieldNotReachableException, FieldNotAvailableException, WrongCommandSequenceException {
        if(this.lastPlayerTurn) throw new WrongCommandSequenceException();
        /*Pattern fireEnginePattern = Pattern.compile(FireEngine.REGEX);
        Matcher fireEngineMatcher = fireEnginePattern.matcher(identifier);
        String playerID = fireEngineMatcher.group(1);
        if (!currentPlayer.equals(this.getPlayer(playerID)))  {
            throw new OtherPlayersTurnException(currentPlayer.identifier);
        }*/

        FireEngine movingFigure = this.currentPlayer.getFireEngine(identifier);
        if(movingFigure.destroyed) {
            throw new FigureDestroyedException(identifier);
        }
        if(!movingFigure.isMovable()) {
            throw new FigureNotMovableException(identifier);
        }
        this.board.moveFigure(movingFigure, destination);
    }

    public String[] extinguish(String identifier, Coordinates coordinates) throws  FigureDestroyedException, FieldNotExtinguishableException, IdentifierNotFoundException, NotEnoughActionPointsExceptions, NotEnoughWaterException, OtherPlayersTurnException, WrongCommandSequenceException, FieldNotReachableException {
        if(this.lastPlayerTurn) throw new WrongCommandSequenceException();

        String[] output = new String[2];

        FireEngine fireEngine = this.currentPlayer.getFireEngine(identifier);
        if(fireEngine.destroyed) {
            throw new FigureDestroyedException(this.currentPlayer.identifier);
        }
        if(!this.board.getField(coordinates).isExtinguishable()) {
            throw new FieldNotExtinguishableException(this.board.getField(coordinates).toString());
        }
        boolean distanceCheck = false;
        for(Field f : this.board.getAdjFields(coordinates)) {
            Terminal.printLine(f.toString());
            for(FireEngine fe : f.getFiguresOnField()) {
                Terminal.printLine(fe.toString());
                if(fe.equals(fireEngine)) {
                    distanceCheck = true;
                    break;
                }
            }
        }

        if(!distanceCheck) throw new FieldNotReachableException(coordinates.toString(), fireEngine.toString());

        output[0] = this.board.extinguishField(coordinates).toString();
        output[1] = String.valueOf(fireEngine.extinguish());

        if(this.board.containsBurningFields()) {
            return output;
        }
        output[0] = null;
        output[1] = null;
        return output;
     }

    public Player[] fireToRoll(int roll) throws WrongCommandSequenceException {
        if(!this.lastPlayerTurn) throw new WrongCommandSequenceException();
        boolean playerAlive = false;
        Player[] lostPlayers = new Player[this.PLAYER_LIST.size()];
        this.board.fireToRoll(roll);
        for(Player p : this.PLAYER_LIST) {
            if(p.alive){
                if (p.checkIfAlive()) {
                    playerAlive = true;
                } else {
                    lostPlayers[this.PLAYER_LIST.indexOf(p)] = p;
                }
            }
        }
        if(playerAlive) return lostPlayers;
        return null;
    }

    public String turn() throws WrongCommandSequenceException {
        if(this.lastPlayerTurn) throw new WrongCommandSequenceException();
        this.currentPlayer.endTurn();
        if(this.currentPlayer.equals(this.getLastPlayer())) {
            this.lastPlayerTurn = true;
        }
        //PLAYER_LIST.add(PLAYER_LIST.remove(0));
        this.currentPlayer = this.getNextPlayer();
        return this.currentPlayer.identifier;
    }

    public int refill(String identifier) throws
            NotEnoughActionPointsExceptions,
            FigureAlreadyFilledUpException,
            FigureDestroyedException, IdentifierNotFoundException, WaterNotAvailableException, WrongCommandSequenceException {
        if(this.lastPlayerTurn) throw new WrongCommandSequenceException();

        FireEngine toRefill = this.currentPlayer.getFireEngine(identifier);
        //board.addAdjFields(toRefill.position);
        //board.addDiagFields(toRefill.position);
        for(Field field : this.board.getAdjFields(toRefill.position)) {
            if(field.containsWater()) {
                toRefill.refill();
                return toRefill.getActionPoints();
            }
        }

        for(Field field : this.board.getDiagFields(toRefill.position)) {
            if(field.containsWater()) {
                toRefill.refill();
                return toRefill.getActionPoints();
            }
        }
        throw new WaterNotAvailableException();
    }

    public int buyFireEngine(Coordinates position) throws FieldNotAvailableException, FieldToDistantException, NotEnoughReputationException, WrongCommandSequenceException {
        if(this.lastPlayerTurn) throw new WrongCommandSequenceException();
        if(!this.board.getField(position).isAvailableToFireEngine()) {
            throw new FieldNotAvailableException(this.board.getField(position).toString());
        }
        boolean adjFieldFound = false;
        for(Field f : this.board.getAdjFields(this.currentPlayer.fireStationPos)) {
            if(f.equals(this.board.getField(position))) {
                adjFieldFound = true;
                break;
            }
        }
        if(!adjFieldFound) {
            for(Field f : this.board.getDiagFields(this.currentPlayer.fireStationPos)) {
                if(f.equals(this.board.getField(position))) {
                    adjFieldFound = true;
                    break;
                }
            }
        }
        if(!adjFieldFound) {
            throw new FieldToDistantException(position.toString());
        }
        FireEngine newFireEngine = this.currentPlayer.addFireEngine(position.y, position.x);
        this.board.placeFireEngine(newFireEngine, position);
        return this.currentPlayer.getReputation();
    }

    public Game reset() throws ValueOutOfRangeException, GameBoardSIzeNotOddExceptions, IdentifierNotFoundException {
        return new Game(fieldSizeBackup, fieldsBackup, playersBackup);
    }

    public String showPlayer() throws IdentifierNotFoundException {
        return this.currentPlayer.toString();
    }

    //TODO aufräumen
    private Player getPlayer(String identifier) throws IdentifierNotFoundException {
        for(Player p : this.PLAYER_LIST) {
            if(p.identifier.equals(identifier)) return p;
        }
        throw new IdentifierNotFoundException(identifier);
    }

    private Player getNextPlayer() {
        for(int i = 0; i < this.PLAYER_LIST.size(); i++) {
            if(currentPlayer.equals(this.PLAYER_LIST.get(i))) {
                for(int j = this.PLAYER_LIST.indexOf(currentPlayer) + 1; j < PLAYER_LIST.size(); j++) {
                    if(this.PLAYER_LIST.get(j).alive) {
                        return this.PLAYER_LIST.get(j);
                    }
                }
            }
        }
        return this.getFirstPlayer();
    }

    private Player getFirstPlayer() {
        for(Player p : this.PLAYER_LIST) {
            if(p.alive) {
                return p;
            }
        }
        return this.PLAYER_LIST.get(0);
    }

    private Player getLastPlayer() {
        for(int i = this.PLAYER_LIST.size() - 1; i > 0; i--){
            if(this.PLAYER_LIST.get(i).alive) {
                return this.PLAYER_LIST.get(i);
            }
        }
        return this.PLAYER_LIST.get(this.PLAYER_LIST.size() - 1);
    }

}
