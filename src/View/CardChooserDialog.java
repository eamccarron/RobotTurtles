package View;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import Controller.CardType;
import Controller.TurtleMover;

public class CardChooserDialog extends JDialog {
    private CardStack stepForward;
    private CardStack turnLeft;
    private CardStack turnRight;
    private CardStack bug;
    private JTextField status;
    private JButton endTurn;

    private final Dimension windowSize = new Dimension(400, 200);

    public CardChooserDialog() {
        super();
        // TODO add sprites to card stacks
        stepForward = new CardStack(CardType.STEP_FORWARD, null);
        turnLeft = new CardStack(CardType.TURN_LEFT, null);
        turnRight = new CardStack(CardType.TURN_RIGHT, null);
        bug = new CardStack(CardType.BUG, null);

        endTurn = new JButton("End Turn");
        status = new JTextField();

        CardStackListener cardStackListener = new CardStackListener();
        stepForward.addActionListener(cardStackListener);
        turnLeft.addActionListener(cardStackListener);
        turnRight.addActionListener(cardStackListener);
        bug.addActionListener(cardStackListener);
        endTurn.addActionListener( e -> TurtleMover.onTurnEnded());
        this.setSize(windowSize); // TODO put dimension into constant
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

    private void promptEndTurn(){
        stepForward.setVisible(false); 
        turnLeft.setVisible(false);
        turnRight.setVisible(false);

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
            CardChooserDialog.this.setVisible(false);
            TurtleMover.onCardChosen(cardChosen);
            CardChooserDialog.this.promptEndTurn();
        }
    }


}
