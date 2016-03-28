package Game;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	GameMechanics class: handles most of the logic of a game of RISK.
*/

import java.util.ArrayList;

import javax.swing.JTextField;

import Deck.Deck;
import Dice.Die;
import GUI.MapConstants;
import GUI.Output;
import Input.Input;
import Turns.Turns;

public class GameMechanics implements Main.GameMechanics {
	
	private JTextField tf;
	private Output output;
	private Input input;
	private ArrayList<Country> countrylist;
	private ArrayList<Army> armylist;
	private ArrayList<Player> playerlist;
	private Deck deck;
	private Die die;
	private Reinforce reinforcemechanics;
	private Integer initialhumanarmysize = 36;
	private Integer initialbotarmysize = 24;
	
	public GameMechanics(){
		this.tf = new JTextField();
		this.armylist = new ArrayList<Army>();
	}
	
	@Override
	public JTextField getInputField(){
		return tf;
	}
	
	@Override
	public void setOutput(Output output){
		this.output = output;
	}
	
	@Override
	public Output getOutput(){
		return this.output;
	}
	
	@Override
	public void setInput(Input input){
		this.input = input;
	}
	
	@Override
	public Input getInput(){
		return this.input;
	}
	
	@Override
	public void setCountryList(){
		
		this.countrylist = new ArrayList<Country>();
		
		for (int i = 0; i < MapConstants.COUNTRY_COORD.length; i++){
			countrylist.add(new Country(i, countrylist, output.getPanelSize()));
		}
		
		for (int i = 0; i < MapConstants.COUNTRY_COORD.length; i++){
			countrylist.get(i).setAdjacentCountries(MapConstants.ADJACENT[i]);
		}
	}
	
	@Override
	public ArrayList<Country> getCountryList(){
		return this.countrylist;
	}
	
	@Override
	public void setArmyList(Player player, Country country, Integer armysize){
		
		boolean found = false;
		int i = 0;
		
		while (!found && i < armylist.size()) {
			if (armylist.get(i).getCountry() == country) {
				found = true;
				
				if (armylist.get(i).getPlayer() != player) {
					armylist.get(i).getPlayer().removePlacedArmy(armylist.get(i));
					armylist.get(i).setPlayer(player);
					player.addPlacedArmies(armylist.get(i));
				}
				
				armylist.get(i).setSize(armysize);
				output.updateMapPanel();
				player.setAvailableArmies(player.getAvailableArmies() - armysize);

			} else {
				i++;
			}
		}
		
		if (!found) {
			Army newarmy = new Army(armysize, player, country);
			armylist.add(newarmy);
			output.updateMapPanel();
			player.addPlacedArmies(newarmy);
			player.setAvailableArmies(player.getAvailableArmies() - armysize);
		}
	}
	
	@Override
	public ArrayList<Army> getArmyList(){
		return this.armylist;
	}
	
	@Override
	public void setPlayerList(ArrayList<Player> playerlist){
		this.playerlist = playerlist;
	}
	
	@Override
	public ArrayList<Player> getPlayerList(){
		return this.playerlist;
	}
	
	@Override
	public void setDeck(){
		this.deck = new Deck();
		this.deck.setCountryList(this.countrylist);
	}
	
	@Override
	public void setDice(){
		this.die = new Die();
	}
	
	@Override
	public Die getDice(){
		return this.die;
	}
	
	@Override
	public Integer getInitialHumanArmySize(){
		return this.initialhumanarmysize;
	}
	
	@Override
	public Integer getInitialBotArmySize(){
		return this.initialbotarmysize;
	}
	
	@Override
	public void initialiseGameMap(){
		
		while (!deck.isEmpty()) {
			
			int indexOfFirstPlayer = decideFirstPlayer();
			int indexOfSecondPlayer = -1;
			
			if (indexOfFirstPlayer == 0)
				indexOfSecondPlayer = 1;
			
			else
				indexOfSecondPlayer = 0;
			
			output.updateGameInfoPanel(playerlist.get(indexOfFirstPlayer).getPlayerName() + " draws first!");
			
			drawCardsAndSetTerritories(playerlist.get(indexOfFirstPlayer));
			drawCardsAndSetTerritories(playerlist.get(indexOfSecondPlayer));
			
			for (int i = 2; i < 6; i++)
				drawCardsAndSetTerritories(playerlist.get(i));
			
		}
	}
	
