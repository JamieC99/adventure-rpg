package main;

import editor.LevelEditor;
import userinterface.*;

import java.awt.Graphics;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class Handler 
{
	/** Linked list containing every game object */
	private static LinkedList<GameObject> objectList = new LinkedList<GameObject>();
	public static LevelEditor levelEditor = new LevelEditor();
	
	public static GUI gui = new GUI();
	
	// Player objects
	private boolean player1Active = true;
	private boolean player2Active = false;
	
	/** Paint the objects */
	public static void paintComponent(Graphics g)
	{
		// Draw world objects
		for (int i = 0; i < objectList.size(); i++)
		{
			GameObject object = objectList.get(i);
			object.paintComponent(g);
		}
		
		levelEditor.drawCursor(g);
		
		gui.paintComponent(g);
	}
	
	/** Update game objects */
	public static void tick()
	{
		objectSort();
		
		for (int i = 0; i < objectList.size(); i++)
		{
			GameObject object = objectList.get(i);
			object.tick();
		}
		
		levelEditor.tick();
		
		gui.tick();
	}
	
	/** Add a new object to the list */
	public static void addObject(GameObject object)
	{
		objectList.add(object);
	}
	
	/** Remove an object from the list */
	public static void removeObject(GameObject object)
	{
		objectList.remove(object);
	}
	
	/** Return the object list */
	public static LinkedList<GameObject> getObjectList()
	{
		return objectList;
	}
	
	/** Sorts through every object in the object list to draw them in the correct order */
	private static void objectSort()
	{
		Collections.sort(objectList, new Comparator<GameObject>()
		{
		    public int compare(GameObject obj1, GameObject obj2)
		    {
		        // Compare objects based on their Y-coordinates
		        int y1 = obj1.getY();
		        int y2 = obj2.getY();
		        
		        // Higher Y-coordinates come first (lower in the list)
		        return Integer.compare(y1, y2);
		    }
		});
	}
}