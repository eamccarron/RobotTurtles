package Model;
import Controller.CardType;
public class TurtleMaster {
    private int playerNumber;
    private int position;
    private Direction direction;
    private final int boardLength = (int)Math.sqrt(Board.BOARD_SIZE);
    private CardType[] hand;
    
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
                position = 0;
                direction = Direction.SOUTH;
                break;
            case Board.PLAYER_2:
                position = 7;
                direction = Direction.SOUTH;
                break;
            case Board.PLAYER_3:
                position = 63;
                direction = Direction.NORTH;
                break;
            case Board.PLAYER_4:
                position = 56;
                direction = Direction.NORTH;
        }
    }

    public boolean moveForward(){
        switch(direction){
            case NORTH:
                position -= boardLength;
                break;
            case EAST:
                position += 1;
                break;
            case SOUTH:
                position += boardLength;
                break;
            case WEST:
                position -= 1;
                break;
        }
        //TODO Check if move is out of bounds
        return true;
    }

    //Return false if move is illegal or if there are not enough cards remaining.
    public boolean onCardPlayed(CardType card){
        //TODO Implement rotations
        //TODO update hand
        //TODO Check if move is illegal (enough cards?)
        switch(card){
            case STEP_FORWARD:
                moveForward();
                break;
            case BUG:
                break;
            case LASER:
                break;
            case TURN_LEFT:
                break;
            case TURN_RIGHT:
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
		return position;
    }
}
