package Controller;

import Model.Turtle;

public interface TurnManager {
    int getNumPlayers();
    int getActivePlayerNumber();
    Player getActivePlayer();
    Player getPlayer(int playerNum);
    void endTurn();
    void addPlayer(Turtle turtle, int playerNumber);
}
