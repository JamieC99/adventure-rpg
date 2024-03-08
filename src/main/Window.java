package main;

import input.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel
{
	private static final long serialVersionUID = -5674682140947704987L;
	
	/** Main game window */
	JFrame frame = new JFrame("Adventure RPG");
	/** The scale of the image based on the window width */
	private static float frameScale;
	/** Proper size of the frame */
	private static Point frameEdgeBounds = new Point(1856, 960);
	/** Default window size */
	private final int WIDTH = frameEdgeBounds.x + 16, HEIGHT = frameEdgeBounds.y + 39;
	
	private static float screenFadeValue = 0;
	private static float fade = 0;
	
	public Window()
	{
		// Setup window
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		
		// Add panel to window
		frame.add(this);
		frame.addKeyListener(new KeyInput());
		frame.addMouseListener(new MouseInput());
		frame.addMouseMotionListener(new MouseInput());
	}
	
	public static void updateScreenFadeValue() 
	{
		// Screen fading
		if (fade == 0)
			if (screenFadeValue > 0) screenFadeValue -= 0.01f;
		if (fade == 1)
			if (screenFadeValue < 1) screenFadeValue += 0.01f;
		
		if (screenFadeValue <= 0) screenFadeValue = 0;
		if (screenFadeValue >= 1) screenFadeValue = 1;
    }
	
	// Rendering
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform transform = new AffineTransform();
		
		// Calculate the window scale
		frameScale = 1.0f * frame.getWidth() / WIDTH;
		
		// Apply scale
		transform.scale(frameScale, frameScale);
		g2d.setTransform(transform);
		
		// Create black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT * 2);
		
		// Draw background
		g.drawImage(new ImageIcon("resources/sprites/environment/background.png").getImage(),
				0, 0, frameEdgeBounds.x, frameEdgeBounds.y, null);
		
		// Draw game objects
		Handler.paintComponent(g);
		
		//updateScreenFadeValue();
		
		// Draw screen fade overlay
		g.setColor(new Color(0, 0, 0, screenFadeValue));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		if (!Handler.modifyingObjectList)
			repaint();
	}
	
	/** Return the scale of the frame */
	public static float getFrameScale()
	{
		return frameScale;
	}
	
	/** Return the frames boundaries */
	public static Point getFrameBounds()
	{
		return frameEdgeBounds;
	}
	
	public static void fadeScreenFromBlack()
	{
		fade = 0;
	}
	
	public static void fadeScreenToBlack()
	{
		fade = 1;
	}
	
	public static float getFadeValue()
	{
		return screenFadeValue;
	}
}