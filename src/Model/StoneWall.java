package Model;

public class StoneWall extends Tile{


    public StoneWall() {
        super(false, false);
        this.setTileType(TileType.STONE_WALL);
    }

    @Override
    public void addCrate() {
        System.out.println("ERROR: Tiles with stone walls cannot have crates");
    }

    @Override
    public void setVacancy(boolean vacancy) {
        System.out.println("ERROR: vacancy of stone walls cannot be altered");
    }
}
