package world;

import main.Debug;
import main.GameObject;
import main.Handler;
import characters.PlayerCharacter;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFileChooser;

/** Loads different parts of the world when entered by the player */
public class Gate extends GameObject
{
	public boolean moveToNewMap = false;
	private boolean selected = false;
	
	private String levelToLoad;
	
	public Gate(int x, int y, String levelToLoad)
	{
		super(x, y, levelToLoad);
		this.levelToLoad = levelToLoad;
		
		width = 64;
		height = 64;
		
		solid = false;
	}

	public void paintComponent(Graphics g)
	{
		if (Handler.levelEditor.editMode)
		{
			g.setColor(Color.BLUE);
			g.fillRect(x, y, width, height);
			
			// Draw level name
			g.setColor(Color.WHITE);
			
			if (levelToLoad != null)
			{
				String[] parts = levelToLoad.split("/");
				g.drawString(parts[parts.length - 1], x, y + height / 2);
			}
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
		
		// Check if the gate is selected by the editor cursor
		if (Handler.levelEditor.editMode)
		{
			if (getBounds().intersects(Handler.levelEditor.getCursorBounds()))
			{
				selected = true;
			}
			else
				selected = false;
		}
		else
			selected = false;
		
		//System.out.println(selected);
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
	
	// Assign the desired level to the gate in the level editor
	public void assignLevel()
	{
		if (selected)
		{
			// Choose level file
			JFileChooser fileChooser = new JFileChooser();
			int returnValue = fileChooser.showOpenDialog(null);
			
			if (returnValue == JFileChooser.APPROVE_OPTION)
			{
				levelToLoad = fileChooser.getSelectedFile().getAbsolutePath();
			}
		}
	}
	
	public String getLevelToLoad()
	{
		return levelToLoad;
	}
	
	public boolean getSelected()
	{
		return selected;
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