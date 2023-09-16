package main;

import characters.PlayerCharacter;
import world.*;

public class Main
{
	public Main()
	{
		Handler.addObject(new PlayerCharacter(256, 256, 1));
		Handler.addObject(new PlayerCharacter(256, 256, 2));
		
		Handler.addObject(new House(768, 256));
		
		for (int i = 0; i < 1920; i += 64)
			for (int j =- 32; j < 128; j += 64)
				Handler.addObject(new Tree(i, j));
		
		new Window(); // Create the game window
		
		run(); // Run the game
	}
	
	// Main game loop
	public void run()
	{
		long lastTime = System.nanoTime();
		double amountOfTicks = 120.0;
		double ns = 1000000000 / amountOfTicks;
		double delta = 0;

		while(true)
		{
			long now = System.nanoTime();
			delta += (now - lastTime) / ns;
			lastTime = now;
			while (delta >= 1)
			{
				tick();
				delta--;
			}
		}
	}
	
	// Main tick method. Updates every game object
	public void tick()
	{
		Handler.tick();
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}