package main;

import characters.PlayerCharacter;

public class Main
{
	public Main()
	{
		Handler.addObject(new PlayerCharacter(256, 256, 1, 0));
		
		// Create the game window
		new Window();
		
		// Run the game
		run(); 
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