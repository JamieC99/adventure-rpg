package world;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameObject;

public class Path extends GameObject
{
	private BufferedImage spriteSheet, pathSprite;
	private int spriteX, spriteY;
	
	public Path(int x, int y, int type) 
	{
		super(x, y, type);
		
		solid = false;
		
		width = 64;
		height = 64;
		
		if (type == 0)
		{
			spriteX = 0;
			spriteY = 0;
		}
		else if (type == 1)
		{
			spriteX = 64;
			spriteY = 0;
			
			solid = true;
		}
		else if (type == 2)
		{
			spriteX = 0;
			spriteY = 64;
		}
		else if (type == 3)
		{
			spriteX = 64;
			spriteY = 64;
		}
		
		// Load sprite sheet
		try
		{
			File imageFile = new File("resources/sprites/environment/ground_sheet.png");
			spriteSheet = ImageIO.read(imageFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		pathSprite = spriteSheet.getSubimage(spriteX, spriteY, width, height);
	}

	public void paintComponent(Graphics g) 
	{
		g.drawImage(pathSprite, x, y, width, height, null);
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
}