package Controller;

import java.util.HashMap;
import Model.Tile;

public interface BoardManager {
    int[] getPlayerPositions();
    int[] getPlayerDirections();
    int getBoardSize();
    Tile getTile(int position);
    HashMap<Integer, Tile> getTileLayout();
}