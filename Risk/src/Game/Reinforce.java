package Game;

/*
Team Name: table_1
Student Numbers: 14480278, 14461158, 14745991

Reinforcement class
*/

import java.util.ArrayList;

public class Reinforce implements Main.Reinforce {
	public Reinforce(GameMechanics gamemechanics){
		this.gamemechanics = gamemechanics;
		matches = new ArrayList<Army>();
	}
	public void setReinforcements(Player player){
		if (player.getHuman()){
			this.reinforceHuman(player);
		}
		else {
			this.reinforceBot(player);
		}
	}
	private void reinforceHuman(Player player){
		do {
			gamemechanics.getOutput().updateGameInfoPanel(
					player.getPlayerName() + " Enter unambiguous country name (your armies already occupy) to reinforce it:\n");
			String input = gamemechanics.getInput().getInputCommand();
			for (Army army : player.getPlacedArmies()){
				String countryname = army.getCountry().getName();
				if (countryname.toLowerCase().contains(input.toLowerCase())){
					matches.add(army);
				}
			}
			this.checkMatches(player);
		} while (matches.size() == 0);
		this.matches.clear();
	}
	private void reinforceBot(Player player){
		Integer i = (int) Math.floor(Math.random() * player.getPlacedArmies().size());
		Army army = player.getPlacedArmies().get(i);
		army.setSize(army.getSize() + 1);
		player.setAvailableArmies(player.getAvailableArmies() - 1);
	}
	private void checkMatches(Player player){
		switch (matches.size()){
			case 0:	gamemechanics.getOutput().updateGameInfoPanel(player.getPlayerName() +
						" No matches found for that word in the list of countries you occupy!");
					break;
			case 1:	matches.get(0).setSize(matches.get(0).getSize() + 3);
					player.setAvailableArmies(player.getAvailableArmies() - 3);
					gamemechanics.getOutput().updateGameInfoPanel(
							player.getPlayerName() + " reinforced " + matches.get(0).getCountry().getName() + "!");
					break;
			default:	gamemechanics.getOutput().updateGameInfoPanel(player.getPlayerName() +
							" Ambiguous name for country given! Please try again!");
						matches.clear();
						break;
		}
		gamemechanics.getOutput().updateMapPanel();
	}
	
	private GameMechanics gamemechanics;
	private ArrayList<Army> matches;
}
