package input;

import editor.LevelEditor;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class MouseInput implements MouseListener, MouseMotionListener
{
	private static int mouseX, mouseY;
	
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX() - 12;
		mouseY = e.getY() - 39;
	}

	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX() - 12;
		mouseY = e.getY() - 39;
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
}