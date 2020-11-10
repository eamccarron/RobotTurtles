package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import java.io.IOException;

import Controller.CardType;
import Controller.Controller;

//Responsible for displaying the options a player has during any point in their turn.
public class CardChooserDialog extends JDialog {
    private CardStack stepForward;
    private CardStack turnLeft;
    private CardStack turnRight;
    private CardStack bug;
    private JButton endTurn;
    private ImageIcon[] cardIcons;

    private final Dimension windowSize = new Dimension(600, 300);

    public CardChooserDialog() {
        super();
        this.setResizable(false);

        cardIcons = new ImageIcon[CardType.values().length];
        try { 
            loadIcons();
        } catch (IOException io) {
            io.printStackTrace();
        }

        stepForward = new CardStack(CardType.STEP_FORWARD, cardIcons[CardType.STEP_FORWARD.ordinal()]);
        turnLeft = new CardStack(CardType.TURN_LEFT, cardIcons[CardType.TURN_LEFT.ordinal()]);
        turnRight = new CardStack(CardType.TURN_RIGHT, cardIcons[CardType.TURN_RIGHT.ordinal()]);
        bug = new CardStack(CardType.BUG, cardIcons[CardType.BUG.ordinal()]);

        endTurn = new JButton("End Turn");

        Controller.registerCardStack(stepForward);
        Controller.registerCardStack(turnLeft);
        Controller.registerCardStack(turnRight);
        Controller.registerCardStack(bug);
        endTurn.addActionListener( e -> Controller.onTurnEnded());
        this.setSize(windowSize); 
        this.setLayout(new FlowLayout());
        this.add(stepForward);
        this.add(turnLeft);
        this.add(turnRight);
        this.add(bug);
        this.add(endTurn);

        endTurn.setVisible(false);
        bug.setVisible(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private void loadIcons() throws IOException{
        cardIcons[CardType.STEP_FORWARD.ordinal()] = new ImageIcon("Assets/StepForwardCard.png");
        cardIcons[CardType.TURN_LEFT.ordinal()] = new ImageIcon("Assets/TurnLeftCard.png");
        cardIcons[CardType.TURN_RIGHT.ordinal()] = new ImageIcon("Assets/TurnRightCard.png");
        cardIcons[CardType.BUG.ordinal()] = new ImageIcon("Assets/BugCard.png");
    }

    public void setStatus(String text){
        this.setTitle(text);
    }

    public void promptIllegalMove(){
        setStatus("Illegal move.  Try again.");
    }

    public void promptEndTurn(){
        stepForward.setVisible(false); 
        turnLeft.setVisible(false);
        turnRight.setVisible(false);
        setStatus("End turn?");
        endTurn.setVisible(true);
        bug.setVisible(true);
    }

	public void promptNextCard() {
        stepForward.setVisible(true);
        turnLeft.setVisible(true);
        turnRight.setVisible(true);

        endTurn.setVisible(false);
        bug.setVisible(false);
	}
	public void promptWin(int number) {
        setStatus(String.format("Player %d has won!", number));
	}


}
