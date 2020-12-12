package Model;

public class TileFactory {

    public Tile createTile(TileType type) {
        switch (type) {
            case REGULAR:
                return new RegularTile();
            case ICE_WALL:
                return new IceWall();
            case STONE_WALL:
                return new StoneWall();
            case JEWEL:
                return new Jewel();
            case PORTAL:
                return new Portal();
            default:
                System.out.println("Tile type not found");
                return null;
        }
    }
}
