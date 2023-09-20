package world;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.Debug;
import main.GameObject;

public class Path extends GameObject
{
	private BufferedImage spriteSheet, pathSprite;
	private int spriteX, spriteY;
	
	public Path(int x, int y, int type) 
	{
		super(x, y, type);
		
		width = 64;
		height = 64;
		
		// Assign path type
		switch(type)
		{
			case 0: spriteX = 0;  spriteY = 0;  solid = false; break; // Stone
			case 1: spriteX = 64; spriteY = 0;  solid = true;  break; // Water
			case 2: spriteX = 0;  spriteY = 64; solid = false; break; // Dirt
			case 3: spriteX = 64; spriteY = 64; solid = false; break; // Sand
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
		
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
}