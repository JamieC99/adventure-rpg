package editor;

import input.*;
import world.*;
import main.*;
import userinterface.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import characters.PlayerCharacter;

public class LevelEditor extends JPanel
{
	private static final long serialVersionUID = 1L;
	
	// Create window
	public JFrame editorFrame = new JFrame("Editor Menu");
	/** List of buttons for the level editor */
	private LinkedList<Button> buttonList = new LinkedList<Button>();
	/** Edit mode */
	public boolean editMode = false;
	// Cursor position
	private int cursorX, cursorY;
	// Size of the grid, linked to the size of the object
	private int gridSizeX = 64, gridSizeY;
	// Toggle grid lines
	public boolean showGridLines = true;
	/** Display the current level in the editor menu */
	public String currentLevel;
	
	// Object type
	public enum ObjectType
	{
		tree,
		house,
		path,
		gate
	}
	public ObjectType selectedObjectType;
	
	public LevelEditor()
	{
		selectedObjectType = ObjectType.tree;
		
		// Create frame
		editorFrame.setSize(273, 552);
		editorFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		editorFrame.setResizable(false);
		editorFrame.setAlwaysOnTop(true);
		
		editorFrame.add(this);
		editorFrame.addMouseMotionListener(new MouseInput());
		editorFrame.addMouseListener(new MouseInput());
		editorFrame.setVisible(true);
		
		// Add buttons
		buttonList.add(new Button(0, 0, "Toggle Edit Mode"));
		buttonList.add(new Button(128, 0, "Toggle Collision Bounds"));
		buttonList.add(new Button(0, 48, "Save Level"));
		buttonList.add(new Button(128, 48, "Load Level"));
		buttonList.add(new Button(0, 96, "Clear Level"));
		buttonList.add(new Button(128, 96, "Toggle Grid Lines"));
		
		buttonList.add(new Button(0, 160, "Mass Place Trees"));
		buttonList.add(new Button(0, 224, "Add Tree"));
		buttonList.add(new Button(0, 272, "Add House"));
		buttonList.add(new Button(0, 320, "Add Path"));
		buttonList.add(new Button(0, 368, "Add Gate"));
	}
	
	public void tick()
	{
		if (editMode)
		{
			// Set size of cursor based on the selected object
			if (selectedObjectType == ObjectType.house)
			{
				gridSizeX = 256;
				gridSizeY = 320;
			}
			else
			{
				gridSizeX = 64;
				gridSizeY = 64;
			}
			
			// Check if the cursor is in the main window
			if (!editorFrame.getBounds().contains(MouseInput.getMousePoint()))
			{
				// Position the cursor
				if (MouseInput.getMouseX() > cursorX * Window.getFrameScale()) cursorX += 64; // Move right
				if (MouseInput.getMouseX() < cursorX * Window.getFrameScale()) cursorX -= 64; // Move left
				
				if (MouseInput.getMouseY() > cursorY * Window.getFrameScale()) cursorY += 64; // Move down
				if (MouseInput.getMouseY() < cursorY * Window.getFrameScale()) cursorY -= 64; // Move up
			}
			
			// Clamp cursor to frame bounds
			if (cursorX <= 0) cursorX = 0;
			if (cursorX >= 1792) cursorX = 1792;
			
			if (cursorY <= 0) cursorY = 0;
			if (cursorY >= 896) cursorY = 896;
		}
		else 
		{
			cursorX = 0;
			cursorY = 0;
		}
		
		for (Button button : buttonList)
			button.tick();
	}
	
	// Draw the cursor
	public void drawCursor(Graphics g) 
	{
		if (editMode)
		{
			if (showGridLines)
			{
				g.setColor(Color.BLACK);
				for (int i = 0; i < 1856; i += 64)
				{
					for (int j = 0; j < 960; j += 64)
					{
						g.drawRect(i, j, 64, 64);
					}
				}
			}
			
			g.setColor(Color.WHITE);
			g.drawRect(cursorX, cursorY, gridSizeX, gridSizeY);
			
			g.setColor(Color.BLACK);
			g.drawRect(cursorX-1, cursorY-1, gridSizeX + 2, gridSizeY + 2);
		}
	}
	
	// Draw the level editor interface
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, editorFrame.getWidth(), editorFrame.getHeight());
		
		// Draw buttons
		for (Button button : buttonList)
			button.paintComponent(g);
		
		// Change font
		
		Font originalFont = g.getFont();
		Font menuFont = originalFont.deriveFont(Font.BOLD, 18);
		g.setFont(menuFont);
		g.setColor(Color.WHITE);
		
		// Show level name
		if (currentLevel != null)
		{
			currentLevel = currentLevel.replace(".csv", "");
			String[] parts = currentLevel.split("/");
			g.drawString("Level: " + parts[parts.length - 1], 4, 488);
		}
		
		// Show cursor coordinates
		g.drawString("X: " + cursorX + "  Y: " + cursorY, 4, 508);
		
		g.setFont(originalFont);
		
		repaint();
	}
	
	/** Creates trees across the whole level */
	public void massPlaceTrees()
	{
		if (editMode)
		{
			Random random = new Random();
			int treeType = 0;
			
			Handler.modifyingObjectList = true;
			
			for (int i = 0; i < 1856; i += 64)
			{
				for (int j =- 32; j < 896; j += 64)
				{
					treeType = random.nextInt(3); // Choose a random tree type
					
					Handler.addObject(new Tree(i, j, treeType));
				}
			}
			
			Handler.modifyingObjectList = false;
		}
	}
	
	public void placeObject()
	{
		if (editMode && !editorFrame.getBounds().contains(MouseInput.getMousePoint()))
		{
			// Place tree
			if (selectedObjectType == ObjectType.tree)
				Handler.addObject(new Tree(cursorX, cursorY - 32, 0));
			
			// Place house
			if (selectedObjectType == ObjectType.house)
				Handler.addObject(new House(cursorX, cursorY, 0));
			
			// Place path
			if (selectedObjectType == ObjectType.path)
				Handler.addObject(new Path(cursorX, cursorY, 2));
			
			// Place gate
			if (selectedObjectType == ObjectType.gate)
				Handler.addObject(new Gate(cursorX, cursorY, ""));
		}
	}
	
	public void removeObject()
	{
		if (editMode && !editorFrame.getBounds().contains(MouseInput.getMousePoint()))
		{
			Handler.modifyingObjectList = true;
			
			for (int i = 0; i < Handler.getObjectList().size(); i++)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				// Check the object to remove is not a player
				if (!(object instanceof PlayerCharacter))
				{
					// Remove the object selected by the cursor
					if (!(object instanceof Tree))
					{
						if (object.getX() == cursorX && object.getY() == cursorY)
							Handler.removeObject(object);
					}
					else // Remove tree
						if (object.getX() == cursorX && object.getY() == cursorY - 32)
							Handler.removeObject(object);
				}
			}
			
			Handler.modifyingObjectList = false;
		}
	}
	
	/** Return the list of buttons */
	public LinkedList<Button> getButtonList()
	{
		return buttonList;
	}
	
	public int getCursorX()
	{
		return cursorX;
	}
	
	public int getCursorY()
	{
		return cursorY;
	}
	
	public Rectangle getCursorBounds()
	{
		return new Rectangle(cursorX, cursorY, gridSizeX, gridSizeY);
	}
}