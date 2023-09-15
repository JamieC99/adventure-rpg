package characters;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import main.GameObject;

public class Character extends GameObject
{
	// Character movement
	protected int velX, velY;
	protected int moveSpeed = 2;
	
	/** 
		Position of the desired image in the sprite sheet
		spriteY = 0   // DOWN
		spriteY = 64  // LEFT
		spriteY = 128 // RIGHT
		spriteY = 192 // UP
	*/
	protected int spriteX = 0, spriteY = 0;
	
	/** Sprite sheet */
	protected BufferedImage spriteSheet; 
	/** Sub image */
	protected BufferedImage characterSprite;
	// Sprite path
	protected String imagePath = "resources/sprites/characters/char_blue_sheet.png";
	
	// Time elapsed since last frame
	private int animationTimer = 0;
	// Time to wait to update the frame
	private int animationDelay = 20;
	
	// Constructor
	public Character(int x, int y)
	{
		super(x, y);
		
		// Width and height of characters
		width = 48;
		height = 64;
		
		// Load the sprite
		try
		{
			File imageFile = new File(imagePath);
			spriteSheet = ImageIO.read(imageFile);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
		
		// Assign image of the character standing still
		characterSprite = spriteSheet.getSubimage(48, spriteY, width, height);
	}
	
	private void animate()
	{
		// Play the animation when the character is moving
		if (velX != 0 || velY != 0)
		{
			// Update timer
			animationTimer++;
			
			// Update frame
			if (animationTimer >= animationDelay)
			{
				spriteX += width;
				
				if (spriteX >= spriteSheet.getWidth())
					spriteX = 0;
				
				animationTimer = 0;
			}	
		}
		else
			spriteX = 48; // Standing still image
			
		// Reassign image	
		characterSprite = spriteSheet.getSubimage(spriteX, spriteY, width, height);	
	}
	
	public void tick() 
	{
		collision();
		animate();
		
		// Update movement
		x += velX;
		y += velY;
	}
	
	public void moveX(int speed)
	{
		velX = speed;
		
		if (velX > 0) spriteY = 128; // Move right
		if (velX < 0) spriteY = 64; // Move left
	}
	
	public void moveY(int speed)
	{
		velY = speed;
		
		if (velY > 0) spriteY = 192; // Move down
		if (velY < 0) spriteY = 0; // Move up
	}
	
	public void paintComponent(Graphics g) {}

	public void collision() {}
	
	public Rectangle getBounds() 
	{
		return null;
	}
}