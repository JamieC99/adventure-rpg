package world;

import java.awt.Graphics;
import java.awt.Rectangle;

import main.GameObject;

public class Path extends GameObject
{
	public Path(int x, int y, int type) 
	{
		super(x, y, type);
		
		solid = false;
		
		width = 64;
		height = 64;
	}

	public void paintComponent(Graphics g) 
	{

	}

	public Rectangle getBounds()
	{
		return null;
	}
}