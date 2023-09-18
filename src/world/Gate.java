package world;

import main.Debug;
import main.GameObject;
import main.Handler;
import characters.PlayerCharacter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import editor.LevelEditor;

/** Loads different parts of the world when entered by the player */
public class Gate extends GameObject
{
	public boolean moveToNewMap = false;
	
	private String levelToLoad = "resources/levels/level1.csv";
	
	public Gate(int x, int y)
	{
		super(x, y);
		
		width = 64;
		height = 64;
		
		solid = false;
	}

	public void paintComponent(Graphics g)
	{
		if (LevelEditor.editMode)
		{
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
			
			// Draw level name
			g.setColor(Color.WHITE);
			
			String[] parts = levelToLoad.split("/");
			g.drawString(parts[parts.length - 1], x, y + height / 2);
		}
		
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}
	
	public void tick()
	{
		moveToNewMap = false;

		// Move to a different map
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			if (Handler.getObjectList().size() != 0)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				// Get the player object
				if (object instanceof PlayerCharacter)
				{
					PlayerCharacter playerObject = (PlayerCharacter) object;
					
					// Check for collision with the players
					if (getBounds().intersects(playerObject.getBounds()))
						moveToNewMap = true;
				}
			}
		}
	
		//System.out.println(moveToNewMap);
	}

	public void loadLevel()
	{
		// Load map
		if (canMoveToNewMap())
		{
			Handler.loadLevelFromGate(levelToLoad);
			movePlayers();
		}
	}
	
	private void movePlayers()
	{
		if (Handler.player1Active)
		{
			for (int i = 0; i < Handler.getObjectList().size(); i++)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				if (object instanceof PlayerCharacter)
				{
					PlayerCharacter playerObject = (PlayerCharacter) object;
					
					// Move to right
					if (getX() <= 64) 
					{ 
						playerObject.setX(1808); 
						playerObject.setY(getY()); 
						continue;
					}
					// Move to left
					if (getX() >= 1744) 
					{ 
						playerObject.setX(0); 
						playerObject.setY(getY()); 
						continue;
					}
					// Move to bottom
					if (getY() <= 64) 
					{ 
						playerObject.setY(896);
						playerObject.setX(getX()); 
						continue;
					}
					// Move to top
					if (getY() >= 832) 
					{ 
						playerObject.setY(0);
						playerObject.setX(getX());
						continue;
					}
				}
			}
		}
	}
	
	// TODO - Assign the desired level to the gate in the level editor
	private void assignLevel()
	{
		
	}
	
	public boolean canMoveToNewMap()
	{
		return moveToNewMap;
	}
	
	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, width, height);
	}
}