package Controller;

import java.util.HashMap;

public interface BoardManager {
    int[] getPlayerPositions();
    int[] getPlayerDirections();
    int getBoardSize();
    TileType getTile(int position);
    HashMap<Integer, TileType> getTileLayout();
}