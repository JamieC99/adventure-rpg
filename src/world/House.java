package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

import main.Debug;
import main.GameObject;

public class House extends GameObject
{
	public House(int x, int y, int type) 
	{
		super(x, y, type);
		
		width = 256;
		height = 320;
	}

	public void paintComponent(Graphics g) 
	{
		// Draw house
		g.drawImage(new ImageIcon("resources/sprites/environment/buildings/house1.png").getImage(), x, y, width, height, null);
		
		// Draw collision box
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y + 62, width, height-62);
	}
}