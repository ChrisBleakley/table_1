package Turns;

import java.util.ArrayList;
import java.util.Collections;

import GUI.Output;
import Game.GameMechanics;
import Game.Player;
import Input.Input;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	The class from which the game will run.
*/

public class Turns {

	private ArrayList<Player> playerList;
	private GameMechanics gameMechanics = new GameMechanics();
	
	public Turns(ArrayList<Player> _playerList, GameMechanics _gameMechanics) {
		playerList = _playerList;
		gameMechanics = _gameMechanics;
		
		gameMechanics.getOutput().updateGameInfoPanel("Game Turns");
	}
	
	// Roll dice to decide who goes first
	public void setTurnOrder() {
		
		int firstplayer = gameMechanics.decideFirstPlayer();
		
		// swap player1 and player2's positions in the list so that player2 goes first.
		if(firstplayer == 1){
			Collections.swap(playerList, 0, 1);
		}
		
		gameMechanics.getOutput().updateGameInfoPanel(playerList.get(0).getPlayerName() + " will go first");
	}
	
	// The number of reinforcements a player gets at the start of each turn.
	private int calculateReinforements(int numberOfTerritories) {
		
		int reinforcements = 3;
		
		if (numberOfTerritories / 3 > 3)
			reinforcements = numberOfTerritories / 3;
		
		return reinforcements;
	}
	
	// Returns the number of territories a player occupies.
	private int getNumberOfPlayerTerritories(Player player) {
		
		return player.getNumberOfTerritoriesOccupied();
	}
	
	public void placeReinforcements() {
		
		// First player places reinforcements
		int reinforcements = calculateReinforements(getNumberOfPlayerTerritories(playerList.get(0)));
		
		gameMechanics.getOutput().updateGameInfoPanel(playerList.get(0).getPlayerName() + " gets " + reinforcements + " this turn.");
		
		// place reinforcements.
		
		
		// Second player places reinforcement.
		
	}
	
}






