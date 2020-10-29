package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import Controller.CardType;
import Controller.TurtleMover;

public class CardChooserDialog extends JDialog {
    private CardStack stepForward;
    private CardStack turnLeft;
    private CardStack turnRight;
    private CardStack bug;
    private JButton endTurn;
    private int currentPlayer;
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

        CardStackListener cardStackListener = new CardStackListener();
        stepForward.addActionListener(cardStackListener);
        turnLeft.addActionListener(cardStackListener);
        turnRight.addActionListener(cardStackListener);
        bug.addActionListener(cardStackListener);
        endTurn.addActionListener( e -> TurtleMover.onTurnEnded());
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
        //DEBUG
            File f = new File("../Assets/Player_1.png");
            System.out.println(f.exists());
        //END DEBUG
        cardIcons[CardType.STEP_FORWARD.ordinal()] = new ImageIcon("../Assets/StepForwardCard.png");
        cardIcons[CardType.TURN_LEFT.ordinal()] = new ImageIcon("../Assets/TurnLeftCard.png");
        cardIcons[CardType.TURN_RIGHT.ordinal()] = new ImageIcon("../Assets/TurnRightCard.png");
        cardIcons[CardType.BUG.ordinal()] = new ImageIcon("../Assets/BugCard.png");
    }

    public void setStatus(String text){
        this.setTitle(text);
    }

    private void promptIllegalMove(){
        setStatus("Illegal move.  Try again.");
        promptNextCard();
    }

    private void promptEndTurn(){
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
    private class CardStackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert e.getSource() instanceof CardStack; // This listener should only be used for cardStacks
            CardType cardChosen = ((CardStack) (e.getSource())).getCardType();
            if(!TurtleMover.onCardChosen(cardChosen)){
                promptIllegalMove();
            }
            CardChooserDialog.this.promptEndTurn();
        }
    }


}
