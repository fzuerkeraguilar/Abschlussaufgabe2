package edu.kit.informatik.data;

import edu.kit.informatik.data.fields.Field;
import edu.kit.informatik.data.fields.FireStation;
import edu.kit.informatik.data.playfigures.Figure;
import edu.kit.informatik.data.playfigures.FireEngine;
import edu.kit.informatik.data.resources.Coordinates;
import edu.kit.informatik.data.resources.exceptions.GameBoardSIzeNotOddExceptions;
import edu.kit.informatik.data.resources.exceptions.ValueOutOfRangeException;
import edu.kit.informatik.io.resources.exceptions.FalseFormattingException;

import java.util.LinkedHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Game {
    private static GameBoard board;
    private static final LinkedHashMap<String, Player> PLAYERNAME_HASH_MAP = new LinkedHashMap<>();
    private static final String STARTING_PLAYER = "A";
    private static Player currentPlayer;
    private static Coordinates fieldSizeBackup;
    private static Field[][] fieldsBackup;
    private static Player[] playersBackup;


    public Game(Coordinates fieldSize, Field[][] fields, Player[] players) throws ValueOutOfRangeException, GameBoardSIzeNotOddExceptions {
        fieldSizeBackup = fieldSize;
        fieldsBackup = fields;
        playersBackup = players;
        board = new GameBoard(fieldSize, fields);
        for(Player p : players) {
            PLAYERNAME_HASH_MAP.put(p.identifier, p);
        }
        currentPlayer = this.getPlayer(STARTING_PLAYER);
    }

    @Override
    public String toString() {
        return board.toString();
    }

    public String showField(Coordinates coordinates) {
        return board.getField(coordinates).showInformation();
    }

    public void move(String identifier, Coordinates destination) throws FalseFormattingException {
        Pattern fireEnginePattern = Pattern.compile(FireEngine.REGEX);
        Matcher fireEngineMatcher = fireEnginePattern.matcher(identifier);
        String playerID = fireEngineMatcher.group(1);
        if (!currentPlayer.equals(this.getPlayer(playerID)))  {
            throw new FalseFormattingException("it's another players turn", "player");
        }

        Player movingPlayer = this.getPlayer(playerID);
        FireEngine movingFigure = movingPlayer.getFireEngine(identifier);
        if(movingFigure.destroyed) {
            throw new FalseFormattingException("is destroyed", "");
        }
        if(!movingFigure.isMovable()) {
            throw new FalseFormattingException("not allowed to move", "");
        }
        if(movingFigure.position.distance(destination) > FireEngine.MAX_MOVEMENT) {
            throw new FalseFormattingException("movement to big", "");
        }
        //TODO distance check
        board.moveFigure(movingFigure, destination);
        //board.getField(movingFigure.position).removeFigure(identifier);
        //board.getField(destination).addFigure(movingFigure);
        //movingFigure.move(destination);
    }

    public void extinguish(String identifier, Coordinates coordinates) throws FalseFormattingException {
        Pattern fireEnginePattern = Pattern.compile(FireEngine.REGEX);
        Matcher fireEngineMatcher = fireEnginePattern.matcher(identifier);
        String playerID = fireEngineMatcher.group(1);
        if (!currentPlayer.equals(this.getPlayer(playerID))) {
            throw new FalseFormattingException("player", "player");
        }
        Player movingPlayer = this.getPlayer(playerID);
        FireEngine movingFigure = movingPlayer.getFireEngine(identifier);
        if(movingFigure.destroyed) {
            throw new FalseFormattingException("is destored", "");
        }
        if(!board.getField(coordinates).isExtinguishable()) {
            throw new FalseFormattingException("not extinguishable", "");
        }
        if(!movingFigure.canExtinguish()) {
            throw new FalseFormattingException("cannot extinguish", "");
        }
        //TODO distance check
        board.extinguishField(coordinates);
    }

    public String fireToRoll(int roll) throws FalseFormattingException {
        board.fireToRoll(roll);
        for(Player p : PLAYERNAME_HASH_MAP.values()) {
            if(p.alive) {
                if(p.isAlive()) {
                    return "OK";
                } else {
                    p.alive = false;
                    return this.getNextPlayer(currentPlayer.identifier).identifier;
                }
            }
        }
        return "lose";
    }

    public String turn() throws FalseFormattingException {
        currentPlayer = this.getNextPlayer(currentPlayer.identifier);
        return currentPlayer.identifier;
    }

    public void refill(String identifier) throws FalseFormattingException {
        FireEngine toRefill = currentPlayer.getFireEngine(identifier);
        board.addAdjFields(toRefill.position, 0);
        board.addDiagFields(toRefill.position, 0);
        for(Field field : board.getField(toRefill.position).adjFields) {
            if(field.containsWater()) {
                toRefill.refill();
                return;
            }
        }

        for(Field field : board.getField(toRefill.position).diagFields) {
            if(field.containsWater()) {
                toRefill.refill();
                return;
            }
        }
        throw new FalseFormattingException("not besides a water source", "");
    }

    public void buyFireEngine(Coordinates position) throws FalseFormattingException {
        if(currentPlayer.fireStationPos.distance(position) > FireStation.FIRE_ENGINE_SPAWNING_RADIUS) {
            throw new FalseFormattingException("distance from fire station to big", "");
        }
        FireEngine newFireEngine = currentPlayer.addFireEngine(position.y, position.x);
        board.placeFireEngine(newFireEngine, position);
    }

    public Game reset() throws ValueOutOfRangeException, GameBoardSIzeNotOddExceptions {
        return new Game(fieldSizeBackup, fieldsBackup, playersBackup);
    }

    private Player getPlayer(String identifier) {
        return PLAYERNAME_HASH_MAP.get(identifier);
    }

    private Player getNextPlayer(String currentPlayerIdentifier) throws FalseFormattingException {
        Player nextPlayer = null;
        switch (currentPlayerIdentifier) {
            case "A": nextPlayer = this.getPlayer("B");
            case "B": nextPlayer = this.getPlayer("C");
            case "C": nextPlayer = this.getPlayer("D");
            case "D": nextPlayer = this.getPlayer("A");
        }
        if(nextPlayer == null){
            throw new FalseFormattingException("identifier not found", "");
        }
        if(!nextPlayer.isAlive()) {
            nextPlayer = getPlayer(nextPlayer.identifier);
        }
        return nextPlayer;
    }


}
