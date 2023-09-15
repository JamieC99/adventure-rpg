package characters;

import java.awt.Graphics;

public class PlayerCharacter extends Character
{
	public PlayerCharacter(int x, int y, int playerNumber)
	{
		super(x, y);
	}
	
	public void paintComponent(Graphics g)
	{
		// Draw player sprite
		g.drawImage(characterSprite, x, y, width, height, null);
	}
}