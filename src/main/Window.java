package main;

import input.*;

import java.awt.Color;
import java.awt.Dimension;
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
	private static float screenFade = 0;
	private static float fade = 0;
	
	public Window()
	{
		// Setup window
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(true);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(WIDTH, HEIGHT));
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.setVisible(true);
		
		// Add panel to window
		frame.add(this);
		frame.addKeyListener(new KeyInput());
		frame.addMouseListener(new MouseInput());
		frame.addMouseMotionListener(new MouseInput());
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
		
		if (fade == 0)
			if (screenFade > 0) screenFade -= 0.01f;
		if (fade == 1)
			if (screenFade < 1) screenFade += 0.01f;
		
		if (screenFade <= 0) screenFade = 0;
		if (screenFade >= 1) screenFade = 1;
		
		// Draw screen fade overlay
		g.setColor(new Color(0, 0, 0, screenFade));
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
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
	
	public static void setFade0()
	{
		fade = 0;
	}
	
	public static void setFade1()
	{
		fade = 1;
	}
	
	public static float getFadeValue()
	{
		return screenFade;
	}
	public static void setFadeValue(float value)
	{
		screenFade = value;
	}
}