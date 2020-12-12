package Model;

import java.util.ArrayList;
import java.util.HashMap;

import Controller.Controller;

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
    //Variables for tracking game state
    private boolean[] occupiedPositions = new boolean[BOARD_SIZE];
    private HashMap<Integer, Tile> layout = new HashMap<>(BOARD_SIZE);
    private Turtle[] turtles = new Turtle[4];
    private ArrayList<BoardSubscriber> subscribers = new ArrayList<>();
    private static Board instance;

    public static Board getInstance(){
        if(instance == null)
            instance = new Board(TurtleMover.NUM_PLAYERS);
        return instance;
    }

    public Board(int numPlayers){
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
        for(int i = 0; i < numPlayers; i++){
            turtles[i] = new Turtle(i, this);
        }

    }

    public static void main(String[] args){
        TurtleMover turtleMover = new TurtleMover();
        Board board = getInstance();
        Turtle[] turtles = board.getTurtles();
        for(int i = 0; i < TurtleMover.NUM_PLAYERS; i++)
            turtleMover.addPlayer(turtles[i], i);
        Controller controller = Controller.getInstance();
        controller.initGame(board, turtleMover);
    }

    public Turtle[] getTurtles(){
        return turtles;
    }

    public boolean isPositionOccupied(int position){
        return occupiedPositions[position];
    }

    public void setOccupied(int position){
        occupiedPositions[position] = true;
    }

    public void setUnoccupied(int position){
        occupiedPositions[position] = false;
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