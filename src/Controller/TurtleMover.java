package Controller;

import View.GameView;
import Model.*;

import java.util.HashMap;

//The controller for the game, responsible for main game loop and tracking turns. Static since each game should only have one controller.
public class TurtleMover {
    private static GameView view;
    private static Board board;
    private static int numPlayers;
    
    private static void initGame(int numPlayers){
        TurtleMover.numPlayers = numPlayers;
        board = new Board(4);
        HashMap<Integer, TileType> layout = board.getTileLayout();
        int[] playerPositions = board.getPlayerPositions();
        TurtleMaster activePlayer = board.getActivePlayer();

        view = new GameView(Board.BOARD_SIZE, playerPositions, layout); 
        view.getNextCard(activePlayer.getNumber());
    }

    public static void main(String[] args) {
        //Start event driven game loop
        TurtleMover.initGame(4);
    }

    public static boolean onCardChosen(CardType cardChosen) {
        HashMap<Integer, TileType> layout = board.getTileLayout();
        TurtleMaster activePlayer = board.getActivePlayer();
        //Update state of model        
        board.playTurn(cardChosen);
        //Render new state in GameView
        view.updatePlayerPosition(activePlayer.getNumber(), activePlayer.getTurtlePosition());
        view.updatePlayerDirection(activePlayer.getNumber(), activePlayer.getTurtleDirection());
        view.updateTiles(layout);
        view.redraw();
        return true;
    }

	public static void onTurnEnded() {
        board.onTurnEnded();
        view.getNextCard(board.getActivePlayer().getNumber());
	}
}
