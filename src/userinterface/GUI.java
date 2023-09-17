package userinterface;

import main.*;
import characters.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/** Draws elements for the user interface */
public class GUI
{
	public void paintComponent(Graphics g)
	{
		// Set font
		Font originalFont = g.getFont();
		Font playerNameFont = originalFont.deriveFont(Font.BOLD, 14);
		g.setFont(playerNameFont);
		
		// Display player names
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			GameObject object = Handler.getObjectList().get(i);
			
			if (object instanceof PlayerCharacter)
			{
				PlayerCharacter playerObject = (PlayerCharacter) object;
				
				if (playerObject.getPlayerNumber() == 1)
				{
					g.setColor(Color.BLACK);
					g.fillRect(playerObject.getX()-8, playerObject.getY()-20, 64, 16); // Draw name box
					
					g.setColor(Color.CYAN);
					g.drawString("Player 1", playerObject.getX()-2, playerObject.getY()-8);
				}
				else if (playerObject.getPlayerNumber() == 2)
				{
					g.setColor(Color.BLACK);
					g.fillRect(playerObject.getX()-8, playerObject.getY()-20, 64, 16); // Draw name box
					
					g.setColor(Color.ORANGE);
					g.drawString("Player 2", playerObject.getX()-2, playerObject.getY()-8);
				}
			}
		}
		
		g.setFont(originalFont);
	}
	
	public void tick()
	{
		
	}
}