package Model;

public class IceWall extends Tile{

    public IceWall() {
        super(false, false);
        this.setTileType(TileType.ICE_WALL);
    }

    public void melt(){
        setVacancy(true);
    }

    @Override
    public void addCrate(){
        if (this.getVacancy()==true)
            super.addCrate();
        else
            System.out.println("ERROR: This ice wall is not melted so this tile cannot hold a crate");
    }
}
