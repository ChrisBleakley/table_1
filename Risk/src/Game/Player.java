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
		
		if (human) {
			this.availablearmies = gamemechanics.getInitialHumanArmySize();
		} else {
			this.availablearmies = gamemechanics.getInitialBotArmySize();
		}
		
		this.placedArmies = new ArrayList<Army>();
		this.playerHand = new ArrayList<Card>();
	}
	
	@Override
	public void setPlayerName(Integer playernumber) {
		
		if (human) {
			playerNumber = playernumber;
			gamemechanics.getOutput().updateGameInfoPanel("\nEnter player " + playerNumber + "'s name:");
			
			playerName = gamemechanics.getInput().getInputCommand();
			
			gamemechanics.getOutput().updateGameInfoPanel("\nPlayer " + playerName + " has joined the game!");
		} else {
			playerName = "Neutral " + String.valueOf(playernumber - 2);
		}
	}
	
	@Override
	public String getPlayerName() {
		return playerName;
	}
	
	@Override
	public void setPlayerColour(Color _playerColour) {
		playerColour = _playerColour;
	}
	
	@Override
	public Color getPlayerColour() {
		return playerColour;
	}
	
	@Override
	public int getPlayerNumber() {
		return this.playerNumber;
	}
	
	@Override
	public boolean getHuman() {
		return this.human;
	}
	
	@Override
	public void setAvailableArmies(Integer availablearmies) {
		this.availablearmies = availablearmies;
	}
	
	@Override
	public Integer getAvailableArmies() {
		return availablearmies;
	}
	
	@Override
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
		System.out.println("inside: " + card);
		System.out.println(card.getCardTerritoryString());
		playerHand.add(card);
	}
	
	/* Returns the players hand. */
	public ArrayList<Card> getPlayerHand() {
		return this.playerHand;
	}
	
	/* Remove a card from the player's hand, will be useful for when cards are traded in. 
	   Returns a string with the territory name. */
	public String removeCardFromHand(Card card) {
		
		if (playerHand.isEmpty()) {
			return playerName + ", you don't have any cards in your hand.";
		}
		
		// Remove the card.
		for (Card c : playerHand) {
			if (c.getCardTerritoryString().equals(card.getCardTerritoryString())) {
				playerHand.remove(card);
				break;
			}
		}
		
		return "Removed " + card.getCardTerritoryString() + " card from " + playerName + "'s hand";
	}
	
	@Override
	public ArrayList<Army> getPlacedArmies() {
		return this.placedArmies;
	}
	
	public int getNumberOfTerritoriesOccupied() {
		return this.placedArmies.size();
	}
	
}



