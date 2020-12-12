package View;

import java.awt.*;

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
    private CardStack functionFrog;
    private CardStack laser;

    private JButton endTurn;
    private JButton submitCards;
    private ImageIcon[] cardIcons;
    private JRadioButton playThree;
    private static final String playThreeText = "Play Three";
    private JRadioButton writeProgram;
    private static final String writeProgramText = "Write Program";

    private final Dimension windowSize = new Dimension(1200, 300);

    public CardChooserDialog() {
        super();

        cardIcons = new ImageIcon[CardType.values().length];
        loadIcons();

        stepForward = new CardStack(CardType.STEP_FORWARD, cardIcons[CardType.STEP_FORWARD.ordinal()]);
        turnLeft = new CardStack(CardType.TURN_LEFT, cardIcons[CardType.TURN_LEFT.ordinal()]);
        turnRight = new CardStack(CardType.TURN_RIGHT, cardIcons[CardType.TURN_RIGHT.ordinal()]);
        bug = new CardStack(CardType.BUG, cardIcons[CardType.BUG.ordinal()]);
        functionFrog = new CardStack(CardType.FUNCTION_FROG, cardIcons[CardType.FUNCTION_FROG.ordinal()]);
        laser = new CardStack(CardType.LASER, cardIcons[CardType.LASER.ordinal()]);

        endTurn = new JButton("End Turn");
        submitCards = new JButton("Submit");

        ButtonGroup ruleControlButtons = new ButtonGroup();
        playThree = new JRadioButton(playThreeText);
        playThree.addActionListener(e -> submitCards.setVisible(false));
        ruleControlButtons.add(playThree);
        writeProgram = new JRadioButton(writeProgramText);
        writeProgram.addActionListener(e -> submitCards.setVisible(true));
        ruleControlButtons.add(writeProgram);

        Controller controller = Controller.getInstance();
        //Register CardStacks
        controller.registerCardStack(stepForward);
        controller.registerCardStack(turnLeft);
        controller.registerCardStack(turnRight);
        controller.registerCardStack(bug);
        controller.registerCardStack(functionFrog);
        controller.registerCardStack(laser);

        //Register control buttons
        controller.registerRuleControlButtons(ruleControlButtons, playThreeText, writeProgramText);
        endTurn.addActionListener( e -> controller.onTurnEnded());
        submitCards.addActionListener(e -> controller.submitCards());

        this.setSize(windowSize); 
        this.setLayout(new BoxLayout(this.getContentPane(), BoxLayout.X_AXIS));

        this.getContentPane().add(stepForward);
        this.getContentPane().add(turnLeft);
        this.getContentPane().add(turnRight);
        this.getContentPane().add(bug);
        this.getContentPane().add(functionFrog);
        this.getContentPane().add(laser);

        this.getContentPane().add(endTurn);
        this.getContentPane().add(submitCards);
        this.getContentPane().add(playThree);
        this.getContentPane().add(writeProgram);

        endTurn.setVisible(false);
        bug.setVisible(false);
        submitCards.setVisible(false);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setResizable(true);
    }

    private void loadIcons() {
        cardIcons[CardType.STEP_FORWARD.ordinal()] = new ImageIcon("Assets/StepForwardCard.png");
        cardIcons[CardType.TURN_LEFT.ordinal()] = new ImageIcon("Assets/TurnLeftCard.png");
        cardIcons[CardType.TURN_RIGHT.ordinal()] = new ImageIcon("Assets/TurnRightCard.png");
        cardIcons[CardType.BUG.ordinal()] = new ImageIcon("Assets/BugCard.png");
        cardIcons[CardType.FUNCTION_FROG.ordinal()] = new ImageIcon("Assets/functionFrog.png");
        cardIcons[CardType.LASER.ordinal()] = new ImageIcon("Assets/Laser.png");
    }

    private void clearContents(){
        for(Component c : this.getContentPane().getComponents()){
            c.setVisible(false);
        }
    }

    public void setStatus(String text){
        this.setTitle(text);
    }

    public void promptIllegalMove(){
        setStatus("Illegal move.  Try again.");
    }

    public void promptEndTurn(){
        clearContents();

        setStatus("End turn?");
        endTurn.setVisible(true);
        bug.setVisible(true);
    }

	public void promptNextCard() {
        clearContents();

        if(writeProgram.isSelected())
            submitCards.setVisible(true);
        stepForward.setVisible(true);
        turnLeft.setVisible(true);
        turnRight.setVisible(true);
        functionFrog.setVisible(true);
        playThree.setVisible(true);
        writeProgram.setVisible(true);
        laser.setVisible(true);
	}
	public void promptWin(int number) {
        setStatus(String.format("Player %d has won!", number));
	}


    public void promptFunctionFrog() {
        clearContents();

        stepForward.setVisible(true);
        turnLeft.setVisible(true);
        turnRight.setVisible(true);
        submitCards.setVisible(true);

        setStatus("Choose cards for function frog then click submit to save");
    }
}
