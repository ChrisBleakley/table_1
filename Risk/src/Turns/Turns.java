package Turns;

import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;

import Game.Army;
import Game.GameMechanics;
import Game.Player;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	The class from which the game will run.
*/

public class Turns {

	private ArrayList<Player> playerList;
	private GameMechanics gameMechanics = new GameMechanics();
	private ArrayList<Army> matches = new ArrayList<Army>();
	
	public Turns(ArrayList<Player> _playerList, GameMechanics _gameMechanics) {
		playerList = _playerList;
		gameMechanics = _gameMechanics;
		
		gameMechanics.getOutput().updateGameInfoPanel("\n--- Game Turns ---");
	}
	
	public ArrayList<Player> getPlayerList() {
		return this.playerList;
	}
	
	// The number of reinforcements a player gets at the start of each turn.
	private int calculateReinforements(int numberOfTerritories) {
		
		int reinforcements = 3;
		
		if (numberOfTerritories / 3 > 3) {
			reinforcements = numberOfTerritories / 3;
		}
		
		return reinforcements;
	}
	
	// Returns the number of territories a player occupies.
	private int getNumberOfPlayerTerritories(Player player) {
		
		return player.getNumberOfTerritoriesOccupied();
	}
	
	// Method to allow the current player to reinforce their territories.
	public void placeReinforcements(Player player) {
		
		gameMechanics.getOutput().updateMapPanel();
		int reinforcements = calculateReinforements(getNumberOfPlayerTerritories(player));
			
		gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + " gets " + reinforcements + " reinforcements this turn.");
			
		reinforce(player, reinforcements);
	}
	
	/* Following two methods add the reinforcements to the player's territories and update the map */
	private void reinforce(Player player, int playerReinforcements) {
		
		player.setAvailableArmies(playerReinforcements);
	
		while (player.getAvailableArmies() > 0) {
			
			do {
				gameMechanics.getOutput().updateGameInfoPanel("Enter territory name to reinforce it:\n");
				
				String input = gameMechanics.getInput().getInputCommand();
				
				// Units holds the number of units a territory will be reinforced with.
				gameMechanics.getOutput().updateGameInfoPanel("Enter number of units to place on territory");
				
				String units = null;
				
				while(!NumberUtils.isNumber(units)){
					units = gameMechanics.getInput().getInputCommand();
					
					if(!NumberUtils.isNumber(units)){
						gameMechanics.getOutput().updateGameInfoPanel("Please enter a number!");
					}
				}
				
				while (Integer.valueOf(units) > player.getAvailableArmies()) {
					gameMechanics.getOutput().updateGameInfoPanel("You only have " + player.getAvailableArmies() + " reinforcements!");
					
					gameMechanics.getOutput().updateGameInfoPanel("Enter number of units to place on territory");
					units = gameMechanics.getInput().getInputCommand();
				}
				
				
				for (Army army : player.getPlacedArmies()) {
					
					String countryname = army.getCountry().getName();
					
					if (countryname.toLowerCase().contains(input.toLowerCase())) {
						matches.add(army);
					}
				}
				
				if(matches.size()>0){
					System.out.println("before: " + matches.get(0).getSize());
				}
				player.setAvailableArmies(this.checkMatches(player, Integer.valueOf(units)));
				if(matches.size()>0){
					System.out.println("after: " + matches.get(0).getSize());
				}
				
				
			} while (matches.size() == 0);
			
			this.matches.clear();
		}
		
	}
	
	private int checkMatches(Player player, int unitsToAdd) {
		
		switch (matches.size()) {
		
			case 0:	gameMechanics.getOutput().updateGameInfoPanel("You don't occupy that territory!");
					break;
					
			case 1:	matches.get(0).setSize(matches.get(0).getSize() + unitsToAdd);
					player.setAvailableArmies(player.getAvailableArmies() - unitsToAdd);
					gameMechanics.getOutput().updateGameInfoPanel(
							"Reinforced " + matches.get(0).getCountry().getName().toUpperCase() + " with " + unitsToAdd + " units");
					
					if (player.getAvailableArmies() > 0) {
						gameMechanics.getOutput().updateGameInfoPanel(player.getAvailableArmies() + " units left to add");
					}
					break;
					
			default:
					gameMechanics.getOutput().updateGameInfoPanel("That's not a territory, try entering again");
					matches.clear();
					break;
		}
		
		gameMechanics.getOutput().updateMapPanel();
		
		return player.getAvailableArmies();
	}
	
}

	






