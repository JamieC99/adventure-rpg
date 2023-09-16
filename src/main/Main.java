package main;

import characters.PlayerCharacter;
import world.*;

public class Main
{
	public Main()
	{
		Handler.addObject(new PlayerCharacter(256, 0, 1));
		Handler.addObject(new PlayerCharacter(256, 0, 2));
		
		for (int i = 0; i < 1536; i += 64)
			for (int j = 0; j < 256; j += 64)
				if (i != 256)
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