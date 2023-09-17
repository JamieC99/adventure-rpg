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
import javax.swing.ImageIcon;

public class Tree extends GameObject
{
	private BufferedImage spriteSheet, treeSprite;
	
	int treeType;
	
	public Tree(int x, int y) 
	{
		super(x, y);
		
		width = 64;
		height = 96;
		
		Random random = new Random();
		treeType = 128 * random.nextInt(3);
		
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
		
		treeSprite = spriteSheet.getSubimage(treeType, 0, 128, 160);
	}

	public void paintComponent(Graphics g) 
	{
		// Draw shadow
		g.drawImage(new ImageIcon("resources/sprites/characters/char_shadow.png").getImage(), x, y+80, 64, 32, null);
		
		// Draw tree
		g.drawImage(treeSprite, x, y, width, height, null);
		
		// Show collision box
		if (Debug.collisionBounds)
		{
			g.setColor(Color.RED);
			g.drawRect(getBounds().x, getBounds().y, getBounds().width, getBounds().height);
		}
	}

	public Rectangle getBounds()
	{
		return new Rectangle(x + 14, y + 62, width-32, height-62);
	}
}