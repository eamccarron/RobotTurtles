package Model;

import Controller.CardType;
import Controller.Player;
import Controller.PlayerSubscriber;

import java.util.ArrayList;
import java.util.Queue;

//Subject for TurtleSubscriber
public class Turtle implements Player {
    private int playerID;
    private int currPosition;
    private int prevPosition;
    private boolean hasWon = false;
    private final int boardLength = (int)Math.sqrt(Board.BOARD_SIZE);
    private static final int NORTH = 0;
    private static final int EAST = 1;
    private static final int SOUTH = 2;
    private static final int WEST = 3;
    private int currDir;
    private int prevDir;
    private ArrayList<PlayerSubscriber> subscribers = new ArrayList<>();

    public Turtle(int playerID){
        this.playerID  = playerID;
        //Place players clockwise in 4 corners of board facing center
        switch (this.playerID) {
            case TurtleMover.PLAYER_1 -> {
                currPosition = prevPosition = 0;
                currDir = prevDir = SOUTH;
            }
            case TurtleMover.PLAYER_2 -> {
                currPosition = prevPosition = 7;
                currDir = prevDir = SOUTH;
            }
            case TurtleMover.PLAYER_3 -> {
                currPosition = prevPosition = 63;
                currDir = prevDir = NORTH;
            }
            case TurtleMover.PLAYER_4 -> {
                currPosition = prevPosition = 56;
                currDir = prevDir = NORTH;
            }
        }
    }

    private void notifySubscribersOfRotation(){
        for(PlayerSubscriber sub : subscribers){
            sub.onPlayerRotated(this.playerID, this.currDir);
        }
    }

    private void notifySubscribersOfMovement(){
        for(PlayerSubscriber sub : subscribers){
            sub.onPlayerMoved(this.playerID, this.currPosition);
        }
    }

    public boolean moveForward(){
        int newPosition;
        switch(currDir){
            case NORTH:
                if (currPosition<boardLength)
                    return false;
                newPosition = currPosition - boardLength;
                break;
            case EAST:
                if (currPosition%boardLength == 7)
                    return false;
                newPosition = currPosition  + 1;
                break;
            case SOUTH:
                if (currPosition >= Board.BOARD_SIZE-boardLength)
                    return false;
                newPosition = currPosition + boardLength;
                break;
            case WEST:
                if (currPosition%boardLength == 0)
                    return false;
                newPosition = currPosition  - 1;
                break;
            default:
                newPosition = currPosition;
        }
        if (Board.getInstance().isPositionOccupied(newPosition)) {
            System.out.println("Position Occupied");
            return false;
        }
        currPosition = newPosition;
        notifySubscribersOfMovement();
        System.out.printf("CurrPosition: %d, PrevPosition: %d\n", currPosition, prevPosition);
        return true;
    }

    public void bug(){
        Board.getInstance().setUnoccupied(currPosition);
        Board.getInstance().setOccupied(prevPosition);
        System.out.printf("Bug played, setting currDir to %d and currPos to %d\n", prevDir, prevPosition);
        currDir = prevDir;
        currPosition = prevPosition;
        notifySubscribersOfRotation();
        notifySubscribersOfMovement();
    }

    private void storePrevState(){
        prevDir = currDir;
        prevPosition = currPosition;
    }

    public void turn(String leftOrRight){
        int turnDirection = 1;
        if (leftOrRight.equals("left"))
            turnDirection = -1;
        currDir = (currDir+turnDirection+4)%4;
        notifySubscribersOfRotation();
    }

    public int getPosition() {
        return currPosition;
    }

    public int getDirection() {
        return this.currDir;
    }

    @Override
    public void addSubscriber(PlayerSubscriber sub){
        this.subscribers.add(sub);
    }

    public void setPosition(int pos) {
        this.storePrevState();
        this.currPosition = pos;
    }

    @Override
    public boolean onCardsPlayed(Queue<CardType> cards) {
        if(cards.peek() != CardType.BUG)
            storePrevState();
        boolean legalMove = true;
        for(CardType card : cards) {
            System.out.println(card);
            switch (card) {
                case STEP_FORWARD:
                    legalMove = legalMove && this.moveForward();
                    break;
                case BUG:
                    this.bug();
                    break;
                case TURN_LEFT:
                    this.turn("left");
                    break;
                case TURN_RIGHT:
                    this.turn("right");
                    break;
                default:
                    break;
            }
        }
        if(!legalMove){ //If this set of moves was illegal, revert to previous state and return
            this.bug();
            return false;
        }
        Board.getInstance().setOccupied(currPosition);
        Board.getInstance().setUnoccupied(prevPosition);
        return true;
    }

    @Override
    public int getNumber() {
       return playerID;
    }

    @Override
    public void setHasWon() {
        hasWon = true;
    }

    @Override
    public boolean hasWon(){
        return hasWon;
    }
}
