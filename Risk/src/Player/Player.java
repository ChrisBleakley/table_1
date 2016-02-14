package Player;

import java.awt.Color;

import GUI.Output;

/*
	Class stores information about the players.
	I.e. Name, Colour. 
*/

public class Player {

	private String playerName = null;
	private Color playerColour = null;
	
	public void setPlayerName(Output gui) {
		gui.updateGameInfoPanel("Enter player name:");

		playerName = gui.getTextField().getText();
		
		gui.updateGameInfoPanel("Player " + playerName + " has joined the game");
	}
	
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

