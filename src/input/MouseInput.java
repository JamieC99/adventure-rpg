package input;

import editor.LevelEditor;
import userinterface.*;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener
{
	private static int mouseX, mouseY;
	private static Point mousePoint = MouseInfo.getPointerInfo().getLocation();
	
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX() - 12;
		mouseY = e.getY() - 39;
		
		mousePoint = MouseInfo.getPointerInfo().getLocation();
	}

	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX() - 12;
		mouseY = e.getY() - 39;
		
		mousePoint = MouseInfo.getPointerInfo().getLocation();
	}

	public static int getMouseX()
	{
		return mouseX;
	}
	
	public static int getMouseY()
	{
		return mouseY;
	}
	
	public void mouseClicked(MouseEvent e) 
	{

	}

	public void mousePressed(MouseEvent e) 
	{
		int button = e.getButton();
		
		// Level editor button press
		for (Button buttonPressed : LevelEditor.getButtonList())
			if (buttonPressed.isSelected())
				buttonPressed.action();
			
		// If the level editor is in edit mode
		if (LevelEditor.editMode)
		{
			// Add object
			if (button == MouseEvent.BUTTON1)
				LevelEditor.placeObject();
			
			// Remove object
			if (button == MouseEvent.BUTTON3)
				LevelEditor.removeObject();
		}
	}

	public void mouseReleased(MouseEvent e) 
	{
		
	}

	public void mouseEntered(MouseEvent e) 
	{

	}

	public void mouseExited(MouseEvent e) 
	{

	}
	
	public static Point getMousePoint()
	{
		return mousePoint;
	}
	
	public static Rectangle getBounds()
	{
		return new Rectangle(mouseX, mouseY, 1, 1);
	}
}