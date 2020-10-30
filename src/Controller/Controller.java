package Controller;

import View.GameView;
import Model.*;

import java.util.ArrayList;
import java.util.HashMap;

//The main controller for the game, responsible for main game loop and tracking turns.
public class Controller {
    private static GameView view;
    private static Board board;
    private static final int NUM_PLAYERS = 4;

    //Start event driven game loop by initializing a new game
    public static void main(String[] args) { Controller.initGame(); }

    //Initializes a new game by instantiating a board and a view.
    public static void initGame(){
        board = new Board(NUM_PLAYERS);
        HashMap<Integer, TileType> layout = board.getTileLayout();
        int[] playerPositions = board.getPlayerPositions();
        TurtleMaster activePlayer = board.getActivePlayer();

        view = new GameView(Board.BOARD_SIZE, playerPositions, board.getPlayerDirections(), layout); 
        view.getNextCard(activePlayer.getNumber());
    }

    //The handler function for when a card is chosen.  This is called whenever a CardStack is clicked.
    public static boolean onCardChosen(CardType cardChosen) {
        HashMap<Integer, TileType> layout = board.getTileLayout(); //Update tileLayout (in case in a future version there are tiles that can be moved)
        TurtleMaster activePlayer = board.getActivePlayer();
        //Update state of model        
        if(!board.playTurn(cardChosen)) //Check to see if the card chosen is a valid move.t 
            return false;
        //Render new state in GameView
        view.updatePlayerPosition(activePlayer.getNumber(), activePlayer.getTurtlePosition());
        view.updatePlayerDirection(activePlayer.getNumber(), activePlayer.getTurtleDirection());
        view.updateTiles(layout);
        view.redraw();
        if(cardChosen == CardType.BUG) //If the bug card was chosen, prompt the player to choose a new move
            view.getNextCard(activePlayer.getNumber());
        if(activePlayer.hasWon())
            view.promptWin(activePlayer.getNumber());
        return true;
    }

	public static void onTurnEnded() {
        board.onTurnEnded();
        view.getNextCard(board.getActivePlayer().getNumber());
	}

	public static void onGameEnded(ArrayList<Integer> winOrder) {
        view.showEndGameScreen(winOrder);
	}
}
