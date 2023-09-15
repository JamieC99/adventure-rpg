package main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject 
{
	// Location, width and height of the object
	protected int x, y, width, height;
	
	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	// Update the object's behaviours
	public abstract void tick();
	
	// Draw the object
	public abstract void paintComponent(Graphics g);
	
	// Collision box
	public abstract Rectangle getBounds();
}