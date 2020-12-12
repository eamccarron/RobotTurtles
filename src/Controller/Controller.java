package Controller;

import Model.*;
import View.GameView;
import View.CardStack;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.*;

//The main controller for the game, responsible for main game loop and determining win conditions
//SINGLETON
public class Controller {
    private GameView view;
    private BoardManager board;
    private TurnManager turnManager;
    private static ArrayList<Integer> winOrder;
    private static Controller instance;

    private boolean playThreeEnabled = false;
    private boolean writeProgramEnabled = false;
    private boolean[] functionStored; //Tracks if each player has a function stored for the function frog
    private boolean functionFrogActive = false;

    private Queue<CardType> cardsChosen;

    private Controller(){
    }


    public static Controller getInstance(){
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    //Initializes a new game by instantiating a board and a view.
    public void initGame(BoardManager board, TurnManager turnManager){
        HashMap<Integer, Tile> layout = board.getTileLayout();
        winOrder = new ArrayList<>(turnManager.getNumPlayers());
        cardsChosen = new LinkedList<>();
        int[] playerPositions = board.getPlayerPositions();
        this.board = board;
        this.turnManager = turnManager;
        this.functionStored = new boolean[turnManager.getNumPlayers()];
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
    public void registerRuleControlButtons(ButtonGroup ruleControlButtons, String play3, String writeProgram){
        Iterator<AbstractButton> i = ruleControlButtons.getElements().asIterator();
        while(i.hasNext()){
            i.next().addItemListener(new RuleControlListener(play3, writeProgram));
        }
    }
    //The handler function for when a card is chosen.  This is called whenever a CardStack is clicked.
    private boolean onCardChosen(CardType cardChosen) {
        //I don't think we need this anymore but I'll leave it commented just in case:
            /*if(cardsChosen.peek() == CardType.BUG) //If bug is the most recent card chosen remove it from queue
                cardsChosen.poll(); */


        Player activePlayer = turnManager.getActivePlayer();
        int activePlayerNum = turnManager.getActivePlayerNumber();

        //If a Bug is chosen, it is the only card that needs to be sent to the player
        if(cardChosen == CardType.BUG){
            cardsChosen.clear();
            Queue<CardType> bug = new LinkedList<>();
            bug.add(cardChosen);
            activePlayer.onCardsPlayed(bug);
            view.redraw();
            view.getNextCard(activePlayerNum);
            return true;
        }
        if(cardChosen == CardType.FUNCTION_FROG && !functionStored[activePlayer.getNumber()]) {
            functionFrogActive = true;
            System.out.println("Function Frog activated");
        }

        cardsChosen.offer(cardChosen);
        System.out.println(cardsChosen);
        //in any of these cases, the cards should not be played yet:
        if((playThreeEnabled && cardsChosen.size() < 3) //All cards have not been chosen yet
            || functionFrogActive //The function has not been finished yet
            || writeProgramEnabled){//Do not submit cards until program is finished
            System.out.println("Holding Cards");
            return true;
        }

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

        //Render new state in GameView (state in the view is automatically updated via the observer pattern)
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
            }
            //Determine if endTurn should be prompted
            switch(cardChosen){
                case BUG:
                    return; //Do not prompt endTurn for a bug card
                case FUNCTION_FROG:
                    if(!functionStored[turnManager.getActivePlayer().getNumber()])
                        view.promptFunctionFrog();
                    else
                        view.promptEndTurn();
                    break;
                default:
                    if(!writeProgramEnabled && !functionFrogActive) { //If write program enabled, do not promptEndTurn;
                        if (playThreeEnabled && cardsChosen.size() == 3)
                            view.promptEndTurn();
                        else if (!playThreeEnabled)
                            view.promptEndTurn();
                    }
            }
        }
    }

    private class RuleControlListener implements ItemListener {
        private final String play3;
        private final String writeProgram;

        public RuleControlListener(String play3, String writeProgram){
            this.play3 = play3;
            this.writeProgram = writeProgram;
        }

        @Override
        public void itemStateChanged(ItemEvent itemEvent) {
            String rule =  ((AbstractButton)(itemEvent.getItem())).getText();
            if(rule.equals(play3)) {
                playThreeEnabled = true;
                writeProgramEnabled = false;
            } else if (rule.equals(writeProgram)){
                playThreeEnabled = false;
                writeProgramEnabled = true;
            }
        }
    }

	public void onTurnEnded() {
        cardsChosen.clear();
        turnManager.endTurn();
        view.getNextCard(turnManager.getActivePlayer().getNumber());
	}

    public void submitCards(){
        if(functionFrogActive) {
            functionFrogActive = false;
            functionStored[turnManager.getActivePlayer().getNumber()] = true;
        }
        turnManager.getActivePlayer().onCardsPlayed(cardsChosen);
        cardsChosen.clear();
        view.redraw();
        view.promptEndTurn();
    }

	public void onGameEnded() {
        view.showEndGameScreen(winOrder);
	}
}
