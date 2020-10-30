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
    private int[] playerDirections;
    private HashMap<Integer, TileType> tileLayout;
    private BufferedImage[] playerSprites;
    private BufferedImage[] tileSprites;

    public BoardPanel(int boardSize, int[] playerPositions,int[] playerDirections, HashMap<Integer, TileType> tileLayout){
        super();
        this.playerPositions = playerPositions;
        this.playerDirections = new int[4];
        this.tileLayout = tileLayout;
        this.boardSize = boardSize;
        boardLength = (int)Math.floor(Math.sqrt(boardSize)); 
            
        playerSprites = new BufferedImage[4];
        tileSprites = new BufferedImage[TileType.values().length];
        try{
        loadSprites();
        } catch(IOException iOe){
            iOe.printStackTrace();
        }
    }

    private void loadSprites() throws IOException{
        playerSprites[0] = ImageIO.read(new File("Assets/Player_1.jpg"));
        playerSprites[1] = ImageIO.read(new File("Assets/Player_2.jpg"));
        playerSprites[2] = ImageIO.read(new File("Assets/Player_3.jpg"));
        playerSprites[3] = ImageIO.read(new File("Assets/Player_4.jpg"));
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
            
            int playerX = (pos % boardLength) * cellWidth;
            int playerY = (pos / boardLength) * cellHeight;
            g2d.drawImage(playerSprites[i], playerX, playerY, null);
        }
    }

    private BufferedImage rotatePlayerSprite(BufferedImage sprite, int prevDir, int newDir){
       int nRot = newDir == 0 ? 4 - prevDir : newDir - prevDir;
       int width = sprite.getWidth(); 
       int height = sprite.getHeight();

       BufferedImage newSprite = new BufferedImage(height, width, sprite.getType());

       Graphics2D graphics2D = newSprite.createGraphics();
       graphics2D.translate((height - width) / 2, (height - width) / 2);
       graphics2D.rotate((Math.PI / 2) * nRot, height / 2, width / 2);
       graphics2D.drawRenderedImage(sprite, null);
       
       return newSprite; 
    }
    
    public void updateTiles(HashMap<Integer, TileType> tileLayout){
        //TODO render tile sprites
    }

	public void updatePlayerPosition(int playerNum, int position) {
        playerPositions[playerNum] = position;
	}

	public void updatePlayerDirection(int playerNumber, int direction) {
        if(playerDirections[playerNumber] == direction)
            return;
        System.out.printf("Player %d getting rotated from %d to %d\n", playerNumber, playerDirections[playerNumber], direction);
        playerSprites[playerNumber] = rotatePlayerSprite(playerSprites[playerNumber], playerDirections[playerNumber], direction);
        playerDirections[playerNumber] = direction;
	}
}