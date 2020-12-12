package Model;

public class Portal extends Tile{

    private int position;
    private Portal correspondingPortal;

    public Portal() {
        super(true, false);
        this.setTileType(TileType.PORTAL);
    }

    public int getPosition() {
        return position;
    }

    public Portal getCorrespondingPortal() {
        return correspondingPortal;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setCorrespondingPortal(Portal portal) {
        this.correspondingPortal = portal;
    }
}
