package input;

import characters.*;
import main.*;
import world.Gate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;




public class KeyInput implements KeyListener
{
	private static boolean shiftPressed = false;
	
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			GameObject object = Handler.getObjectList().get(i);
			
			// Player movement
			if (object instanceof PlayerCharacter)
			{
				PlayerCharacter playerObject = (PlayerCharacter) object;
				
				// Player 1
				if (Handler.player1Active)
				{
					if (playerObject.getPlayerNumber() == 1)
					{
						if (!playerObject.topCollide)
							if (key == KeyEvent.VK_W) playerObject.moveY(-playerObject.getMoveSpeed()); // Move up
						
						if (!playerObject.bottomCollide)
							if (key == KeyEvent.VK_S) playerObject.moveY(playerObject.getMoveSpeed()); // Move down
						
						if (!playerObject.leftCollide)
							if (key == KeyEvent.VK_A) playerObject.moveX(-playerObject.getMoveSpeed()); // Move left
						
						if (!playerObject.rightCollide)
							if (key == KeyEvent.VK_D) playerObject.moveX(playerObject.getMoveSpeed()); // Move right
					}
				}
				
				// Player 2
				if (Handler.player2Active)
				{
					if (playerObject.getPlayerNumber() == 2)
					{
						if (!playerObject.topCollide)
							if (key == KeyEvent.VK_UP) playerObject.moveY(-playerObject.getMoveSpeed()); // Move up
						
						if (!playerObject.bottomCollide)
							if (key == KeyEvent.VK_DOWN) playerObject.moveY(playerObject.getMoveSpeed()); // Move down
						
						if (!playerObject.leftCollide)
							if (key == KeyEvent.VK_LEFT) playerObject.moveX(-playerObject.getMoveSpeed()); // Move left
						
						if (!playerObject.rightCollide)
							if (key == KeyEvent.VK_RIGHT) playerObject.moveX(playerObject.getMoveSpeed()); // Move right
						
						// Remove player 2
						if (shiftPressed && key == KeyEvent.VK_UP)
						{
							Handler.removeObject(playerObject);
							Handler.player2Active = false;
						}
					}
				}
				else // If player 2 is NOT active Spawn player 2 at player 1's position
				{
					if (key == KeyEvent.VK_UP)
					{
						Handler.addObject(new PlayerCharacter(playerObject.getX(), playerObject.getY(), 2, 0));
					}
				}
			}
			else // If the object IS NOT a player 
			{ // Move through a gate to a different level
				if (object instanceof Gate)
				{
					Gate gateObject = (Gate) object;
					
					if (!Handler.modifyingObjectList)
					{
						if (key == KeyEvent.VK_SPACE)
							gateObject.loadLevel();
					}
					else
						System.out.println("Failed to load");
				}
			}
		}
		
		// Check if shift is pressed
		if (key == KeyEvent.VK_SHIFT)
			shiftPressed = true;
	}
	
	
	
	
	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			GameObject object = Handler.getObjectList().get(i);
			
			// Stop player movement
			if (object instanceof PlayerCharacter)
			{
				PlayerCharacter playerObject = (PlayerCharacter) object;
				
				// Player 1
				if (playerObject.getPlayerNumber() == 1)
				{
					if (key == KeyEvent.VK_W || key == KeyEvent.VK_S) playerObject.moveY(0);
					if (key == KeyEvent.VK_A || key == KeyEvent.VK_D) playerObject.moveX(0);
				}
				
				// Player 2
				if (playerObject.getPlayerNumber() == 2)
				{
					if (key == KeyEvent.VK_UP || key == KeyEvent.VK_DOWN) playerObject.moveY(0);
					if (key == KeyEvent.VK_LEFT || key == KeyEvent.VK_RIGHT) playerObject.moveX(0);
				}
			}
		}
		
		// Check if shift is released
		if (key == KeyEvent.VK_SHIFT)
			shiftPressed = false;
	}
	
	
	
	
	public void keyTyped(KeyEvent e){}
	
	
	
	
	public static boolean getShiftPressed()
	{
		return shiftPressed;
	}
}