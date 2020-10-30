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
    private int direction;
    private Board board;

    public Turtle(int playerID, Board board){
        this.playerID  = playerID;
        //Place players clockwise in 4 corners of board
        switch(this.playerID){
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
        int newPosition;
        switch(direction){
            case NORTH:
                if (currPosition<boardLength)
                    return false;
                newPosition = currPosition - boardLength;
                if (board.isPositionOccupied(newPosition))
                    return false;
                prevPosition = currPosition;
                currPosition = newPosition;
                break;
            case EAST:
                if (currPosition%boardLength == 7)
                    return false;
                newPosition = currPosition  + 1;
                if (board.isPositionOccupied(newPosition))
                    return false;
                prevPosition = currPosition;
                currPosition = newPosition;
                break;
            case SOUTH:
                if (currPosition >= Board.BOARD_SIZE-boardLength)
                    return false;
                currPosition += boardLength;
                newPosition = currPosition + boardLength;
                if (board.isPositionOccupied(newPosition))
                    return false;
                prevPosition = currPosition;
                currPosition = newPosition;
                break;
            case WEST:
                if (currPosition%boardLength == 0)
                    return false;
                newPosition = currPosition  - 1;
                if (board.isPositionOccupied(newPosition))
                    return false;
                prevPosition = currPosition;
                currPosition = newPosition;
                break;
        }
        return true;
    }

    public void bug(){
        currPosition = prevPosition;
    }

    public void turn(String leftOrRight){
        int turnDirection = 1;
        if (leftOrRight.equals("left"))
            turnDirection = -1;
        System.out.println(direction);
        System.out.println(turnDirection);
        direction = (direction+turnDirection+4)%4;
        System.out.println("direction = " + direction);
    }

    public int getPosition() {
        return currPosition;
    }

    public int getDirection() {
        return this.direction;
    }

    public void setPrevPosition(int currPosition){
        this.prevPosition = currPosition;
    }

}
