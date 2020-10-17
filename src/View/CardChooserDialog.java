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
    private final Dimension windowSize = new Dimension(400, 200);

    public CardChooserDialog() {
        super();
        // TODO add sprites to card stacks
        stepForward = new CardStack(CardType.STEP_FORWARD, null);
        turnLeft = new CardStack(CardType.TURN_LEFT, null);
        turnRight = new CardStack(CardType.TURN_RIGHT, null);
        bug = new CardStack(CardType.BUG, null);

        CardStackListener cardStackListener = new CardStackListener();
        stepForward.addActionListener(cardStackListener);
        turnLeft.addActionListener(cardStackListener);
        turnRight.addActionListener(cardStackListener);
        bug.addActionListener(cardStackListener);

        this.setSize(windowSize); // TODO put dimension into constant
        this.setLayout(new FlowLayout());
        this.add(stepForward);
        this.add(turnLeft);
        this.add(turnRight);
        this.add(bug);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    }

    private class CardStackListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            assert e.getSource() instanceof CardStack; // This listener should only be used for cardStacks
            CardType cardChosen = ((CardStack) (e.getSource())).getCardType();
            CardChooserDialog.this.setVisible(false);
            TurtleMover.onCardChosen(cardChosen);
        }
    }
}
