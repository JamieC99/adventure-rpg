package world;

import main.Debug;
import main.GameObject;
import main.Handler;
//import main.Window;
import characters.PlayerCharacter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.File;

import javax.swing.JFileChooser;




/** Loads different parts of the world when entered by the player */
public class Gate extends GameObject
{
	/** Checks if the player is able to move to a different level */
	public boolean moveToNewMap = false;
	/** Checks if the gate is selected for level assignment */
	private boolean selected = false;
	/** The level assigned to the gate */
	private String levelToLoad = null;
	/** Checks if the player is moving to the assigned level */
	private boolean isLoading = false;
	
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
			g.setColor(Color.CYAN);
			g.fillRect(x, y, width, height);
			
			// Draw level name
			g.setColor(Color.BLACK);
			
			if (levelToLoad != null)
			{
				Font originalFont = g.getFont();
				Font gateFont = originalFont.deriveFont(Font.BOLD, 15);
				g.setFont(gateFont);
				
				String displayLevelName = levelToLoad.replace(".csv", "");
				String[] parts = displayLevelName.split("/");
				
				g.drawString(parts[parts.length - 1], x + 6, y + 38);

				g.setFont(originalFont);
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
		if (!isLoading)
			moveToNewMap = false;
		
		// Move to the assigned level
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			if (Handler.modifyingObjectList)
				break;
			
			if (!Handler.modifyingObjectList && !isLoading)
			{
				if (Handler.getObjectList() == null)
					break;
				
				if (Handler.getObjectList().get(i) != null)
				{
					GameObject object = Handler.getObjectList().get(i);
			    
			        // Get the player object
			        if (object instanceof PlayerCharacter)
			        {
			            PlayerCharacter playerObject = (PlayerCharacter) object;
			
			            // Check for collision with the players
			            if (getBounds().intersects(playerObject.getBounds()))
			            {
			                moveToNewMap = true;
			                break; // No need to continue checking if collision is detected
			            }
			        }
				}
			}
		}
		
		// Check if the gate is selected by the editor cursor
		if (Handler.levelEditor.editMode)
		{
			if (getBounds().intersects(Handler.levelEditor.getCursorBounds()))
				selected = true;
			else
				selected = false;
		}
		else
			selected = false;
		
		// Re-call the loadLevel method when the screen fades to black
		//if (isLoading)
		//{
			//if (Window.getFadeValue() == 1)
				//loadLevel();
		//}
	}
	
	
	
	
	/** Move to another level when the player enters the gate */
	public void loadLevel()
	{
		// Check a level is not already loading
	    if (canMoveToNewMap() && !Handler.modifyingObjectList) 
	    {
    		//if (Window.getFadeValue() == 0)
    		//{
    			//Window.fadeScreenToBlack();
    			//isLoading = true;
    		//}
    		//else if (Window.getFadeValue() == 1)
    		//{
    			isLoading = true;
    			
		        Handler.loadLevel(levelToLoad);
		        movePlayers();
		        //Window.fadeScreenFromBlack();
		        
		        isLoading = false;
    		//}
	    }
	}
	
	
	
	
	/** Shift players to the opposite side of the frame */
	private void movePlayers()
	{
		if (!Handler.modifyingObjectList)
		{
			for (int i = 0; i < Handler.getObjectList().size(); i++)
			{
				if (Handler.getObjectList().get(i) != null)
				{
					GameObject object = Handler.getObjectList().get(i);
					
					if ((object instanceof PlayerCharacter) && object != null)
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
	}
	
	
	
	
	/** Assign the desired level to the gate in the level editor */
	public void assignLevel()
	{
		if (selected)
		{
			// Choose level file
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setCurrentDirectory(new File("resources/levels/"));
			int returnValue = fileChooser.showOpenDialog(null);
			
			if (returnValue == JFileChooser.APPROVE_OPTION)
			{
				levelToLoad = "resources/levels/" + fileChooser.getSelectedFile().getName();
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