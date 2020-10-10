package Model;

public class TurtleMaster {

    //Return an int array where each index corresponds to the number of a certain kind of cards remaining
    //use CardType.ordinal() to index by card type
    int[] getHand(){
        return new int[]{-1,-1,-1,-1}; //TEMP
    }

    public TurtleMaster(){

    }
    //Return false if move is illegal or if there are not enough cards remaining.
    public boolean onCardPlayer(CardType card){
        return true;
    }
}
