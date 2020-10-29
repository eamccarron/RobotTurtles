package View;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

//import the controller
import Controller.*;

public class GameView{
    TurtleMover controller;
    //Dimensions
    private final Dimension windowSize = new Dimension(1000,1000);
    //GUI Elements
    private JFrame window;
    private CardChooserDialog cardChooserDialog; 
    private BoardPanel boardPanel;

    public GameView(int boardSize,  int[] playerPositions, HashMap<Integer, TileType> boardLayout){
        loadSprites();

        //Initialize window & layout manager
        window = new JFrame("Robot Turtles alpha");
        window.setSize(windowSize);
        window.getContentPane().setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize the Card Chooser Dialog Window
        cardChooserDialog = new CardChooserDialog();
        
        boardPanel = new BoardPanel(boardSize, playerPositions, boardLayout);
        
        window.setLayout(new BorderLayout());
        window.getContentPane().add(boardPanel, BorderLayout.CENTER);

        window.setVisible(true);
        window.setResizable(false);
        cardChooserDialog.setVisible(true);
    }

    private void loadSprites(){
        //TODO load sprites
    }

    //Creates a dialog which prompts the current player to choose a card from a card stack
    public void getNextCard(int playerID) {
        cardChooserDialog.setStatus(String.format("Player %d, pick a card", playerID + 1));
        cardChooserDialog.promptNextCard();
    }

    public void updatePlayerPosition(int playerNumber, int position){
        boardPanel.updatePlayerPosition(playerNumber, position);
    }

    public void updatePlayerDirection(int playerNumber, int direction){
        boardPanel.updatePlayerDirection(playerNumber, direction);
    }

	public void updateTiles(HashMap<Integer, TileType> layout) {
       boardPanel.updateTiles(layout); 
	}

	public void redraw() {
        boardPanel.repaint();
	}

    //The action listener for a card stack.  Generalized to be usable by a CardStack of any type.
}
