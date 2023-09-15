package main;

public class Debug 
{
	private static boolean collisionBounds = false;
	
	/** Checks if the collision bounds are on or not */
	public static boolean getCollisionBounds()
	{
		return collisionBounds;
	}
	
	/** Turn on collision box debug */
	public static void showCollisionBounds()
	{
		collisionBounds = true;
	}
	
	/** Turn off collision box debug */
	public static void hideCollisionBounds()
	{
		collisionBounds = false;
	}
}