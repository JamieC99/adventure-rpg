package characters;

import main.*;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;

public class PlayerCharacter extends Character
{
	// Player 1 or player 2
	private int playerNumber;
	
	public PlayerCharacter(int x, int y, int playerNumber, int type)
	{
		super(x, y, getPlayerImage(playerNumber), type);
		
		this.playerNumber = playerNumber;
	}

	// Perform collision checking
	public void collision()
	{
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			if (Handler.getObjectList() != null && !Handler.modifyingObjectList)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				// Check if the objects bounds are not null and is solid
				if (object.getBounds() != null && object.isSolid())
				{
			        if (topBounds().intersects(object.getBounds()))
			        {
			        	if (velY < 0) velY = 0;
			        	topCollide = true;
			        }
			        else
			        	topCollide = false;
			        
			        if (bottomBounds().intersects(object.getBounds()))
			        {
			        	if (velY > 0) velY = 0;
			        	bottomCollide = true;
			        }
			        else
			        	bottomCollide = false;
			        
			        if (leftBounds().intersects(object.getBounds()))
			        {
			        	if (velX < 0) velX = 0;
			        	leftCollide = true;
			        }
			        else
			        	leftCollide = false;
			        
			        if (rightBounds().intersects(object.getBounds()))
			        {
			        	if (velX > 0) velX = 0;
			        	rightCollide = true;
			        }
			        else
			        	rightCollide = false;
				}
		    }
		}
	}
	
	private static String getPlayerImage(int playerNumber) 
	{
        if (playerNumber == 1)
        {
            Handler.player1Active = true;
            return "resources/sprites/characters/char_blue_sheet.png";
        } 
        else if (playerNumber == 2) 
        {
            Handler.player2Active = true;
            return "resources/sprites/characters/char_orange_sheet.png";
        }
        else
        	return null;
    }
	
	public void paintComponent(Graphics g)
	{
		// Draw shadow
		g.drawImage(new ImageIcon("resources/sprites/characters/char_shadow.png").getImage(), x, y + 48, width, height / 2, null);
		
		// Draw player sprite
		g.drawImage(characterSprite, x, y, width, height, null);
		
		// Draw collision bounds
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.fillRect(topBounds().x, topBounds().y, topBounds().width, topBounds().height);
			g.fillRect(bottomBounds().x, bottomBounds().y, bottomBounds().width, bottomBounds().height);
			g.fillRect(leftBounds().x, leftBounds().y, leftBounds().width, leftBounds().height);
			g.fillRect(rightBounds().x, rightBounds().y, rightBounds().width, rightBounds().height);
		}
	}
	
	public int getPlayerNumber()
	{
		return playerNumber;
	}
}