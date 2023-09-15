package world;

import main.Debug;
import main.GameObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tree extends GameObject
{
	private BufferedImage spriteSheet, treeSprite;
	
	public Tree(int x, int y) 
	{
		super(x, y);
		
		Random random = new Random();
		int spriteIndex = random.nextInt(3);
		spriteIndex = 128 * spriteIndex;
		
		// Load sprite sheet
		try
		{
			File imageFile = new File("resources/sprites/environment/tree_sheet.png");
			spriteSheet = ImageIO.read(imageFile);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		treeSprite = spriteSheet.getSubimage(spriteIndex, 0, 128, 160);
		
		width = 64;
		height = 96;
	}
	
	public void tick() 
	{
		
	}

	public void paintComponent(Graphics g) 
	{
		// Draw tree
		g.drawImage(treeSprite, x, y-height, width, height, null);
		
		// Show collision box
		if (Debug.getCollisionBounds())
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x + 14, y-32, width-32, height-64);
	}
}