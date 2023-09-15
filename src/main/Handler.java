package main;

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler 
{
	private static LinkedList<GameObject> objectList = new LinkedList<GameObject>();
	
	// Paint the objects
	public static void paintComponent(Graphics g)
	{
		for (GameObject object : objectList)
			object.paintComponent(g);
	}
	
	// Update game objects
	public static void tick()
	{
		for (GameObject object : objectList)
			object.tick();
	}
	
	// Add a new object to the list
	public static void addObject(GameObject object)
	{
		objectList.add(object);
	}
	
	// Remove an object from the list
	public static void removeObject(GameObject object)
	{
		objectList.remove(object);
	}
}