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
	/** The variation of the object */
	protected int type;
	/** The level to load for a Gate object */
	protected String levelToLoad;
	
	
	
	
	public GameObject(int x, int y, int type)
	{
		this.x = x;
		this.y = y;
		this.type = type;
	}
	
	
	
	
	public GameObject(int x, int y, String levelToLoad) 
	{
		this.x = x;
		this.y = y;
		this.levelToLoad = levelToLoad;
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
	
	
	
	
	/** Set x position */
	public void setX(int x)
	{
		this.x = x;
	}
	
	
	
	
	/** Set y position */
	public void setY(int y)
	{
		this.y = y;
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
	
	
	
	
	/** Return the variation of the object */
	public int getType()
	{
		return type;
	}
	
	
	
	
	public String getLevelToLoad()
	{
		return levelToLoad;
	}
	
	
	
	
	/** Update the object's behaviours */
	public void tick() {}
	
	
	
	
	/** Draw the object */
	public abstract void paintComponent(Graphics g);
	
	
	
	
	/** Collision box */
	public abstract Rectangle getBounds();
}