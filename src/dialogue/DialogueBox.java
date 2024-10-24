package dialogue;

import main.Window;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class DialogueBox
{
	private int xPos = Window.getFrameBounds().x / 2 - 256, yPos = Window.getFrameBounds().y - 288;
	
	private boolean isOpen = false;
	
	Image dialogueImage = new ImageIcon("resources/ui/dialogue_box.png").getImage();
	
	public void paintComponent(Graphics g)
	{
		if (isOpen)
		{
			g.drawImage(dialogueImage, xPos, yPos, null);
		}
	}
	
	public void openDialogueBox()
	{
		isOpen = true;
	}
	
	public void closeDialogueBox()
	{
		isOpen = false;
	}
}