	public void drawCardsAndSetTerritories(Player player) {
		
		if (player.getHuman()){
			output.updateGameInfoPanel("\n" + player.getPlayerName() + " enter 'draw' to draw a card");
			
			while (!input.getInputCommand().equals("draw")) {
				output.updateGameInfoPanel("That's not a command, " + player.getPlayerName() + " try using 'draw'");
			}
			
			for (int i = 0; i < 9; i++) {
				Country card = deck.getCountryCard();
				this.setArmyList(player, card, 1);
				output.updateGameInfoPanel(player.getPlayerName() + " drew territory card:  " + card.getName().toUpperCase());
			}
					
		}
		
		else {
			output.updateGameInfoPanel("\nDrawing cards for " + player.getPlayerName());
			
			for (int i = 0; i < 6; i++) {
				Country card = deck.getCountryCard();
				this.setArmyList(player, card, 1);
				output.updateGameInfoPanel(player.getPlayerName() + " drew territory card:  " + card.getName().toUpperCase());
			}
		}
		
	}
	
	/* This method handles the turn based logic for the two players */
	public void turns() {
		Turns gameTurns = new Turns(this.playerlist, this);
		
		int indexOfFirstPlayer = decideFirstPlayer();
		int indexOfSecondPlayer = -1;
		
		if (indexOfFirstPlayer == 0)
			indexOfSecondPlayer = 1;
		
		else
			indexOfSecondPlayer = 0;
		
		while (true) {
			gameTurns.placeReinforcements(gameTurns.getPlayerList().get(indexOfFirstPlayer));
			gameTurns.placeReinforcements(gameTurns.getPlayerList().get(indexOfSecondPlayer));
		}
		
		//output.updateGameInfoPanel("Turn sequence has ended!");
	}
	
	
	@Override
	public void setReinforceMechanics() {
		this.reinforcemechanics = new Reinforce(this);
	}
	
	@Override
	public void reinforce() {
		int playersToReinforce = 6;
		
		int indexOfFirstPlayer = decideFirstPlayer();
		int indexOfSecondPlayer = -1;
		
		if (indexOfFirstPlayer == 0)
			indexOfSecondPlayer = 1;
		
		else
			indexOfSecondPlayer = 0;
		
		do {
			// These two if/else blocks handle the case of either player reinforcing first/second.
			if (playerlist.get(indexOfFirstPlayer).getAvailableArmies() > 0)
				reinforcemechanics.setReinforcements(playerlist.get(indexOfFirstPlayer));
			
			else
				playersToReinforce--;
			
			if (playerlist.get(indexOfSecondPlayer).getAvailableArmies() > 0)
				reinforcemechanics.setReinforcements(playerlist.get(indexOfSecondPlayer));
			
			else playersToReinforce--;
				
			
			// Handle neutral reinforcements.
			for (int i = 2; i < playerlist.size(); i++) {
				
				if (playerlist.get(i).getAvailableArmies() > 0) {
					this.reinforcemechanics.setReinforcements(playerlist.get(i));
				} 
				
				else {
					playersToReinforce--;
				}
			}
			
		} while(playersToReinforce > 0);
	}
	
	public int decideFirstPlayer(){
		
		boolean draw;
		int index = 0;
		int player1die = 0;
		int player2die = 0;
		
		do{
			
			for (int i = 0; i < 2; i++){
				
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(i).getPlayerName() + " type 'roll' to roll the dice");
				
				while (!input.getInputCommand().equals("roll")) {
					output.updateGameInfoPanel("That's not a command, try using 'roll'");
				}
				
				die.roll();
				
				if(i==0){	
					player1die = die.getFace();
				}
				
				else {
					player2die = die.getFace();
				}
				
				this.getOutput().updateGameInfoPanel(playerlist.get(i).getPlayerName() + " rolled a " + String.valueOf(die.getFace()));
			}
			
			if (player1die==player2die){
				draw = true;
				this.getOutput().updateGameInfoPanel("It's a draw! Let's roll again!");
			}
			
			else if (player1die > player2die){
				draw = false;
				index = 0;
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(index).getPlayerName() + " rolled the highest!");
			}
			
			else {
				draw = false;
				index = 1;
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(index).getPlayerName() + " rolled the highest!");
			}	
			
		} while (draw);
		
		return index;
	}
	
}
