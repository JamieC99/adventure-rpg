package main;

import input.*;

import java.awt.Color;
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
	private final int WIDTH = 1552, HEIGHT = 999;
	
	private float windowScale;
	
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
	
	// Rendering
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D) g;
		
		// Apply scaling to the window
		AffineTransform transform = new AffineTransform();
		
		// Calculate the window scale
		windowScale = 1.0f * frame.getWidth() / WIDTH;
		
		//frame.setSize(frame.getWidth(), (int) (frame.getWidth() * windowScale));
		
		// Apply scale
		transform.scale(windowScale, windowScale);
		g2d.setTransform(transform);
		
		// Draw background
		g.drawImage(new ImageIcon("resources/sprites/environment/background.png").getImage(), 0, 0, 1536, 960, null);
		
		// Draw game objects
		Handler.paintComponent(g);
		
		repaint();
	}
}