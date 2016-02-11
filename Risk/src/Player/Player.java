package Player;

import java.awt.Color;

/*
	Class stores information about the players.
	I.e. Name, Colour. 
*/

public class Player {

	private String playerName = null;
	private Color playerColour = null;
	
	// set the name of the player.
	public void setName(String _playerName)
	{
		playerName = _playerName;
	}
	
	// return the player's name.
	public String getName()
	{
		return playerName;
	}
	
	// set the player's colour.
	public void setColour(Color _playerColour)
	{
		playerColour = _playerColour;
	}
	
	// return the player's colour.
	public Color getColour()
	{
		return playerColour;
	}
	
}

