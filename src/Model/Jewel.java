package Model;

public class Jewel extends Tile{

    public Jewel() {
        super(true, false);
    }

    @Override
    public void addCrate() {
        System.out.println("Tiles with jewels cannot hold crates");
    }
}
