package Controller;

import java.util.HashMap;
import Model.Tile;

public interface BoardSubscriber {
    void onTilesUpdated(HashMap<Integer, Tile> tiles);
}
