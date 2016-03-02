package Game;

import GUI.MapConstants;
import Main.Dice;
import Input.Input;
import Player.Player;
import Reinforce.Reinforce;
import java.util.ArrayList;
import javax.swing.JTextField;
import GUI.Output;
import Deck.Deck;

public class GameMechanics implements Main.GameMechanics{
	public GameMechanics(){
		this.tf = new JTextField();
		this.armylist = new ArrayList<Army>();
	}
	public JTextField getInputField(){
		return tf;
	}
	public void setOutput(Output output){
		this.output = output;
	}
	public Output getOutput(){
		return this.output;
	}
	public void setInput(Input input){
		this.input = input;
	}
	public Input getInput(){
		return this.input;
	}
	public void setCountryList(){
		this.countrylist = new ArrayList<Country>();
		for (int i = 0; i < MapConstants.COUNTRY_COORD.length; i++){
			countrylist.add(new Country(i, countrylist, output.getPanelSize()));
		}
		for (int i = 0; i < MapConstants.COUNTRY_COORD.length; i++){
			countrylist.get(i).setAdjacentCountries(MapConstants.ADJACENT[i]);
		}
	}
	public ArrayList<Country> getCountryList(){
		return this.countrylist;
	}
	public void setArmyList(Player player, Country country, Integer armysize){
		boolean found = false;
		int i = 0;
		while (!found && i < armylist.size()){
			if (armylist.get(i).getCountry() == country){
				found = true;
				armylist.get(i).setPlayer(player);
				armylist.get(i).setSize(armysize);
				output.updateMapPanel();
			}
			else {
				i++;
			}
		}
		if (!found){
			armylist.add(new Army(armysize, player, country));
			output.updateMapPanel();
		}
	}
	public ArrayList<Army> getArmyList(){
		return this.armylist;
	}
	public void setPlayerList(ArrayList<Player> playerlist){
		this.playerlist = playerlist;
	}
	public ArrayList<Player> getPlayerList(){
		return this.playerlist;
	}
	public void setDeck(){
		this.deck = new Deck();
		this.deck.setCountryList(this.countrylist);
	}
	public void initialiseGameMap(){
		while (!deck.isEmpty()){
			for (Player player : playerlist){
				if (player.getHuman()){
					output.updateGameInfoPanel(player.getPlayerName() + " press any key to draw a card!");
					input.getInputCommand();		
				}
				Country card = deck.getCountryCard();
				this.setArmyList(player, card, 1);
				output.updateGameInfoPanel(player.getPlayerName() + " drew the country card: " + card.getName());			
			}
		}
	}
	public void setReinforceMechanics(){
		this.reinforcemechanics = new Reinforce(this);
	}
	public void reinforce(){
		//output.updateGameInfoPanel(player.getPlayerName() + "Enter country name to reinforce:");
		//this.reinforcemechanics.setReinforcements(input.getInputCommand());
	}
	private JTextField tf;
	private Output output;
	private Input input;
	private ArrayList<Country> countrylist;
	private ArrayList<Army> armylist;
	private ArrayList<Player> playerlist;
	private Deck deck;
	private Dice die;
	private Reinforce reinforcemechanics;
}
