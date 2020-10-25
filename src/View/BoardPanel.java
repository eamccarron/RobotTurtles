package View;

import Controller.TileType;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.*;


public class BoardPanel extends JPanel{
    private int boardSize; //The number of tiles on the board
    private int boardLength; //Assume square board so length = with
    private int[] playerPositions;
    private HashMap<Integer, TileType> tileLayout;
    private BufferedImage[] playerSprites;
    private final BufferedImage[] tileSprites;

    public BoardPanel(int boardSize, int[] playerPositions, HashMap<Integer, TileType> tileLayout){
        super();
        this.playerPositions = playerPositions;
        this.tileLayout = tileLayout;
        this.boardSize = boardSize;
        boardLength = (int)Math.floor(Math.sqrt(boardSize)); 
            
        playerSprites = new BufferedImage[4];
        tileSprites = new BufferedImage[TileType.values().length];
        System.out.println(System.getProperty("user.dir"));
        try{
        loadSprites();
        } catch(IOException iOe){
            iOe.printStackTrace();
        }
    }

    private void loadSprites() throws IOException{
        playerSprites[0] = ImageIO.read(new File("../../Assests/Player_1.bmp"));
        playerSprites[1] = ImageIO.read(new File("../../Assests/Player_2.bmp"));
        playerSprites[2] = ImageIO.read(new File("../../Assests/Player_3.bmp"));
        playerSprites[3] = ImageIO.read(new File("../../Assests/Player_4.bmp"));
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

    private BufferedImage scaleSprite(BufferedImage sprite, int width, int height){
        //TODO scale sprites
       return null; 
    }

    public void updateTiles(HashMap<Integer, TileType> tileLayout){
        //TODO render tile sprites
    }

	public void updatePlayerPosition(int playerNum, int position) {
        playerPositions[playerNum] = position;
	}

	public void updatePlayerDirection(int playerNumber, int direction) {
	}
}