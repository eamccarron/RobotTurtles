package Model;

import java.util.HashMap;

import Controller.TileType;
import Controller.CardType;
public class Board {
    private int currentTurn = PLAYER_1;

    public final static int PLAYER_1 = 0;
    public final static int PLAYER_2 = 1;
    public final static int PLAYER_3 = 2;
    public final static int PLAYER_4 = 3;
    public static final int BOARD_SIZE = 64;

    //Variables for tracking game state
    private TurtleMaster[] players;
    private HashMap<Integer, TileType> layout = new HashMap<>();

    public Board(int numPlayers){
        players = new TurtleMaster[numPlayers];
        for(int i = 0; i < numPlayers; i++){ 
            players[i] = new TurtleMaster(i);
        }
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
            positions[i] = players[i].turtle.getPosition();
        }
        return positions;
    }

    public boolean playTurn(CardType card){
        //TODO check for illegal moves
        boolean successfulMove = players[currentTurn].onCardPlayed(card);
        if (!successfulMove)
            return false;
        return true;
    }

    public void onTurnEnded(){
        currentTurn++;
        if(currentTurn > 3) currentTurn = 0;
        System.out.println(currentTurn);
    }
}