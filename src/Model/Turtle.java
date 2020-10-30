package Model;

public class Turtle {

    int playerID;
    private int currPosition;
    private int prevPosition;
    private final int boardLength = (int)Math.sqrt(Board.BOARD_SIZE);
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private int currDir;
    private int prevDir;
    private Board board;

    public Turtle(int playerID, Board board){
        this.playerID  = playerID;
        this.board = board;
        //Place players clockwise in 4 corners of board facing center
        switch(this.playerID){
            case Board.PLAYER_1:
                currPosition = prevPosition = 0;
                currDir = prevDir = SOUTH;
                break;
            case Board.PLAYER_2:
                currPosition = prevPosition = 7;
                currDir = prevDir = SOUTH;
                break;
            case Board.PLAYER_3:
                currPosition = prevPosition = 63;
                currDir = prevDir = NORTH;
                break;
            case Board.PLAYER_4:
                currPosition = prevPosition = 56;
                currDir = prevDir = NORTH;
        }
    }

    public boolean moveForward(){
        int newPosition;
        switch(currDir){
            case NORTH:
                if (currPosition<boardLength)
                    return false;
                newPosition = currPosition - boardLength;
                if (board.isPositionOccupied(newPosition))
                    return false;
                break;
            case EAST:
                if (currPosition%boardLength == 7)
                    return false;
                newPosition = currPosition  + 1;
                if (board.isPositionOccupied(newPosition))
                    return false;
                break;
            case SOUTH:
                if (currPosition >= Board.BOARD_SIZE-boardLength)
                    return false;
                newPosition = currPosition + boardLength;
                if (board.isPositionOccupied(newPosition))
                    return false;
                break;
            case WEST:
                if (currPosition%boardLength == 0)
                    return false;
                newPosition = currPosition  - 1;
                if (board.isPositionOccupied(newPosition))
                    return false;
                break;
            default:
                newPosition = currPosition;
        }
        prevPosition = currPosition;
        currPosition = newPosition;
        prevDir = currDir; //Make sure that turtle can not revert to a previous rotation by playing a bug card after moving

        System.out.printf("CurrPosition: %d, PrevPosition: %d\n", currPosition, prevPosition);
        board.setOccupied(currPosition);
        board.setUnoccupied(prevPosition);
        return true;
    }

    public void bug(){
        board.setUnoccupied(currPosition);
        board.setOccupied(prevPosition);
        currPosition = prevPosition;
        currDir = prevDir;
        System.out.printf("Bug played: CurrPosition set to %d, currDir set to %d\n", currPosition, currDir);
    }

    public void turn(String leftOrRight){
        int turnDirection = 1;
        if (leftOrRight.equals("left"))
            turnDirection = -1;
        prevDir = currDir;
        currDir = (currDir+turnDirection+4)%4;
    }

    public int getPosition() {
        return currPosition;
    }

    public int getDirection() {
        return this.currDir;
    }

    public void setPrevPosition(int currPosition){
        this.prevPosition = currPosition;
    }

}
