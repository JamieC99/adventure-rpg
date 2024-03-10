package userinterface;

import input.MouseInput;
import main.Debug;
import main.GameObject;
import main.Handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import characters.CharacterSpawner;
import characters.NonPlayerCharacter;
import editor.LevelEditor.ObjectType;

public class Button
{
	private int x, y, width = 128, height = 48;
	private String buttonName;
	private boolean selected = false;
	
	
	
	
	public Button(int x, int y, String name)
	{
		this.x = x;
		this.y = y;
		buttonName = name;
	}
	
	
	
	
	public void paintComponent(Graphics g)
	{
		// Draw button
		if (selected)
			g.setColor(Color.GRAY);
		else
			g.setColor(Color.BLUE);
		
		g.fillRect(x, y, width, height);
		
		// Draw outline
		g.setColor(Color.WHITE);
		g.drawRect(x, y, width, height);
		
		// Draw button name
		Font originalFont = g.getFont();
		Font buttonFont = originalFont.deriveFont(Font.BOLD, 14);
		g.setFont(buttonFont);
		g.setColor(Color.WHITE);
		
		if (buttonName == "Toggle Edit Mode")
			g.drawString("Edit Mode: " + Handler.levelEditor.editMode, x + 5, y + 32);
		else if (buttonName == "Toggle Collision Bounds")
			g.drawString("Col Bounds: " + Debug.collisionBounds, x + 5, y + 32);
		else
			g.drawString(buttonName, x + 5, y + 32);
		
		g.setFont(originalFont);
	}
	
	
	
	
	public void tick()
	{
		// Check for intersection with the mouse. Button selection
		if (Handler.levelEditor.editorFrame.getBounds().contains(MouseInput.getMousePoint()))
		{
			if (getBounds().intersects(MouseInput.getBounds()))
				selected = true;
			else
				selected = false;
		}
		else
			selected = false;
	}
	
	
	
	
	/** Perform the actions for all the buttons */
	public void action()
	{
		if (selected)
		{
			// Toggle edit mode on and off
			if (buttonName == "Toggle Edit Mode")
			{
				Handler.modifyingObjectList = true;
				for (int i = 0; i < Handler.getObjectList().size(); i++)
				{
					GameObject object = Handler.getObjectList().get(i);
					
					if (Handler.levelEditor.editMode)
					{
						// Spawn NPCs when in play mode
						if (object instanceof CharacterSpawner)
						{
							CharacterSpawner spawner = (CharacterSpawner) object;
							spawner.spawnNPC();
						}
					}
					// Remove NPCs when in edit mode
					else if (!Handler.levelEditor.editMode)
					{
						if (object instanceof NonPlayerCharacter)
						{
							Handler.removeObject(object);
						}
					}
				}
				Handler.modifyingObjectList = false;
				
				Handler.levelEditor.editMode = !Handler.levelEditor.editMode;
			}
			
			// Toggle show collision bounds
			if (buttonName == "Toggle Collision Bounds")
				Debug.collisionBounds = !Debug.collisionBounds;
			
			// Save level
			if (buttonName == "Save Level")
				Handler.saveLevel();
			
			// Load level
			if (buttonName == "Load Level")
				Handler.loadLevelFromEditor();
			
			// Clear level
			if (buttonName == "Clear Level")
			{
				if (Handler.levelEditor.editMode)
				{
					Handler.modifyingObjectList = true;
					Handler.clearLevel();
					Handler.modifyingObjectList = false;
				}
			}
			
			// Toggle grid lines
			if (buttonName == "Toggle Grid Lines")
				Handler.levelEditor.showGridLines = !Handler.levelEditor.showGridLines;
				
			// Mass place trees
			if (buttonName == "Mass Place Trees")
				Handler.levelEditor.massPlaceTrees();
			
			// Select tree
			if (buttonName == "Add Tree")
				Handler.levelEditor.selectedObjectType = ObjectType.tree;
			
			// Select NPC
			if (buttonName == "Add NPC Spawner")
				Handler.levelEditor.selectedObjectType = ObjectType.npcSpawner;
			
			// Select house
			if (buttonName == "Add House")
				Handler.levelEditor.selectedObjectType = ObjectType.house;
			
			// Select path
			if (buttonName == "Add Path")
				Handler.levelEditor.selectedObjectType = ObjectType.path;
			
			// Select gate
			if (buttonName == "Add Gate")
				Handler.levelEditor.selectedObjectType = ObjectType.gate;
		}
	}
	
	
	
	
	public void selectObjectVariation()
	{
		if (selected)
			Handler.levelEditor.selectObjectVariation();
	}
	
	
	
	
	public String getName()
	{
		return buttonName;
	}
	
	
	
	
	public boolean isSelected()
	{
		return selected;
	}
	
	
	
	
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
}