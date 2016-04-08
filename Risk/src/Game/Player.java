package Game;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
  
	Class stores information about the players, i.e. Name, Colour. 
	Setting the information involves interacting with the GUI's text field.
*/

import java.awt.Color;
import java.util.ArrayList;
import Deck.Card;
import Main.GameMechanics;

public class Player implements Main.Player {
	
	private GameMechanics gamemechanics;
	private boolean human;
	private String playerName;
	private Color playerColour;
	private Integer playerNumber;
	private Integer availablearmies;
	private ArrayList<Army> placedArmies;
	private ArrayList<Card> playerHand;
	
	public Player(GameMechanics gamemechanics, boolean human) {
		
		this.gamemechanics = gamemechanics;
		this.human = human;
		
		if (human)
			this.availablearmies = gamemechanics.getInitialHumanArmySize();
		
		else 
			this.availablearmies = gamemechanics.getInitialBotArmySize();
		
		this.placedArmies = new ArrayList<Army>();
	}
	
	public void setPlayerName(Integer playernumber) {
		
		if (human) {
			playerNumber = playernumber;
			gamemechanics.getOutput().updateGameInfoPanel("Enter player " + playerNumber + "'s name:");
			
			playerName = gamemechanics.getInput().getInputCommand();
			
			gamemechanics.getOutput().updateGameInfoPanel("Player " + playerName + " has joined the game");
		}
		
		else
			playerName = "Neutral " + String.valueOf(playernumber - 2);
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
	
	public boolean getHuman() {
		return this.human;
	}
	
	public void setAvailableArmies(Integer availablearmies) {
		this.availablearmies = availablearmies;
	}
	
	public Integer getAvailableArmies() {
		return availablearmies;
	}
	
	public void addPlacedArmies(Army army) {
		this.placedArmies.add(army);
	}
	
	public void removePlacedArmy(Army armytoremove) {
		
		for (int i = 0; i < placedArmies.size(); i++) {
			
			if (placedArmies.get(i).getCountry().getName().equals(armytoremove.getCountry().getName())) {
				this.placedArmies.remove(i);
				i = placedArmies.size();
			}
			
		}
	}
	
	/* Method adds a Card to the player's hand. */
	public void addCardToPlayerHand(Card card) {
		playerHand.add(card);
	}
	
	public ArrayList<Card> getPlayerHand() {
		return this.playerHand;
	}
	
	/* Remove a card from the player's hand, will be useful for when cards are traded in. */
	public String removeCardFromHand(Card card) {
		
		if (playerHand.isEmpty())
			return playerName + ", you don't have any cards in your hand.";
		
		// Remove the card.
		for (Card c : playerHand) {
			if (c.getCardTerritoryString().equals(card.getCardTerritoryString())) {
				playerHand.remove(card);
				break;
			}
		}
		
		return "Removed " + card.getCardTerritoryString() + " card from " + playerName + "'s hand";
	}
	
	public ArrayList<Army> getPlacedArmies() {
		return this.placedArmies;
	}
	
	public int getNumberOfTerritoriesOccupied() {
		return this.placedArmies.size();
	}
	
}



