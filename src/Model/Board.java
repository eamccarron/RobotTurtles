package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.BoardSubscriber;
import Controller.BoardManager;

//Subject for BoardSubscribers
//SINGLETON
public class Board implements BoardManager {
    public static final int BOARD_SIZE = 64;
    public static final int BOARD_LENGTH = (int)Math.floor(Math.sqrt(BOARD_SIZE));
    private static final TileType[] initialBoardLayout1 =
            {TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.JEWEL, TileType.JEWEL, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.JEWEL, TileType.JEWEL, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR,
            TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, TileType.REGULAR, };
    private static final int[] initialCrateLocations = {};
    //Variables for tracking game state
    private HashMap<Integer, Tile> layout = new HashMap<>(BOARD_SIZE);
    private Turtle[] turtles = new Turtle[4];
    private ArrayList<BoardSubscriber> subscribers = new ArrayList<>();
    private static Board board;

    public static Board getInstance(){
        if(board == null) {
            board = new Board(TurtleMover.NUM_PLAYERS);
        }
        return board;
    }

    private Board(int numPlayers){
        TileFactory tileFactory = new TileFactory();
        boolean firstPortalInPair = true;
        Portal firstInPair = new Portal();//Just to initialize
        for (int i = 0; i < BOARD_SIZE; i++) {
            TileType type = initialBoardLayout1[i];
            Tile newTile = tileFactory.createTile(type);
            layout.put(i, newTile);
            if (type == TileType.PORTAL){

                ((Portal) newTile).setPosition(i);
                if (firstPortalInPair) {
                    firstPortalInPair = false;
                    firstInPair = (Portal) newTile;
                }
                else{
                    firstPortalInPair = true;
                    firstInPair.setCorrespondingPortal((Portal) newTile);
                    ((Portal) newTile).setCorrespondingPortal(firstInPair);
                }
            }
        }
        for (int location : initialCrateLocations) {
            board.getTile(location).addCrate();
        }

    }

    public void setTurtles(Turtle[] turtles) {
        for (int i = 0; i < TurtleMover.NUM_PLAYERS; i++) {
            this.turtles[i] = turtles[i];
        }
    }

    public Turtle[] getTurtles(){
        return turtles;
    }

    public boolean isPositionOccupied(int position){
        return !board.getTile(position).getVacancy();
    }

    public Tile getTile(int position){
        return layout.get(position);
    }

    public void addSubscriber(BoardSubscriber subscriber){
        this.subscribers.add(subscriber);
    }

	public HashMap<Integer, Tile> getTileLayout() {
        return this.layout;
	}

	public int[] getPlayerPositions() {
        int[] positions = new int[turtles.length];
        for(int i = 0; i < positions.length; i++){
            positions[i] = turtles[i].getPosition();
        }
        return positions;
    }


	public int[] getPlayerDirections() {
        int[] directions = new int[turtles.length];
        for(int i = 0; i < turtles.length; i++)
            directions[i] = turtles[i].getDirection();
        return directions;
	}

    @Override
    public int getBoardSize() {
        return BOARD_SIZE;
    }
}