package Game;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	Reinforcement class
*/

import java.util.ArrayList;

public class Reinforce implements Main.Reinforce {
	
	private GameMechanics gamemechanics;
	private ArrayList<Army> matches;
	
	public Reinforce(GameMechanics gamemechanics) {
		this.gamemechanics = gamemechanics;
		matches = new ArrayList<Army>();
	}
	
	@Override
	public void setReinforcements(Player player) {
		
		if (player.getHuman()) {
			this.reinforceHuman(player);
		} else {
			this.reinforceBot(player);
		}
	}
	
	/* Allows the human players to reinforce their territories. */
	private void reinforceHuman(Player player) {
		
		do {
			gamemechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + ", enter territory name to reinforce it:");
			
			String input = gamemechanics.getInput().getInputCommand();
			
			for (Army army : player.getPlacedArmies()) {
				
				String countryname = army.getCountry().getName();
				
				if (countryname.toLowerCase().contains(input.toLowerCase())) {
					matches.add(army);
				}
			}
			
			this.checkMatches(player);
			
		} while (matches.size() == 0);
		
		this.matches.clear();
	}
	
	/* Reinforce the AI territories. */
	private void reinforceBot(Player player) {
		
		int i = (int) Math.floor(Math.random() * player.getPlacedArmies().size());
		
		Army army = player.getPlacedArmies().get(i);
		army.setSize(army.getSize() + 2);
		
		player.setAvailableArmies(player.getAvailableArmies() - 2);
	}
	
	private void checkMatches(Player player) {
		
		switch (matches.size()) {
		
			case 0:	gamemechanics.getOutput().updateGameInfoPanel("\nYou don't occupy that territory!\n");
					break;
					
			case 1:	matches.get(0).setSize(matches.get(0).getSize() + 3);
					player.setAvailableArmies(player.getAvailableArmies() - 3);
					gamemechanics.getOutput().updateGameInfoPanel("\nReinforced " + matches.get(0).getCountry().getName().toUpperCase());
					break;
					
			default:
					gamemechanics.getOutput().updateGameInfoPanel("\nThat's not a territory, try entering again!\n");
					matches.clear();
					break;
		}
		
		gamemechanics.getOutput().updateMapPanel();
	}
	
}
