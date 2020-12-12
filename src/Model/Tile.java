package Model;

public abstract class Tile {

    private boolean vacancy;
    private boolean crateState;
    protected TileType tileType;

    public Tile(boolean vacancy, boolean crateState){
        this.setVacancy(vacancy);
        if (crateState)
            this.addCrate();
        else this.removeCrate();
    }

    public boolean getVacancy(){
        return vacancy;
    }

    public void setVacancy(boolean vacancy){
        this.vacancy = vacancy;
    }

    public boolean hasCrate(){
        return crateState;
    }

    public void addCrate() {
        this.crateState = true;
        this.setVacancy(false);
    }

    public void removeCrate() {
        this.crateState = false;
        this.setVacancy(true);
    }

    public TileType getTileType() {
        return tileType;
    }

    public void setTileType(TileType type){
        this.tileType = type;
    }

    public void getHitByLaser(){};
}
