package Model;

import java.util.HashMap;

public interface BoardManager {
    public int[] getPlayerPositions();
    public int[] getPlayerDirections();
    public int getBoardSize();
    public Tile getTile(int position);
    public HashMap<Integer, Tile> getTileLayout();
}