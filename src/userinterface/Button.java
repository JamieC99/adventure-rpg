package userinterface;

import input.MouseInput;
import main.Debug;
import main.Handler;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

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
		if (Handler.levelEditor.editorFrame.getBounds().contains(MouseInput.getMousePoint()))
		{
			if (getBounds().intersects(MouseInput.getBounds()))
			{
				selected = true;
			}
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
				Handler.levelEditor.editMode = !Handler.levelEditor.editMode;
			
			// Toggle show collision bounds
			if (buttonName == "Toggle Collision Bounds")
				Debug.collisionBounds = !Debug.collisionBounds;
			
			// Select tree
			if (buttonName == "Add Tree")
				Handler.levelEditor.selectedObjectType = ObjectType.tree;
			
			// Mass place trees
			if (buttonName == "Mass Place Trees")
				Handler.levelEditor.massPlaceTrees();
			
			// Select house
			if (buttonName == "Add House")
				Handler.levelEditor.selectedObjectType = ObjectType.house;
			
			// Clear level
			if (buttonName == "Clear Level")
				if (Handler.levelEditor.editMode)
					Handler.clearLevel();
			
			// Select gate
			if (buttonName == "Add Gate")
				Handler.levelEditor.selectedObjectType = ObjectType.gate;
			
			// Save level
			if (buttonName == "Save Level")
				Handler.saveLevel();
			
			// Load level
			if (buttonName == "Load Level")
				Handler.loadLevelFromEditor();
		}
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