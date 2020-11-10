package Controller;

import View.GameView;
import View.CardStack;

import Model.BoardManager;
import Model.CardPlayer;
import Model.TurnManager;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;


//The main controller for the game, responsible for main game loop and determining win conditions 
public class Controller {
    private static GameView view;
    private static BoardManager board;
    private static TurnManager turnManager;

    private static final int NUM_PLAYERS = 4;
    private static ArrayList<Integer> winOrder = new ArrayList<>(NUM_PLAYERS);
    //Start event driven game loop by initializing a new game

    //Initializes a new game by instantiating a board and a view.
    public static void initGame(BoardManager board, TurnManager turnManager){
        HashMap<Integer, TileType> layout = board.getTileLayout();
        int[] playerPositions = board.getPlayerPositions();
        Controller.board = board;
        Controller.turnManager = turnManager;
        view = new GameView(board.getBoardSize(), playerPositions, board.getPlayerDirections(), layout); 
        view.getNextCard(turnManager.getActivePlayerNumber());
    }

    public static void registerCardStack(CardStack cardStack){
        CardStackListener listener = new CardStackListener();
        cardStack.addActionListener(listener);
    }

    //The handler function for when a card is chosen.  This is called whenever a CardStack is clicked.
    private static boolean onCardChosen(CardType cardChosen) {
        CardPlayer activePlayer = turnManager.getActivePlayer();
        int activePlayerNum = turnManager.getActivePlayerNumber();

        if(!activePlayer.onCardPlayed(cardChosen))
            return false;

        int playerPos = board.getPlayerPositions()[turnManager.getActivePlayerNumber()];
        if(board.getTile(playerPos) == TileType.JEWEL){
            activePlayer.setHasWon();
            view.promptWin(activePlayerNum);
            winOrder.add(turnManager.getActivePlayerNumber());
        }
        //Retrieve updated game state from board.
        HashMap<Integer, TileType> layout = board.getTileLayout();
        int[] playerPositions = board.getPlayerPositions();
        int[] playerDirections = board.getPlayerDirections();

        if(cardChosen == CardType.BUG) //If the bug card was chosen, prompt the player to choose a new move
            view.getNextCard(activePlayerNum);

        //Render new state in GameView
        view.updatePlayerPosition(activePlayerNum, playerPositions[activePlayerNum]);
        view.updatePlayerDirection(activePlayerNum, playerDirections[activePlayerNum]);
        view.updateTiles(layout);
        view.redraw();
        return true;
    }

    private static class CardStackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert e.getSource() instanceof CardStack; // This listener should only be used for cardStacks
            CardType cardChosen = ((CardStack) (e.getSource())).getCardType();
            System.out.println(cardChosen);
            if(!Controller.onCardChosen(cardChosen)){
                System.out.println("Illegal Move");
                view.promptIllegalMove();
                view.getNextCard(turnManager.getActivePlayer().getNumber());
            }else if(cardChosen != CardType.BUG){  //Only prompt end turn if the bug card was not chosen
                view.promptEndTurn();
            }
        }
    }

	public static void onTurnEnded() {
        turnManager.endTurn();
        view.getNextCard(turnManager.getActivePlayer().getNumber());
	}

	public static void onGameEnded() {
        view.showEndGameScreen(winOrder);
	}
}
