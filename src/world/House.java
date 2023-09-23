package world;

import main.Debug;
import main.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class House extends GameObject
{
	private BufferedImage spriteSheet, houseSprite;
	private int spriteX, spriteY;
	
	public House(int x, int y, int type) 
	{
		super(x, y, type);
		
		width = 256;
		height = 320;
		
		switch (type)
		{
			case 0: spriteX = 0; spriteY = 0; break;
			case 1: spriteX = 256; spriteY = 0; break;
		}
		
		// Load sprite sheet
		try
		{
			File imageFile = new File("resources/sprites/environment/buildings/houses.png");
			spriteSheet = ImageIO.read(imageFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		houseSprite = spriteSheet.getSubimage(spriteX, spriteY, width, height);
	}

	public void paintComponent(Graphics g) 
	{
		// Draw house
		g.drawImage(houseSprite, x, y, null);
		
		// Draw collision box
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x, y + 62, width, height-62);
	}
}