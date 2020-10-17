package View;

import Controller.CardType;
import Controller.TileType;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import javax.swing.*;


public class BoardPanel extends JPanel{
    private int boardSize; //The number of tiles on the board
    private int boardLength; //Assume square board so length = with
    private int[] playerPositions;
    private HashMap<Integer, TileType> tileLayout;

    private final BufferedImage[] playerSprites;
    private final BufferedImage[] tileSprites;

    public BoardPanel(int boardSize, int[] playerPositions, HashMap<Integer, TileType> tileLayout){
        super();
        this.playerPositions = playerPositions;
        this.tileLayout = tileLayout;
        this.boardSize = boardSize;
        boardLength = (int)Math.floor(Math.sqrt(boardSize)); 
        playerSprites = new BufferedImage[4];
        tileSprites = new BufferedImage[TileType.values().length];
        
    }

    private void loadSprites(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = this.getWidth();
        int height = this.getHeight();
        int cellWidth = width/boardLength;
        int cellHeight = height/boardLength;

        Graphics2D g2d = (Graphics2D)g;
        //Draw equally spaced vertical lines 
        g2d.setColor(Color.BLACK); 
        for(int i = 0; i < boardLength; i++){
            g2d.drawLine(cellWidth * i, 0, cellWidth * i, height);
        }
        //draw equally spaced horizontal lines
        for(int i = 0; i < boardLength; i++){
            g2d.drawLine(0, cellHeight * i, width, cellHeight * i);
        }
        
        //Draw players
        g2d.setFont(new Font("Monospaced", Font.PLAIN, 18));
        for(int i = 0; i < playerPositions.length; i++){
            int pos = playerPositions[i];
            if(pos == -1)
                continue;

            int playerX = (pos % boardLength) * cellWidth + cellWidth / 2;
            int playerY = (pos / boardLength) * cellHeight + cellHeight / 2;
            g2d.drawString(Integer.toString(i + 1), playerX, playerY);
            System.out.println("Player" + Integer.toString(i) + " drawn at " + Integer.toString(pos));
        }
    } 

    public void updateTiles(HashMap<Integer, TileType> tileLayout){
        //TODO render tile sprites
    }

	public void updatePlayerPosition(int playerNum, int position) {
        playerPositions[playerNum] = position;
	}
}