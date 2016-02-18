package Player;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
  
	Class stores information about the players, i.e. Name, Colour. 
	Setting the information involves interacting with the GUI's text field.
*/

import java.awt.Color;
import java.util.EmptyStackException;
import GUI.Output;

public class Player {

	private String playerName = "";
	private Color playerColour = null;
	
	public void setPlayerName(Output gui) {
		gui.updateGameInfoPanel("Enter player name:");
		
		while (gui.isInputStackEmpty()) {
			
			try {
				playerName = gui.getTextField().getText();
			}
			
			catch (EmptyStackException e) { }
			
		}
		
		gui.popFromInputStack();
		gui.updateGameInfoPanel("Player " + playerName + " has joined the game");
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
