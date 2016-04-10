package Deck;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	Class stores information about the cards, i.e. Country field and Insignia field. 
*/

import Game.Country;

public class Card {
	
	Country territory;
	String insignia;

	public Card(Country _territory, String _insignia) {
		territory = _territory;
		insignia = _insignia;
	}
	
	/* Give the ability to overwrite the insignia, in case it can't
	   be determined in the constructor. */
	public void setCardInsignia(String _insignia) {
		insignia = _insignia;
	}
	
	/* Returns a country object for that card. */
	public Country getCardTerritory() {
		return this.territory;
	}
	
	/* Returns a string representation of the territory (country) for that card. */
	public String getCardTerritoryString() {
		return this.territory.getName();
	}
	
	/* Returns a string for the card's insignia. */
	public String getCardInsignia() {
		return insignia;
	}
	
	/* Returns the entire card as a string, for example:
	   Peru  /  Army Type: Infantry */
	public String getCardAsString() {
		return territory.getName() + "  /  Army Type: " + insignia; 
	}
	
}
