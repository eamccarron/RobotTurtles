package Model;

public interface TurnManager {
    public int getActivePlayerNumber();
    public TurtleMaster getActivePlayer();
    public void endTurn();
    public void addPlayer(Turtle turtle, int playerNumber);
}
