package input;

import main.*;
import userinterface.*;
import world.Gate;

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
	private Point mouseOffset = new Point(8, 32);
	
	// Object selection
	private boolean objectIsSelected = false;
	
	public void mouseDragged(MouseEvent e)
	{
		mouseX = e.getX() - mouseOffset.x;
		mouseY = e.getY() - mouseOffset.y;
		
		mousePoint = MouseInfo.getPointerInfo().getLocation();
	}

	public void mouseMoved(MouseEvent e) 
	{
		mouseX = e.getX() - mouseOffset.x;
		mouseY = e.getY() - mouseOffset.y;
		
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
		checkIfObjectSelected();
		
		int button = e.getButton();
		
		// Level editor button press
		for (Button buttonPressed : Handler.levelEditor.getButtonList())
		{
			if (button == MouseEvent.BUTTON1)
				buttonPressed.action();
			else if (button == MouseEvent.BUTTON3)
				buttonPressed.selectObjectVariation();
		}
			
		// If the level editor is in edit mode
		if (Handler.levelEditor.editMode)
		{
			// Add object. Only place an object if the grid is clear or shift is also pressed
			if (button == MouseEvent.BUTTON1 && (!objectIsSelected || KeyInput.getShiftPressed()))
			{
				Handler.modifyingObjectList = true;
				Handler.levelEditor.placeObject();
				Handler.modifyingObjectList = false;
			}
			
			// Remove object
			if (button == MouseEvent.BUTTON3)
			{
				Handler.modifyingObjectList = true;
				Handler.levelEditor.removeObject();
				Handler.modifyingObjectList = false;
			}
			
			// Assign level to gate
			for (int i = 0; i < Handler.getObjectList().size(); i++)
			{
				GameObject object = Handler.getObjectList().get(i);
				
				if (object instanceof Gate)
				{
					Gate gateObject = (Gate) object;
					
					if (gateObject.getSelected())
						gateObject.assignLevel();
				}
			}
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
	
	/** Check if an object is selected by the mouse */
	private void checkIfObjectSelected()
	{
		objectIsSelected = false;
		
		for (int i = 0; i < Handler.getObjectList().size(); i++)
		{
			GameObject object = Handler.getObjectList().get(i);
			
			if (object.getBounds() != null)
			{
				if (Handler.levelEditor.getCursorBounds().intersects(object.getBounds()))
				{
					objectIsSelected = true;
				}
			}
		}
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