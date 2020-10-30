package View;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

//import the controller
import Controller.*;

//The main window for the game.  
//Responsible for communicating updates from the controller to its components, which then update to display the state of the gameObjects. 
public class GameView{
    Controller controller;
    //Dimensions
    private final Dimension windowSize = new Dimension(1000,1000);
    //GUI Elements
    private JFrame window;
    private CardChooserDialog cardChooserDialog; 
    private BoardPanel boardPanel;

    //Initializes a new game view based on the game parameters which are determined by the Controller.
    public GameView(int boardSize,  int[] playerPositions, int[] playerDirections, HashMap<Integer, TileType> boardLayout){
        loadSprites();

        //Initialize window & layout manager
        window = new JFrame("Robot Turtles alpha");
        window.setSize(windowSize);
        window.getContentPane().setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize the Card Chooser Dialog Window
        cardChooserDialog = new CardChooserDialog();
        
        boardPanel = new BoardPanel(boardSize, playerPositions, playerDirections, boardLayout);
        
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

	public void promptWin(int number) {
        cardChooserDialog.promptWin(number);
	}

	public void showEndGameScreen(ArrayList<Integer> winOrder) {
        //Attempted to create and endgame message and replay capability but I couldn't figure out why it doesn't render.  Will fix for next Milestone
        System.out.println("Rendering endgame");
        window.removeAll(); 
        cardChooserDialog.setVisible(false);

        JTextField endGameStatus = new JTextField();
        endGameStatus.setEditable(false);
        endGameStatus.setFont(new Font("Monospace", 0, 24));
        String sWinOrder = String.format("First: %d \n Second: %d \n Third: %d \n Fourth: %d", winOrder.get(0), winOrder.get(1), winOrder.get(2), winOrder.get(3));
        endGameStatus.setText("Well Played!  The results are: \n" + sWinOrder);
        JButton playAgain = new JButton("Play Again?");
        playAgain.setVisible(true);

        playAgain.addActionListener(e -> Controller.initGame(4));

        window.getContentPane().add(endGameStatus, BorderLayout.CENTER);
        window.getContentPane().add(playAgain, BorderLayout.SOUTH);
	}
}
