package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.TileType;
import Controller.CardType;
import Controller.Controller;

public class Board implements BoardManager{

    public static final int BOARD_SIZE = 64;
    public static final int BOARD_LENGTH = (int)Math.floor(Math.sqrt(BOARD_SIZE));
    private static final int[] MIDDLE_POSITIONS = {27, 28, 35, 36}; //TODO Calculate based on board size
    //Variables for tracking game state
    private boolean[] occupiedPositions = new boolean[BOARD_SIZE];
    private ArrayList<Integer> winOrder;
    private HashMap<Integer, TileType> layout = new HashMap<>(BOARD_SIZE);
    private Turtle[] turtles = new Turtle[4];

    public Board(int numPlayers){
        winOrder = new ArrayList<>(numPlayers);
        for(int i = 0; i < numPlayers; i++){ 
            turtles[i] = new Turtle(i, this);
        }
        for(int position : MIDDLE_POSITIONS){
            layout.put(position, TileType.JEWEL);
        }
    }

    public static void main(String[] args){
        TurtleMover turtleMover = new TurtleMover();
        Board board = new Board(TurtleMover.NUM_PLAYERS);
        Turtle[] turtles = board.getTurtles();
        for(int i = 0; i < TurtleMover.NUM_PLAYERS; i++)
            turtleMover.addPlayer(turtles[i], i);

        Controller.initGame(board, turtleMover);
    }

    //This method is used only for the instantiation of a TurnManager in the main method.
    private Turtle[] getTurtles(){
        return turtles;
    }

    public boolean isPositionOccupied(int position){
        return occupiedPositions[position];
    }

    public void setOccupied(int position){
        occupiedPositions[position] = true;
    }

    public void setUnoccupied(int position){
        occupiedPositions[position] = false;
    }

    public TileType getTile(int position){
        return layout.getOrDefault(position, TileType.EMPTY);
    }


	public HashMap<Integer, TileType> getTileLayout() {
        return this.layout;
	}

	public int[] getPlayerPositions() {
        int[] positions = new int[turtles.length];
        for(int i = 0; i < positions.length; i++){
            positions[i] = turtles[i].getPosition();
        }
        return positions;
    }


	public int[] getPlayerDirections() {
        int[] directions = new int[turtles.length];
        for(int i = 0; i < turtles.length; i++)
            directions[i] = turtles[i].getDirection();
        return directions;
	}
}