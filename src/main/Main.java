package main;

public class Main
{
	public Main()
	{
		new Window();
		
		run();
	}
	
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
	
	public void tick()
	{
		Handler.tick();
	}
	
	public static void main(String[] args)
	{
		new Main();
	}
}