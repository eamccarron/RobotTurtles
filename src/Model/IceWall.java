package Model;

public class IceWall extends Tile{

    private boolean isFrozen;

    public IceWall() {
        super(false, false);
        this.setTileType(TileType.ICE_WALL);
        this.setIsFrozen(true);
    }

    public void getHitByLaser(){
        if (isFrozen) {
            setVacancy(true);
            setIsFrozen(false);
        }
    }

    @Override
    public void addCrate(){
        if (this.getVacancy()==true)
            super.addCrate();
        else
            System.out.println("ERROR: This ice wall is not melted so this tile cannot hold a crate");
    }

    public void setIsFrozen(boolean isFrozen) {
        this.isFrozen = isFrozen;
    }

    public boolean getIsFrozen(){
        return isFrozen;
    }
}
