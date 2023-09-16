package characters;

import main.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

public class PlayerCharacter extends Character
{
	// Player 1 or player 2
	private int playerNumber;
	
	// Collision bounds
	public boolean topCollide, bottomCollide, leftCollide, rightCollide;
	
	public PlayerCharacter(int x, int y, int playerNumber)
	{
		super(x, y);
		
		this.playerNumber = playerNumber;
		
		moveSpeed = 3;
	}
	
	public int getY()
	{
		return y;
	}
	
	// Perform collision checking
	public void collision()
	{
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			GameObject object = Handler.getObjectList().get(i);
			
			// Check if the objects bounds are not null
			if (object.getBounds() != null)
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
	
	// Top collision box
	public Rectangle topBounds()
	{
		return new Rectangle(x + 3, y + 24, width - 5, 1);
	}
	
	// Bottom collision box
	public Rectangle bottomBounds()
	{
		return new Rectangle(x + 3, y + height, width - 5, 1);
	}
	
	// Left collision box
	public Rectangle leftBounds()
	{
		return new Rectangle(x - 1, y + 27, 1, height - 29);
	} 
	
	// Right collision box
	public Rectangle rightBounds()
	{
		return new Rectangle(x + width, y + 27, 1, height - 29);
	}
	
	public void paintComponent(Graphics g)
	{
		// Draw shadow
		g.drawImage(new ImageIcon("resources/sprites/characters/char_shadow.png").getImage(), x, y + 48, width, height / 2, null);
		
		// Draw player sprite
		g.drawImage(characterSprite, x, y, width, height, null);
		
		// Draw collision bounds
		if (Debug.getCollisionBounds())
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