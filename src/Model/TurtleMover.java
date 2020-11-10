package Model;

import Controller.Controller;

//A Class representing the "TurtleMover" of the game, which is responsible for tracking the players and their turns.
public class TurtleMover implements TurnManager{
    public static final int NUM_PLAYERS = 4;
    public final static int PLAYER_1 = 0;
    public final static int PLAYER_2 = 1;
    public final static int PLAYER_3 = 2;
    public final static int PLAYER_4 = 3;

    private TurtleMaster[] players = new TurtleMaster[NUM_PLAYERS];
    private int currentTurn = PLAYER_1;
    
    public TurtleMover(){
        
    }

	@Override
	public int getActivePlayerNumber() {
        return players[currentTurn].getNumber();
	}

	@Override
	public TurtleMaster getActivePlayer() {
        return players[currentTurn];
	}

	@Override
	public void endTurn() {
        //Check if all players have won
        boolean gameOver = true;
        for(TurtleMaster player : players){
            gameOver = gameOver && player.hasWon();
        }
        //If all players have won, end game
        if(gameOver){
            Controller.onGameEnded(); 
            return;
        }

        do{ 
            currentTurn = (currentTurn + 1) % 4;
        }while(getActivePlayer().hasWon());

	}

    @Override
    public void addPlayer(Turtle turtle, int playerNumber) {
       players[playerNumber] = new TurtleMaster(playerNumber, turtle); 
    }
}
