package world;

import main.Debug;
import main.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import editor.LevelEditor;

/** Loads different parts of the world when entered by the player */
public class Gate extends GameObject
{
	private static final long serialVersionUID = 4810522481310547325L;

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
			
			g.setColor(Color.WHITE);
			g.drawString("GATE", x + width / 4, y + height / 2);
		}
		
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}

	public Rectangle getBounds() 
	{
		return new Rectangle(x, y, width, height);
	}
}