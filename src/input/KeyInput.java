package input;

import characters.*;
import main.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener
{
	public void keyPressed(KeyEvent e)
	{
		int key = e.getKeyCode();
		
		for (GameObject object : Handler.getObjectList())
		{
			// Player movement
			if (object instanceof PlayerCharacter)
			{
				PlayerCharacter playerObject = (PlayerCharacter) object;
				
				// Player 1
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
				
				// Player 2
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
				}
			}
		}
	}
	
	public void keyReleased(KeyEvent e) 
	{
		int key = e.getKeyCode();
		
		for (GameObject object : Handler.getObjectList())
		{
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
	}
	
	public void keyTyped(KeyEvent e) 
	{

	}
}