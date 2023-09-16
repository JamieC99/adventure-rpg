package main;

import input.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel
{
	private static final long serialVersionUID = -5674682140947704987L;
	
	// Create frame
	JFrame frame = new JFrame("Adventure RPG");
	
	// Default window size
	private final int WIDTH = 1872, HEIGHT = 999;
	
	// The scale of the image based on the window width
	private static float frameScale;
	
	public Window()
	{
		// Setup window
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(true);
		//frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMaximumSize(new Dimension(1920, 999));
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
		
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT * 2);
		
		// Draw background
		g.drawImage(new ImageIcon("resources/sprites/environment/background.png").getImage(), 0, 0, 1856, 960, null);
		
		// Draw game objects
		Handler.paintComponent(g);
		
		repaint();
	}
	
	/** Return the scale of the frame */
	public static float getFrameScale()
	{
		return frameScale;
	}
}