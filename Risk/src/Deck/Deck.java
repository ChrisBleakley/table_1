package Deck;

import java.util.ArrayList;
import java.util.Random;

import GUI.Output;
import Player.Player;

public class Deck {
	
	private ArrayList<String> cardDeck = new ArrayList<String>();
	private int cardCount = -1;
	private Random randomGenerator = new Random();
	
	public Deck() {
		
		cardDeck.add("Territory Card:  Ontario  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Quebec  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  NW Territory  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Alberta  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Greenland  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  E United States  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  W United States  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Central America  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Alaska  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Great Britain  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  W Europe  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  S Europe  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Ukraine  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  N Europe  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Iceland  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Scandinavia  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Afghanistan  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  India  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Middle East  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Japan  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Ural  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Yakutsk  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Kamchatka  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Siam  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Irkutsk  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Siberia  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Mongolia  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  China  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  E Australia  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  New Guinea  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  W Australia  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Indonesia  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Venezuela  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Peru  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  Brazil  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Argentina  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  Congo  /  Army Type: Cavalry");
		cardDeck.add("Territory Card:  N Africa  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  S Africa  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Eqypt  /  Army Type: Infantry");
		cardDeck.add("Territory Card:  E Africa  /  Army Type: Artillery");
		cardDeck.add("Territory Card:  Madagascar  /  Army Type: Infantry");
		
		cardCount = cardDeck.size();
	}
	
	/* Method draws a card from the deck, removing it from the card list. */
	public String drawCard() {
		
		int tempCount = cardCount;
		int index = randomGenerator.nextInt(tempCount);
		
		String card = cardDeck.get(index);
		
		cardDeck.remove(index);
		cardCount--;
		
		return card;
	}
	
	/* Draws a player's hand */
	public void drawPlayerHand(Player player, Output gui) {
		
		gui.updateGameInfoPanel("\nPlayer " + player.getPlayerNumber() + " draw a card (Hit any key)");
		gui.getInputCommand();
		
		for (int i = 0; i < 9; i++)
		{
			String card = drawCard();
			player.getPlayerHand().add(card);
			
			gui.updateGameInfoPanel("Drew card:   " + card.toUpperCase());
		}
	}
	
	/* Method automatically draws cards for the neutral players. */
	public void drawNeutralPlayerHand(Player player, Output gui) {
		
		gui.updateGameInfoPanel("\nDrawing hand for neutral player");
		
		for (int i = 0; i < 6; i++)
		{
			String card = drawCard();
			player.getPlayerHand().add(card);
			
			gui.updateGameInfoPanel("Neutral drew card:   " + card.toUpperCase());
		}
	}
	
	
}
