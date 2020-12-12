package Controller;

import java.util.HashMap;

public interface BoardSubscriber {
    void onTilesUpdated(HashMap<Integer, TileType> tiles);
}
