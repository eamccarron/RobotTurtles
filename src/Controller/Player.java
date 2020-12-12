package Controller;

import java.util.Queue;

//An interface for controlling a class capable of playing cards.
//The implementing class should be able to determine if playing a card is a legal move and update its state accordingly,
//and also should be able to enter a winning state.
//Since a Player is also the subject of an observer pattern, it should be able to add subscribers
public interface Player {
   boolean onCardsPlayed(Queue<CardType> cards);
   void addSubscriber(PlayerSubscriber sub);
   int getNumber();
   void setHasWon();
   boolean hasWon();
   void usePortal();
}
