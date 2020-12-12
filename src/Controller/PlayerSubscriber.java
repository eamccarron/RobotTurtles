package Controller;

import java.util.HashMap;

public interface PlayerSubscriber {
    void onPlayerMoved(int playerNum, int pos);
    void onPlayerRotated(int playerNum, int dir);
}
