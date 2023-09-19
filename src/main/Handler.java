package main;

import editor.*;
import userinterface.*;
import characters.*;
import world.*;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JFileChooser;

public class Handler
{
	/** Linked list containing every game object */
	private static LinkedList<GameObject> objectList = new LinkedList<GameObject>();
	public static LevelEditor levelEditor = new LevelEditor();
	
	/** Checks if the objectList is being loaded. */
	public static boolean modifyingObjectList = false;
	
	public static GUI gui = new GUI();
	
	/** Active status for player 1 */
	public static boolean player1Active = false;
	/** Active status for player 2 */
	public static boolean player2Active = false;
	
	/** Paint the objects */
	public static void paintComponent(Graphics g)
	{
		// Draw world objects
		if (!modifyingObjectList)
		{
			for (int i = 0; i < objectList.size(); i++)
			{
				GameObject object = objectList.get(i);
				object.paintComponent(g);
			}
		}
		
		levelEditor.drawCursor(g);
		
		gui.paintComponent(g);
	}
	
	/** Update game objects */
	public static void tick()
	{
		if (!modifyingObjectList)
		{
			for (int i = 0; i < objectList.size(); i++)
			{
				GameObject object = objectList.get(i);
				object.tick();
			}
			
			objectSort();
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

	/** Clears all objects in a level except the players */
	public static void clearLevel()
	{
		modifyingObjectList = true;
		
		Iterator<GameObject> iterator = objectList.iterator();
		while (iterator.hasNext())
		{
			GameObject object = iterator.next();
			
			if (!(object instanceof PlayerCharacter))
				iterator.remove();
		}
		
		modifyingObjectList = false;
	}
	
	/** Return the object list */
	public static LinkedList<GameObject> getObjectList()
	{
		return objectList;
	}
	
	/** Save level to a CSV file */
	public static void saveLevel()
	{
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showSaveDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			String csvFileName = fileChooser.getSelectedFile().toString() + ".csv";
			
			try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName)))
			{
				for (int i = 0; i < objectList.size(); i++)
				{
					GameObject object = objectList.get(i);
					
					if (!(object instanceof PlayerCharacter))
						writer.println(object.getClass().getSimpleName() + "," + object.getX() + "," + object.getY() + "," + object.getType());
				}
				
				writer.close();
				System.out.println("Level Saved");
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	/** Load level when entered through a gate */
	public static void loadLevelFromGate(String levelName)
	{
		loadLevel(levelName);
	}
	
	/** Load level inside the level editor */
	public static void loadLevelFromEditor()
	{
		// Choose level file
		JFileChooser fileChooser = new JFileChooser();
		int returnValue = fileChooser.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			String csvFileName = fileChooser.getSelectedFile().getAbsolutePath();
			loadLevel(csvFileName);
		}
	}
	
	/** Level load function */
	public static void loadLevel(String levelName)
	{
		modifyingObjectList = true;
		
		// Clear world objects
		clearLevel();
		
		// Read file
		try (BufferedReader reader = new BufferedReader(new FileReader(levelName)))
		{
			String line;
			while ((line = reader.readLine()) != null)
			{
				String[] parts = line.split(",");
				
				if (parts.length == 4)
				{
					// Get the class name position
					String className = parts[0];
					int x = Integer.parseInt(parts[1]);
					int y = Integer.parseInt(parts[2]);
					int type = Integer.parseInt(parts[3]);
					
					// Add the object to the list
					switch (className)
					{
						case "Tree": objectList.add(new Tree(x, y, type)); break;
						case "House": objectList.add(new House(x, y, type)); break;
						case "Gate": objectList.add(new Gate(x, y, type)); break;
					}
				}
			}
			
			reader.close();
			System.out.println("Level loaded");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		modifyingObjectList = false;
	}
	
	/** Sorts through every object in the object list to draw them in the correct order */
	private static void objectSort()
	{
		if (objectList != null && !modifyingObjectList)
		{
			synchronized (objectList)
			{
				Collections.sort(objectList, new Comparator<GameObject>()
				{
				    public int compare(GameObject obj1, GameObject obj2)
				    {
				        // Compare objects based on their y-coordinates
				    	int y1 = 0, y2 = 0;
				    	
				    	if (obj1 != null && obj2 != null)
				    	{
					        y1 = obj1.getY();
					        y2 = obj2.getY();
				    	}
				        
				        // Higher y-coordinates come first (lower in the list)
				        return Integer.compare(y1, y2);
				    }
				});
			}
		}
	}
}