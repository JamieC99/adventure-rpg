package main;

import editor.*;
import userinterface.*;
import characters.*;
import world.*;

import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.File;
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
	public static GUI gui = new GUI();
	
	/** Checks if the objectList is being loaded. */
	public static boolean modifyingObjectList = false;
	/** Active status for player 1 */
	public static boolean player1Active = false;
	/** Active status for player 2 */
	public static boolean player2Active = false;
	
	private static int timesLoaded = 0;
	
	/** Paint the objects */
	public static void paintComponent(Graphics g)
	{
		if (!modifyingObjectList)
		{
			// Draw world objects
			for (int i = 0; i < objectList.size(); i++)
			{
				GameObject object = objectList.get(i);
				
				if (object != null)
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
				if (modifyingObjectList)
					continue;
				
				GameObject object = objectList.get(i);
				
				if (object != null && !modifyingObjectList)
					object.tick();
			}
			
			objectSort();
		}
		
		levelEditor.tick();
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
		Iterator<GameObject> iterator = objectList.iterator();
		while (iterator.hasNext())
		{
			GameObject object = iterator.next();
			
			if (!(object instanceof PlayerCharacter))
				iterator.remove();
		}
		
		System.out.println("Level Clear");
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
		fileChooser.setCurrentDirectory(new File("resources/levels/"));
		int returnValue = fileChooser.showSaveDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			String csvFileName = "resources/levels/" + fileChooser.getSelectedFile().getName() + ".csv";
			
			System.out.println(csvFileName);
			
			try (PrintWriter writer = new PrintWriter(new FileWriter(csvFileName)))
			{
				for (int i = 0; i < objectList.size(); i++)
				{
					GameObject object = objectList.get(i);
					
					if (!(object instanceof PlayerCharacter) && !(object instanceof Gate) && !(object instanceof NonPlayerCharacter))
					{
						writer.println(object.getClass().getSimpleName() + "," + object.getX() + "," + object.getY() + "," + object.getType());
					}
					else if (object instanceof Gate) // Write the gate object
					{
						writer.println(object.getClass().getSimpleName() + "," + object.getX() + "," + object.getY() + "," + object.getLevelToLoad());
					}
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
	
	
	
	
	/** Load level inside the level editor */
	public static void loadLevelFromEditor()
	{
		// Choose level file
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("resources/levels/"));
		int returnValue = fileChooser.showOpenDialog(null);
		
		if (returnValue == JFileChooser.APPROVE_OPTION)
		{
			String csvFileName = "resources/levels/" + fileChooser.getSelectedFile().getName();
			loadLevel(csvFileName);
		}
	}
	
	
	
	
	/** Level load function */
	public static void loadLevel(String levelName)
	{
		if (!modifyingObjectList)
		{
			modifyingObjectList = true;
			levelEditor.currentLevel = levelName;
			
			// Clear world objects
			clearLevel();
			
			// Read file
			try (BufferedReader reader = new BufferedReader(new FileReader(levelName)))
			{
				String[] parts;
				String line;
				String levelToLoad;
				int x, y, type;
				String className;
				
				while ((line = reader.readLine()) != null)
				{
					parts = line.split(",");
					
					// Get the class name position
					className = parts[0];
					x = Integer.parseInt(parts[1]);
					y = Integer.parseInt(parts[2]);
					type = 0;
					levelToLoad = "";
					
					type = className.equals("Gate") ? 0 : Integer.parseInt(parts[3]);
                    levelToLoad = className.equals("Gate") ? parts[3] : "";
					
					// Add the object to the list
                    if (className.equals("Tree")) 
                    {
                        objectList.add(new Tree(x, y, type));
                    } 
                    else if (className.equals("House")) 
                    {
                        objectList.add(new House(x, y, type));
                    }
                    else if (className.equals("Path")) 
                    {
                        objectList.add(new Path(x, y, type));
                    } 
                    else if (className.equals("Gate")) 
                    {
                        objectList.add(new Gate(x, y, levelToLoad));
                    } 
                    else if (className.equals("CharacterSpawner")) 
                    {
                        objectList.add(new CharacterSpawner(x, y, type));
                    }
				}
				
				reader.close();
				System.out.println("Level loaded " + ++timesLoaded + " List Size: " + getObjectList().size());
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
			
			modifyingObjectList = false;
		}
	}
	
	
	
	
	/** Sorts through every object in the object list to draw them in the correct order */
	private static void objectSort()
	{
		if (!modifyingObjectList && Handler.getObjectList() != null)
		{
			Collections.sort(objectList, new Comparator<GameObject>()
			{
			    public int compare(GameObject obj1, GameObject obj2) 
			    {
			        // Check if either obj1 or obj2 is a Path
			        boolean isObj1Path = obj1 instanceof Path;
			        boolean isObj2Path = obj2 instanceof Path;

			        // If both are Paths or neither is a Path, compare based on y-coordinates
			        if (isObj1Path == isObj2Path)
			        {
			            int y1 = (isObj1Path) ? Integer.MIN_VALUE : obj1.getY();
			            int y2 = (isObj2Path) ? Integer.MIN_VALUE : obj2.getY();
			            
			            return Integer.compare(y1, y2);
			        }

			        // If obj1 is a Path, it should come first
			        return (isObj1Path) ? -1 : 1;
			    }
			});
		}
	}
}