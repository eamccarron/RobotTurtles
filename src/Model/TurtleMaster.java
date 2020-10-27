package Model;
import Controller.CardType;
public class TurtleMaster {
    private int playerNumber;
    private int currPosition;
    private int prevPosition;
    //private Direction direction;
    private final int boardLength = (int)Math.sqrt(Board.BOARD_SIZE);
    private CardType[] hand;
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private int direction;


    private enum Direction{
        NORTH,
        EAST,
        SOUTH,
        WEST
    }

    //Return an int array where each index corresponds to the number of a certain kind of cards remaining
    //use CardType.ordinal() to index by card type
    public CardType[] getHand(){
       return hand; 
    }

    public TurtleMaster(int playerNumber){
        this.playerNumber  = playerNumber;
        //Place players clockwise in 4 corners of board
        switch(this.playerNumber){
            case Board.PLAYER_1:
                currPosition = 0;
                direction = SOUTH;
                break;
            case Board.PLAYER_2:
                currPosition = 7;
                direction = SOUTH;
                break;
            case Board.PLAYER_3:
                currPosition = 63;
                direction = NORTH;
                break;
            case Board.PLAYER_4:
                currPosition = 56;
                direction = NORTH;
        }
    }

    public boolean moveForward(){
        switch(direction){
            case NORTH:
                if (currPosition<boardLength)
                    return false;
                currPosition -= boardLength;
                break;
            case EAST:
                if (currPosition%boardLength == 7)
                    return false;
                currPosition += 1;
                break;
            case SOUTH:
                if (currPosition >= Board.BOARD_SIZE-boardLength)
                    return false;
                currPosition += boardLength;
                break;
            case WEST:
                if (currPosition%boardLength == 0)
                    return false;
                currPosition -= 1;
                break;
        }
        return true;
    }

    public void turn(String leftOrRight){
        int turnDirection = 1;
        if (leftOrRight.equals("left"))
            turnDirection = -1;
        direction = (direction+turnDirection)%4;
    }

    public void bug(){
        currPosition = prevPosition;
    }

    //Return false if move is illegal or if there are not enough cards remaining.
    public boolean onCardPlayed(CardType card){
        //TODO Implement rotations
        //TODO update hand
        //TODO Check if move is illegal (enough cards?)
        prevPosition = currPosition ;
        switch(card){
            case STEP_FORWARD:
                return moveForward();
            case BUG:
                break;
            case TURN_LEFT:
                turn("left");
                break;
            case TURN_RIGHT:
                turn("right");
                break;
            default:
                break;
        }
        return true;
    }

	public int getNumber() {
		return playerNumber;
	}

	public int getPosition() {
		return currPosition;
    }

	public int getDirection() {
        return this.direction;
	}
}
