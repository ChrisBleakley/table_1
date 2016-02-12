package Player;

import java.awt.Color;

/*
	Class stores information about the players.
	I.e. Name, Colour. 
*/

public class Player {

	private String playerName = null;
	private Color playerColour = null;
	
	public void setPlayerName(String _playerName) {
		playerName = _playerName;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public void setPlayerColour(Color _playerColour) {
		playerColour = _playerColour;
	}
	
	public Color getPlayerColour() {
		return playerColour;
	}
	
}

