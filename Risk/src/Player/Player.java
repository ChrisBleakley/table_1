package Player;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
  
	Class stores information about the players, i.e. Name, Colour. 
	Setting the information involves interacting with the GUI's text field.
*/

import java.awt.Color;
import java.util.ArrayList;
import Main.GameMechanics;

public class Player {

	private String playerName = "";
	private Color playerColour = null;
	private int playerNumber = -1;
	private ArrayList<String> playerHand = new ArrayList<String>();
	
	public void setPlayerName(GameMechanics gamemechanics, int _playerNumber) {
		if (_playerNumber < 3){
			human = true;
			playerNumber = _playerNumber;
			gamemechanics.getOutput().updateGameInfoPanel("Enter player " + playerNumber + "'s name:");
			
			playerName = gamemechanics.getInput().getInputCommand();
			
			gamemechanics.getOutput().updateGameInfoPanel("Player " + playerName + " has joined the game");
		}
		else {
			human = false;
			playerName = "Neutral " + String.valueOf(_playerNumber);
		}
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
	
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	public ArrayList<String> getPlayerHand() {
		return this.playerHand;
	}
	
	public boolean getHuman(){
		return this.human;
	}
	
	/* Removes all cards from the player's card list.
	   This is done after the inital allocation of territories. */
	public void resetPlayerHand() {
		this.playerHand.clear();
	}
	
	private boolean human;
}
