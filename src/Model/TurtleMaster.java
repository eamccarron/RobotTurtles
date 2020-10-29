package Model;
import Controller.CardType;
public class TurtleMaster {
    private int playerNumber;
    Turtle turtle;


    public TurtleMaster(int playerID){
        turtle = new Turtle(playerID);
        this.playerNumber = playerID;
    }

    //Return false if move is illegal or if there are not enough cards remaining.
    public boolean onCardPlayed(CardType card){
        //TODO Implement rotations
        //TODO update hand
        //TODO Check if move is illegal (enough cards?)
        turtle.setPrevPosition(turtle.getPosition());
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

	public int getTurtlePosition() {
		return turtle.getPosition();
    }

	public int getTurtleDirection() {
        return turtle.getDirection();
	}
}
