package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.TileType;
import Controller.CardType;
import Controller.TurtleMover;

public class Board {
    private int currentTurn = PLAYER_1;

    public final static int PLAYER_1 = 0;
    public final static int PLAYER_2 = 1;
    public final static int PLAYER_3 = 2;
    public final static int PLAYER_4 = 3;
    public static final int BOARD_SIZE = 64;
    public static final int BOARD_LENGTH = (int)Math.floor(Math.sqrt(BOARD_SIZE));
    private static final int[] MIDDLE_POSITIONS = {27, 28, 35, 36}; //TODO Calculate based on board size
    //Variables for tracking game state
    private TurtleMaster[] players;
    private boolean[] occupiedPositions = new boolean[BOARD_SIZE];
    private ArrayList<Integer> winOrder;
    private HashMap<Integer, TileType> layout = new HashMap<>(BOARD_SIZE);

    public Board(int numPlayers){
        players = new TurtleMaster[numPlayers];
        winOrder = new ArrayList<>(numPlayers);
        for(int i = 0; i < numPlayers; i++){ 
            players[i] = new TurtleMaster(i, this);
        }
        for(int position : MIDDLE_POSITIONS){
            layout.put(position, TileType.JEWEL);
        }
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

    public TurtleMaster getActivePlayer(){
        return players[currentTurn];
    }

	public HashMap<Integer, TileType> getTileLayout() {
        return this.layout;
	}

	public int[] getPlayerPositions() {
        int[] positions = new int[players.length];
        for(int i = 0; i < positions.length; i++){
            positions[i] = players[i].getTurtlePosition();
        }
        return positions;
    }

    public boolean playTurn(CardType card){
        TurtleMaster activePlayer = this.getActivePlayer();
        if(!activePlayer.onCardPlayed(card))
            return false;
        
        int playerPos = activePlayer.getTurtlePosition();
        if(this.getTile(playerPos) == TileType.JEWEL){
            activePlayer.setHasWon();
            winOrder.add(activePlayer.getNumber());
        }
        return true;
        }

    public void onTurnEnded(){
        //Check if all players have won
        boolean gameOver = true;
        for(TurtleMaster player : players){
            gameOver = gameOver && player.hasWon();
        }
        //If all players have won, end game
        if(gameOver){
           TurtleMover.onGameEnded(winOrder); 
        }

        do{ 
            currentTurn = (currentTurn + 1) % 4;
        }while(getActivePlayer().hasWon());
    }

	public int[] getPlayerDirections() {
        int[] directions = new int[players.length];
        for(int i = 0; i < players.length; i++)
            directions[i] = players[i].getTurtleDirection();
        return directions;
	}
}