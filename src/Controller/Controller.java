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
    private GameView view;
    private BoardManager board;
    private TurnManager turnManager;

    private static final int NUM_PLAYERS = 4;
    private static ArrayList<Integer> winOrder = new ArrayList<>(NUM_PLAYERS);
    private static Controller instance;
    //Start event driven game loop by initializing a new game

    private Controller(){};

    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    //Initializes a new game by instantiating a board and a view.
    public void initGame(BoardManager board, TurnManager turnManager){
        HashMap<Integer, TileType> layout = board.getTileLayout();
        int[] playerPositions = board.getPlayerPositions();
        this.board = board;
        this.turnManager = turnManager;
        view = new GameView(board.getBoardSize(), playerPositions, board.getPlayerDirections(), layout); 
        view.getNextCard(turnManager.getActivePlayerNumber());
    }

    public void registerCardStack(CardStack cardStack){
        CardStackListener listener = new CardStackListener();
        cardStack.addActionListener(listener);
    }

    //The handler function for when a card is chosen.  This is called whenever a CardStack is clicked.
    private boolean onCardChosen(CardType cardChosen) {
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

    private class CardStackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert e.getSource() instanceof CardStack; // This listener should only be used for cardStacks
            CardType cardChosen = ((CardStack) (e.getSource())).getCardType();
            System.out.println(cardChosen);
            Controller controller = getInstance();
            if(!controller.onCardChosen(cardChosen)){
                System.out.println("Illegal Move");
                view.promptIllegalMove();
                view.getNextCard(turnManager.getActivePlayer().getNumber());
            }else if(cardChosen != CardType.BUG){  //Only prompt end turn if the bug card was not chosen
                view.promptEndTurn();
            }
        }
    }

	public void onTurnEnded() {
        turnManager.endTurn();
        view.getNextCard(turnManager.getActivePlayer().getNumber());
	}

	public void onGameEnded() {
        view.showEndGameScreen(winOrder);
	}
}
