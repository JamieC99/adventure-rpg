package characters;

import main.GameObject;
import main.Window;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Character extends GameObject
{
	// Character attributes
	protected float velX, velY;
	protected float moveSpeed = 2f;
	protected int healthPoints = 100;
	
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
	protected String imagePath;
	
	// Time elapsed since last frame
	private int animationTimer = 0;
	// Time to wait to update the frame
	private int animationDelay = 20;
	
	// Constructor
	public Character(int x, int y, String imagePath, int type)
	{
		super(x, y, type);
		this.imagePath = imagePath;
		
		solid = false;
		
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
		
		if (velY > 0) spriteY = 0; // Move down
		if (velY < 0) spriteY = 192; // Move up
		if (velX > 0) spriteY = 128; // Move right
		if (velX < 0) spriteY = 64; // Move left
		
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
		
		// Clamp movement to screen
		if (x > Window.getFrameBounds().x - 48) x = Window.getFrameBounds().x-48;
		if (x < 0) x = 0;
		if (y > Window.getFrameBounds().y - 64) y = Window.getFrameBounds().y - 64;
		if (y < 0) y = 0;
	}
	
	/** Horizontal movement speed */
	public void moveX(float speed)
	{
		velX = speed;
	}
	
	/** Vertical movement speed */
	public void moveY(float speed)
	{
		velY = speed;
	}
	
	public float getMoveSpeed()
	{
		return moveSpeed;
	}
	
	public int getHealthPoints()
	{
		return healthPoints;
	}
	
	public void damageCharacter(int damage)
	{
		if (healthPoints > 0)
			healthPoints -= damage;
	}
	
	public void healCharacter(int heal)
	{
		if (healthPoints < 100)
			healthPoints += heal;
	}
	
	public void paintComponent(Graphics g) {}

	public void collision() {}
	
	/** Collision box used for triggers */
	public Rectangle getBounds()
	{
		return new Rectangle(x, y, width, height);
	}
}