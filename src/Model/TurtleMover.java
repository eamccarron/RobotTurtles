package Model;

import Controller.Controller;
import Controller.TurnManager;
import Controller.Player;
//A Class representing the "TurtleMover" of the game, which is responsible for updating turns and the win state
public class TurtleMover implements TurnManager {
    public static final int NUM_PLAYERS = 4;
    public final static int PLAYER_1 = 0;
    public final static int PLAYER_2 = 1;
    public final static int PLAYER_3 = 2;
    public final static int PLAYER_4 = 3;

    public Controller controller;
    private Turtle[] players = new Turtle[NUM_PLAYERS];
    private int currentTurn = PLAYER_1;
    
    public TurtleMover(){
        controller = Controller.getInstance();
    }

    @Override
    public int getNumPlayers() {
       return NUM_PLAYERS;
    }

    @Override
	public int getActivePlayerNumber() {
        return players[currentTurn].getNumber();
	}

	@Override
	public Player getActivePlayer() {
        return players[currentTurn];
	}

    @Override
    public Player getPlayer(int playerNum) {
       return players[playerNum];
    }

    @Override
	public void endTurn() {
        //Check if all players have won
        boolean gameOver = true;
        for(Player player : players){
            gameOver = gameOver && player.hasWon();
        }
        //If all players have won, end game
        if(gameOver){
            controller.onGameEnded();
            return;
        }

        do{ 
            currentTurn = (currentTurn + 1) % 4;
        }while(getActivePlayer().hasWon());



	}

    @Override
    public void addPlayer(Turtle turtle, int playerNumber) {
       players[playerNumber] = turtle;
    }
}
