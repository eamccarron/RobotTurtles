package View;

import Model.CardType;

import javax.swing.*;

public class CardStack extends JButton {
    private CardType cardType;

    public CardStack(CardType type, Icon sprite){
        this.cardType = type;
        super.setText(type.toString());
    }

    public CardType getCardType(){
        return cardType;
    }

}
