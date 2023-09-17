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
	/** Is the object solid */
	protected boolean solid = true;
	
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
	
	/** Return the object width */
	public int getWidth()
	{
		return width;
	}
	
	/** Return the object height */
	public int getHeight()
	{
		return height;
	}
	
	/** Gets if this is a solid object */
	public boolean isSolid()
	{
		return solid;
	}
	
	/** Update the object's behaviours */
	public void tick() {}
	
	/** Draw the object */
	public abstract void paintComponent(Graphics g);
	
	/** Collision box */
	public abstract Rectangle getBounds();
}