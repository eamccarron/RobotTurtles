package View;

import Controller.TileType;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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

    public BoardPanel(int boardSize, int[] playerPositions, int[] playerDirections, HashMap<Integer, TileType> tileLayout){
        super();
        this.playerPositions = playerPositions;
        this.playerDirections = playerDirections;
        this.tileLayout = tileLayout;
        this.boardSize = boardSize;

        System.out.println(Arrays.toString(playerDirections));
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
        playerSprites[0] = rotatePlayerSprite(ImageIO.read(new File("../Assets/Player_1.jpg")), 0, playerDirections[0]);
        playerSprites[1] = rotatePlayerSprite(ImageIO.read(new File("../Assets/Player_2.jpg")), 0, playerDirections[1]);
        playerSprites[2] = rotatePlayerSprite(ImageIO.read(new File("../Assets/Player_3.jpg")), 0, playerDirections[2]);
        playerSprites[3] = rotatePlayerSprite(ImageIO.read(new File("../Assets/Player_4.jpg")), 0, playerDirections[3]);

        tileSprites[TileType.JEWEL.ordinal()] = ImageIO.read(new File("../Assets/Jewel.png"));
    }
 
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int width = this.getWidth();
        int height = this.getHeight();
        int cellWidth = width/boardLength;
        int cellHeight = height/boardLength;

        //Cast graphics to Graphics2D to render sprites
        assert g instanceof Graphics2D;
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
        
        //Draw tiles
        for(int i = 0; i < boardSize; i++){
            TileType tile = tileLayout.getOrDefault(i, TileType.EMPTY);
            if(tile != TileType.EMPTY){
                int tileX = (i % boardLength) * cellWidth;
                int tileY = (i / boardLength) * cellHeight;

                g2d.drawImage(tileSprites[tile.ordinal()], tileX, tileY, null);
            }
        }
        //Draw players
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
        playerSprites[playerNumber] = rotatePlayerSprite(playerSprites[playerNumber], playerDirections[playerNumber], direction);
        playerDirections[playerNumber] = direction;
	}
}