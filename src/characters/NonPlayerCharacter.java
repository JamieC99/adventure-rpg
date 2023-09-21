package characters;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.ImageIcon;

import main.Debug;
import main.GameObject;
import main.Handler;
import main.Window;
import world.Path;

public class NonPlayerCharacter extends Character
{
	/** The amount of time until the character changes direction */
	private int movementTimer = 0;
	/** Stop = 0 Up = 1 Down = 2 Left = 3 Right = 4 */
	private int direction = 0;
	
	Random random = new Random();
	
	public NonPlayerCharacter(int x, int y, int type)
	{
		super(x, y, getCharacterSprite(type), type);
		
		moveSpeed = 1f;
		
		chooseDirection();
	}
	
	public void paintComponent(Graphics g)
	{
		// Draw shadow
		g.drawImage(new ImageIcon("resources/sprites/characters/char_shadow.png").getImage(), x, y + 48, width, height / 2, null);
		
		// Draw character sprite
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
	
	public void collision()
	{
		// Collide with screen borders
		if (y <= 0) 								 // Top bounds
			moveDown();
		else if (y >= Window.getFrameBounds().y - height) // Bottom bounds
			moveUp();
		else if (x <= 0) 							  	 // Left bounds
			moveRight();
		else if (x >= Window.getFrameBounds().x-width) 	 // Right bounds
			moveLeft();
		
		// Collide with world objects
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			if (Handler.getObjectList() != null && !Handler.modifyingObjectList)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				if (object.getBounds() != null)
				{
					if (!(object instanceof PlayerCharacter)
							&& !(object instanceof NonPlayerCharacter) 
							&& !(object instanceof Path))
					{
						if (topBounds().intersects(object.getBounds()))
							moveDown();
						else if (bottomBounds().intersects(object.getBounds()))
							moveUp();
						
						else if (leftBounds().intersects(object.getBounds()))
							moveRight();
						else if (rightBounds().intersects(object.getBounds()))
							moveLeft();
					}
				}
			}
		}
		
		chooseDirection();
	}
	
	private void chooseDirection()
	{
		movementTimer++;
		
		if (movementTimer > random.nextInt(100) + 200)
		{
			direction = random.nextInt(5);
			
			switch(direction)
			{
				case 0: stop(); break;
				case 1: moveUp(); break;
				case 2: moveDown(); break;
				case 3: moveLeft(); break;
				case 4: moveRight(); break;
			}
			
			movementTimer = 0;
		}
	}
	
	private void moveUp()
	{
		moveX(0);
		moveY(-moveSpeed);
	}
	
	private void moveDown()
	{
		moveX(0);
		moveY(moveSpeed);
	}
	
	private void moveLeft()
	{
		moveX(-moveSpeed);
		moveY(0);
	}
	
	private void moveRight()
	{
		moveX(moveSpeed);
		moveY(0);
	}
	
	public void stop()
	{
		moveX(0);
		moveY(0);
	}
	
	private static String getCharacterSprite(int type)
	{
		if (type == 0)
			return "resources/sprites/characters/char_red_sheet.png";
		else if (type == 1)			
			return "resources/sprites/characters/char_pink_sheet.png";
		else if (type == 2)
			return "resources/sprites/characters/char_yellow_sheet.png";
		else if (type == 3)
			return "resources/sprites/characters/char_green_sheet.png";
		else if (type == 4)
			return "resources/sprites/characters/char_purple_sheet.png";
		else
			return null;
	}
}