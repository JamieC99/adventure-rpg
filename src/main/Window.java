package main;

import input.*;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends JPanel
{
	private static final long serialVersionUID = -5674682140947704987L;
	
	// Create frame
	JFrame frame = new JFrame("Adventure RPG");
	private final int WIDTH = 1552, HEIGHT = 999;
	
	public Window()
	{
		// Setup window
		frame.setSize(WIDTH, HEIGHT);
		frame.setResizable(false);
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
		
		// Fill black background
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		g.drawImage(new ImageIcon("resources/sprites/environment/background.png").getImage(), 0, 0, 1536, 960, null);
		
		// Draw game objects
		Handler.paintComponent(g);
		
		repaint();
	}
}