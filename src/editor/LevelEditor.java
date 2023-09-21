package editor;

import input.*;
import world.*;
import main.*;
import userinterface.*;
import characters.CharacterSpawner;
import characters.PlayerCharacter;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.LinkedList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

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
	/** The variable of the object to create */
	private int objectVariation;
	
	// Object type
	public enum ObjectType
	{
		tree,
		house,
		path,
		gate,
		npcSpawner
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
		buttonList.add(new Button(128, 224, "Add NPC Spawner"));
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
			if (cursorX >= Window.getFrameBounds().x - 64) cursorX = Window.getFrameBounds().x - 64;
			
			if (cursorY <= 0) cursorY = 0;
			if (cursorY >= Window.getFrameBounds().y - 64) cursorY = Window.getFrameBounds().y - 64;
		}
		else
		{
			cursorX = 0;
			cursorY = 0;
		}
		
		for (Button button : buttonList)
			button.tick();
	}
	
	/** Draw the editor cursor */
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
						g.drawRect(i, j, 64, 64);
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
		
		g.drawString("Object: " + selectedObjectType.toString() + " Type: " + objectVariation, 4, 468);
		
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
			Handler.modifyingObjectList = true;
			
			for (int i = 0; i < Window.getFrameBounds().x; i += 64)
				for (int j = 0; j < Window.getFrameBounds().y; j += 64)
					addTree(i, j);
			
			Handler.modifyingObjectList = false;
		}
	}
	
	/** Add a tree at the given coordinates with a random type */
	private void addTree(int x, int y)
	{
		Random random = new Random();
		int treeType = random.nextInt(3);
		Handler.addObject(new Tree(x, y-32, treeType));
	}
	
	public void placeObject()
	{
		if (editMode && !editorFrame.getBounds().contains(MouseInput.getMousePoint()))
		{
			Handler.modifyingObjectList = true;
			
			// Place tree
			if (selectedObjectType == ObjectType.tree)
				addTree(cursorX, cursorY);
			
			if (selectedObjectType == ObjectType.npcSpawner)
				Handler.addObject(new CharacterSpawner(cursorX, cursorY, objectVariation));
			
			// Place house
			if (selectedObjectType == ObjectType.house)
				Handler.addObject(new House(cursorX, cursorY, 0));
			
			// Place path
			if (selectedObjectType == ObjectType.path)
				Handler.addObject(new Path(cursorX, cursorY, objectVariation));
			
			// Place gate
			if (selectedObjectType == ObjectType.gate)
				Handler.addObject(new Gate(cursorX, cursorY, ""));
			
			Handler.modifyingObjectList = false;
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
	
	public void selectObjectVariation()
	{
		if (selectedObjectType == ObjectType.path)
		{
			String[] options = {"Stone", "Water", "Dirt", "Sand"};
			objectVariation = showObjectSelectDialog("Select an option", "Object Selection", options);
		}
		else if (selectedObjectType == ObjectType.npcSpawner)
		{
			String[] options = {"Red", "Pink", "Yellow", "Green", "Purple"};
			objectVariation = showObjectSelectDialog("Select an option", "NPC Selection", options);
		}
		
	}
	
	private int showObjectSelectDialog(String message, String title, String[] options)
	{
		int result = JOptionPane.showOptionDialog(
				null,
				message,
				title,
				JOptionPane.DEFAULT_OPTION,
				JOptionPane.PLAIN_MESSAGE,
				null,
				options,
				options[0]);
		return result;
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