package Model;
import Controller.CardType;

//Class representing the player in a game.  Responsible for translating a card into either an illegal move or 
//appropriate state change for the turtle it is controlling.
public class TurtleMaster implements CardPlayer{
    private int playerNumber;
    private boolean hasWon;
    Turtle turtle;

    public TurtleMaster(int playerID, Turtle turtle){
        this.turtle = turtle; 
        this.playerNumber = playerID;
    }

    //Return false if move is illegal or if there are not enough cards remaining.
    public boolean onCardPlayed(CardType card){
        switch(card){
            case STEP_FORWARD:
                return turtle.moveForward();
            case BUG:
                turtle.bug();
                break;
            case TURN_LEFT:
                turtle.turn("left");
                break;
            case TURN_RIGHT:
                turtle.turn("right");
                break;
            default:
                break;
        }
        return true;
    }

	public int getNumber() {
		return playerNumber;
	}

	public void setHasWon() {
        hasWon = true;
    }
    public boolean hasWon(){
        return hasWon;
    }
}
