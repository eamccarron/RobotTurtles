package Controller;

import Model.*;
import View.GameView;
import View.CardStack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

//The main controller for the game, responsible for main game loop and determining win conditions
//SINGLETON
public class Controller {
    private GameView view;
    private BoardManager board;
    private TurnManager turnManager;
    private static ArrayList<Integer> winOrder;
    private static Controller instance;
    private boolean playThreeEnabled = false;
    private Queue<CardType> cardsChosen;
    //Start event driven game loop by initializing a new game

    private Controller(){
    }


    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public static void main(String[] args){
        TurtleMover turtleMover = new TurtleMover();
        Board board = Board.getInstance();
        Turtle[] turtles = new Turtle[TurtleMover.NUM_PLAYERS];
        for(int i = 0; i < TurtleMover.NUM_PLAYERS; i++) {
            turtles[i] = new Turtle(i);
            turtleMover.addPlayer(turtles[i], i);
        }
        board.setTurtles(turtles);
        Controller controller = Controller.getInstance();
        controller.initGame(board, turtleMover);
    }

    //Initializes a new game by instantiating a board and a view.
    public void initGame(BoardManager board, TurnManager turnManager){
        HashMap<Integer, Tile> layout = board.getTileLayout();
        winOrder = new ArrayList<>(turnManager.getNumPlayers());
        cardsChosen = new LinkedList<>();
        int[] playerPositions = board.getPlayerPositions();
        this.board = board;
        this.turnManager = turnManager;
        view = new GameView(board.getBoardSize(), playerPositions, board.getPlayerDirections(), layout);
        for(int i = 0; i < turnManager.getNumPlayers(); i++){
            turnManager.getPlayer(i).addSubscriber(view.getBoardPanel());
        }
        view.getNextCard(turnManager.getActivePlayerNumber());
    }

    public void registerCardStack(CardStack cardStack){
        CardStackListener listener = new CardStackListener();
        cardStack.addActionListener(listener);
    }
    public void registerPlayThreeToggle(JCheckBox playThreeToggle){
        playThreeToggle.addItemListener(new PlayThreeListener());
    }
    //The handler function for when a card is chosen.  This is called whenever a CardStack is clicked.
    private boolean onCardChosen(CardType cardChosen) {
        if(cardsChosen.peek() == CardType.BUG) //If bug is the most recent card chosen remove it from queue
            cardsChosen.poll();

        Player activePlayer = turnManager.getActivePlayer();
        int activePlayerNum = turnManager.getActivePlayerNumber();

        if(cardChosen == CardType.BUG){
            cardsChosen.clear();
        }

        cardsChosen.offer(cardChosen);
        System.out.println(cardsChosen.size());

        if(playThreeEnabled && cardsChosen.size() < 3 && cardChosen != CardType.BUG)
            return true;  //Do not play cards until three are chosen, unless the Bug card was played

        if(!activePlayer.onCardsPlayed(cardsChosen)) {
            cardsChosen.clear();
            return false;
        }

        int playerPos = board.getPlayerPositions()[turnManager.getActivePlayerNumber()];
        if(board.getTile(playerPos) instanceof Jewel){
            activePlayer.setHasWon();
            view.promptWin(activePlayerNum);
            winOrder.add(turnManager.getActivePlayerNumber());
        }
        if(board.getTile(playerPos) instanceof Portal){
            activePlayer.usePortal();
        }
        //Retrieve updated game state from board

        if(cardChosen == CardType.BUG) //If the bug card was chosen, prompt the player to choose a new move
            view.getNextCard(activePlayerNum);

        //Render new state in GameView
        view.redraw();
        return true;
    }

    private class CardStackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert e.getSource() instanceof CardStack; // This listener should only be used for cardStacks
            CardType cardChosen = ((CardStack) (e.getSource())).getCardType();
            Controller controller = getInstance();
            if(!controller.onCardChosen(cardChosen)){
                System.out.println("Illegal Move");
                view.promptIllegalMove();
                view.getNextCard(turnManager.getActivePlayer().getNumber());
            }if(playThreeEnabled) {
                if(cardsChosen.size() == 3 && cardChosen != CardType.BUG) {
                    view.promptEndTurn();
                }
            }
            else if(cardChosen != CardType.BUG) {
                view.promptEndTurn();
            }
        }
    }

    private class PlayThreeListener implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            Controller.this.playThreeEnabled = !playThreeEnabled;
            System.out.println(playThreeEnabled);
        }
    }

	public void onTurnEnded() {
        cardsChosen.clear();
        turnManager.endTurn();
        view.getNextCard(turnManager.getActivePlayer().getNumber());
	}

	public boolean playThreeEnabled(){
        return playThreeEnabled;
    }

	public void onGameEnded() {
        view.showEndGameScreen(winOrder);
	}
}
