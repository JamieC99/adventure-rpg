package editor;

import input.*;
import main.GameObject;
import main.Handler;
import world.Tree;

import java.awt.Color;
import java.awt.Graphics;

public class LevelEditor
{
	public static boolean editMode = true;
	
	// Cursor position
	private static int cursorX, cursorY;
	// Size of the grid, linked to the size of the object
	private int gridSize = 64;
	
	// Object type
	private enum ObjectType
	{
		tree,
		house
	}
	private static ObjectType selectedObjectType;
	
	public LevelEditor()
	{
		selectedObjectType = ObjectType.tree;
	}
	
	public void tick() 
	{
		if (editMode)
		{
			// Position the cursor
			if (MouseInput.getMouseX() > cursorX + gridSize) cursorX += gridSize;
			if (MouseInput.getMouseX() < cursorX) cursorX -= gridSize;
			
			if (MouseInput.getMouseY() > cursorY + gridSize) cursorY += gridSize;
			if (MouseInput.getMouseY() < cursorY) cursorY -= gridSize;
		}
	}

	public void paintComponent(Graphics g) 
	{
		if (editMode)
		{
			g.setColor(Color.RED);
			g.drawRect(cursorX, cursorY, gridSize, gridSize);
		}
	}
	
	public static void placeObject()
	{
		if (editMode)
		{
			// Place a new object at the cursor
			if (selectedObjectType == ObjectType.tree)
				Handler.addObject(new Tree(cursorX, cursorY + 64));
		}
	}
	
	public static void removeObject()
	{
		if (editMode)
		{
			for (int i = 0; i < Handler.getObjectList().size(); i++)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				// Remove the object selected by the cursor
				if (!(object instanceof Tree))
				{
					if (object.getX() == cursorX && object.getY() == cursorY)
						Handler.removeObject(object);
				}
				else // Remove tree
					if (object.getX() == cursorX && object.getY() == cursorY + 64)
						Handler.removeObject(object);
			}
		}
	}
	
	public int getCursorX()
	{
		return cursorX;
	}
	
	public int getCursorY()
	{
		return cursorY;
	}
}