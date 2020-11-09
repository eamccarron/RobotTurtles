package View;

import Controller.CardType;
import Controller.Controller;

import javax.swing.*;

public class CardStack extends JButton {
    private CardType cardType;

    public CardStack(CardType type, Icon icon){
        this.cardType = type;
        super.setIcon(icon);
    }

    public CardType getCardType(){
        return cardType;
    }

}
