package Deck;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	Card-Deck class for drawing cards
*/

import java.util.ArrayList;
import java.util.Collections;
import Game.Country;

public class Deck implements Main.Deck {
	
	private ArrayList<Country> countrycards;
	private ArrayList<Card> fullGameDeck = new ArrayList<Card>();
	
	/* Due to how the full deck is set, it's more efficient to have a copy
	   of the deck (for when we need to reset the deck), than setting it completely, again. */
	private ArrayList<Card> deckBackup = new ArrayList<Card>();
	
	
	/* Add the initial territory cards to the game deck. */
	public void setCountryList(ArrayList<Country> countrylist){
		
		this.countrycards = new ArrayList<Country>();
		
		for (Country country : countrylist){
			this.countrycards.add(country);
		}
		
	}
	
	/* Set each card of the full deck, and shuffle it. */
	public void setFullDeck(ArrayList<Country> countryList, String[] insignias) {
		
		for (int i = 0; i < countryList.size(); i++) {
			
			Card nextCard = new Card(countryList.get(i), insignias[i]);
			
			fullGameDeck.add(nextCard);
		}
		
		Collections.shuffle(fullGameDeck);
		
		/* Store the deck in the backup list. */
		for (Card card : fullGameDeck)
			deckBackup.add(card);
	}
	
	/* Draw a card from the deck. If deck is empty, reset it and draw. */
	public Card getCard() {
		
		if (fullGameDeck.isEmpty())
			resetDeck();
		
		Card card = fullGameDeck.get(0);
		fullGameDeck.remove(0);
		
		return card;
	}
	
	/* Returns the full game deck list. */
	public ArrayList<Card> getFullDeck() {
		return this.fullGameDeck;
	}
	
	/* Clears the deck (if needed), and resets the cards. */
	public void resetDeck() { 
		
		if (!fullGameDeck.isEmpty())
			fullGameDeck.clear();
		
		for (Card card : deckBackup) {
			fullGameDeck.add(card);
		}
		
		Collections.shuffle(fullGameDeck);
	}
	
	public Country getCountryCard(){
		
		Collections.shuffle(countrycards);
		Country countrycard = countrycards.get(0);
		countrycards.remove(0);
		
		return countrycard;
	}
	
	public boolean isEmpty(){
		
		boolean empty;
		
		if (countrycards.size() == 0)
			empty = true;
		
		else 
			empty = false;
		
		return empty;
	}
	
}
