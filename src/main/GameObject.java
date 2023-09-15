package main;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject 
{
	/** x coordinate */
	protected int x;
	/** y coordinate */
	protected int y;
	/** object width */
	protected int width;
	/** object height */
	protected int height;
	
	public GameObject(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	/** Return the x position */
	public int getX()
	{
		return x;
	}
	
	/** Return the y position */
	public int getY()
	{
		return y;
	}
	
	/** Update the object's behaviours */
	public abstract void tick();
	
	/** Draw the object */
	public abstract void paintComponent(Graphics g);
	
	/** Collision box */
	public abstract Rectangle getBounds();
}