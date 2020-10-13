package Controller;

import View.GameView;
import Model.*;
import java.util.LinkedList;

//The controller for the game, responsible for main game loop and tracking turns
public class TurtleMover {
    //Constants to hold values for tracking state of current turn, also convenient for indexing players array.
    //These might not be necessary but keep them for now
    public static final int PLAYER_1 = 0;
    public static final int PLAYER_2 = 1;
    public static final int PLAYER_3 = 2;
    public static final int PLAYER_4 = 3;

    private static final int BOARD_SIZE = 64;

    //Variables for tracking game state
    private int currentTurn;
    private TurtleMaster[] players = new TurtleMaster[4];
    private LinkedList<Tile> tiles = new LinkedList<>();
    private GameView view;

    private TurtleMover(){
        currentTurn = PLAYER_1;
        view = new GameView(this);
        for(int i = 0; i < players.length; i++)
            players[i] = new TurtleMaster();

    }

    private void initGame(){
        view.getNextCard(currentTurn, players[PLAYER_1].getHand());
    }

    public static void main(String[] args) {
        TurtleMover controller = new TurtleMover();
        //Start event driven game loop
        controller.initGame();
    }

    public void onCardChosen(CardType cardChosen) {
        System.out.println(cardChosen.toString());

        //Update player state
        if(!players[currentTurn].onCardPlayer(cardChosen)){
            //view.onInvalidMove();  TODO
        }

        //Redraw board
        //view.updateBoard(); TODO

        view.getNextCard(currentTurn, players[currentTurn].getHand());
    }
}
