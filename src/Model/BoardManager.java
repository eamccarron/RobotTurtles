package Model;

import java.util.HashMap;

import Controller.TileType;

public interface BoardManager {
    public int[] getPlayerPositions();
    public int[] getPlayerDirections();
    public TileType getTile(int position);
    public HashMap<Integer, TileType> getTileLayout();
}