package input;

import characters.*;
import main.*;
import world.Gate;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyInput implements KeyListener
{
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
					}
				}
				else // Spawn player 2 at player 1's position
				{
					if (key == KeyEvent.VK_UP)
						Handler.addObject(new PlayerCharacter(playerObject.getX(), playerObject.getY(), 2, 0));
				}
			}
			
			// Move to a different part of the map when the player has entered a gate and pressed space
			if (object instanceof Gate)
			{
				Gate gateObject = (Gate) object;
				
				if (gateObject.canMoveToNewMap())
				{
					if (key == KeyEvent.VK_SPACE)
						gateObject.loadLevel();
				}
			}
		}
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
	}
	
	public void keyTyped(KeyEvent e) 
	{

	}
}