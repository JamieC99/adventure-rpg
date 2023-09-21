package characters;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import main.GameObject;
import main.Handler;

public class CharacterSpawner extends GameObject
{
	public CharacterSpawner(int x, int y, int type)
	{
		super (x, y, type);
		
		width = 64;
		height = 64;
		
		spawnNPC();
	}

	public void paintComponent(Graphics g) 
	{
		if (Handler.levelEditor.editMode)
		{
			g.setColor(Color.WHITE);
			g.fillRect(x, y, width, height);
			
			Font originalFont = g.getFont();
			Font newFont = originalFont.deriveFont(Font.BOLD, 14);
			g.setFont(newFont);
			g.setColor(Color.BLACK);
			
			g.drawString("Spawner", x + 2, y + 32);
			g.drawString("Type: " + type, x + 8, y + 48);
			
			g.setFont(originalFont);
		}
	}
	
	public void spawnNPC()
	{
		if (!Handler.levelEditor.editMode)
			Handler.addObject(new NonPlayerCharacter(x, y, type));
	}

	public Rectangle getBounds() 
	{
		return null;
	}
}