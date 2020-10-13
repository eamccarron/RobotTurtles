package Model;
import Controller.CardType;
public class TurtleMaster {
    private CardType[] hand;
    
    //Return an int array where each index corresponds to the number of a certain kind of cards remaining
    //use CardType.ordinal() to index by card type
    public CardType[] getHand(){
       return hand; 
    }

    public TurtleMaster(){

    }
    //Return false if move is illegal or if there are not enough cards remaining.
    public boolean onCardPlayer(CardType card){
        return true;
    }
}
