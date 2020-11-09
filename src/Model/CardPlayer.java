package Model;
import Controller.CardType;

//An interface for controlling a class capable of playing cards.
//The implementing class should be able to determine if playing a card is a legal move and update its state accordingly,
//and also should be able to enter a winning state.
public interface CardPlayer {
   public boolean onCardPlayed(CardType card); 
   public void setHasWon();
}
