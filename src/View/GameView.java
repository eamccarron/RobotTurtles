package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

//Three necessary imports from model:
import Model.CardType; //CardTypes are needed for the CardStack buttons
import Model.Tile; //In order to draw the positions of tiles, we need a function which takes an argument of Tile[]
import Model.TurtleMover;  //The view and controller need to exchange state information

public class GameView{
    TurtleMover controller;
    //Dimensions
    private final Dimension windowSize = new Dimension(800,600);
    //GUI Elements
    private JFrame window;
    private JPanel boardView;
    private CardStack moveForward;
    private CardStack turnLeft;
    private CardStack turnRight;
    private CardStack bug;
    private JDialog cardChooserDialog; //TODO put cardChooserDialog in its own class to clean up code

    //Sprite arrays which are indexed by respective enumerations and/or constants
    Icon[] playerSprites;
    Icon[] cardStackSprites = new Icon[4];
    Icon[] tileSprites;

    public GameView(TurtleMover controller){
        loadSprites();

        this.controller = controller;
        //Initialize window & layout manager
        window = new JFrame("Robot Turtles alpha");
        window.setSize(windowSize);
        window.getContentPane().setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initialize the four (or more later on) cardStack Buttons with an actionListener
        moveForward = new CardStack(CardType.STEP_FORWARD, cardStackSprites[CardType.STEP_FORWARD.ordinal()]);
        moveForward.addActionListener(new CardStackListener());
        turnLeft = new CardStack(CardType.TURN_LEFT, cardStackSprites[CardType.STEP_FORWARD.ordinal()]);
        turnLeft.addActionListener(new CardStackListener());
        turnRight  = new CardStack(CardType.TURN_RIGHT, cardStackSprites[CardType.STEP_FORWARD.ordinal()]);
        turnRight.addActionListener(new CardStackListener());
        bug = new CardStack(CardType.BUG, cardStackSprites[CardType.STEP_FORWARD.ordinal()]);
        bug.addActionListener(new CardStackListener());

        //Initialize the Card Chooser Dialog Window
        cardChooserDialog = new JDialog();
        cardChooserDialog.setSize(400,200); //TODO put dimension into constant
        cardChooserDialog.setLayout(new FlowLayout());
        cardChooserDialog.add(moveForward);
        cardChooserDialog.add(turnLeft);
        cardChooserDialog.add(turnRight);
        cardChooserDialog.add(bug);
        cardChooserDialog.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        window.setVisible(true);
        cardChooserDialog.setVisible(true);
    }

    private void loadSprites(){
        cardStackSprites[CardType.STEP_FORWARD.ordinal()] = new ImageIcon("H:/Java/Assets/BugCard.png");
    }

    //Creates a dialog which prompts the current player to choose a card from a card stack
    public void getNextCard(int player, int[] cardCount) {
        cardChooserDialog.setVisible(true);
    }


    //Called by the controller once all states have been appropriately updated at the end of a turn.
    public void updateBoard(int[] playerLocations, Tile[] board){

    }

    //The action listener for a card stack.  Generalized to be usable by a CardStack of any type.
    private class CardStackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert e.getSource() instanceof CardStack;  //This listener should only be used for cardStacks
            CardType cardChosen = ((CardStack)(e.getSource())).getCardType();
            cardChooserDialog.setVisible(false);
            controller.onCardChosen(cardChosen);
        }
    }
}
