package Game;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	GameMechanics class: handles most of the logic of a game of RISK.
*/

import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JTextField;

import Combat.Combat;
import Combat.Fortify;
import Deck.Card;
import Deck.Deck;
import Dice.Die;
import GUI.MapConstants;
import GUI.Output;
import Input.Input;
import Turns.Turns;

public class GameMechanics implements Main.GameMechanics {
	
	private JTextField textField;
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
		this.textField = new JTextField();
		this.armylist = new ArrayList<Army>();
	}
	
	@Override
	public JTextField getInputField(){
		return textField;
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
	
	/* Roll dice and draw cards to set inital territories */
	@Override
	public void initialiseGameMap(){
		
		while (!deck.isEmpty()) {
			
			int indexOfFirstPlayer = decideFirstPlayer();
			int indexOfSecondPlayer = -1;
			
			if (indexOfFirstPlayer == 0) {
				indexOfSecondPlayer = 1;
			} else {
				indexOfSecondPlayer = 0;
			}
			
			output.updateGameInfoPanel(playerlist.get(indexOfFirstPlayer).getPlayerName() + " draws first!");
			
			drawCardsAndSetTerritories(playerlist.get(indexOfFirstPlayer));
			drawCardsAndSetTerritories(playerlist.get(indexOfSecondPlayer));
			
			for (int i = 2; i < 6; i++) {
				drawCardsAndSetTerritories(playerlist.get(i));
			}
			
		}
	}
	
	/* Allow the player to draw a card, and set units on that territory. */
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
			output.updateGameInfoPanel("\nDrawing cards for " + player.getPlayerName()+".\n");
			
			for (int i = 0; i < 6; i++) {
				Country card = deck.getCountryCard();
				this.setArmyList(player, card, 1);
				output.updateGameInfoPanel(player.getPlayerName() + " drew territory card:  " + card.getName().toUpperCase());
			}
		}
		
	}
	
	//Method to display a player's hand in a list
	public void displayCards(Player player){
		if(player.getPlayerHand().size()>0){
			output.updateGameInfoPanel("\n" + player.getPlayerName() + ", would you like to see your cards? Enter 'yes' or 'no'");
			String response = new String();
			do
				{
					response = this.getInput().getInputCommand();
					
					if(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")){
						output.updateGameInfoPanel("\nPlease enter either 'yes' or 'no'!");
					}
				}
			while(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));
			
			if(response.equalsIgnoreCase("yes")){
				output.updateGameInfoPanel("\n" + player.getPlayerName() + "'s cards:");
				for(Card c: player.getPlayerHand()){
					output.updateGameInfoPanel(c.getCardAsString());
				}
			}
		}
	}
	
	//method for handling card trade ins for reinforcements
	public void cardTradeIn(Player player){
		LinkedList<Card> infantries = new LinkedList<Card>();
		LinkedList<Card> cavalries = new LinkedList<Card>();
		LinkedList<Card> artilleries = new LinkedList<Card>();
		boolean tripin = false;
		boolean diffin = false;
		boolean cantrade=false;
		//if player has 3 cards or more
		if(player.getPlayerHand().size()>2){
			for(Card c: player.getPlayerHand()){
				if(c.getCardInsignia().equalsIgnoreCase("cavalry")){
					cavalries.add(c);
				}
				if(c.getCardInsignia().equalsIgnoreCase("artillery")){
					artilleries.add(c);
				}
				if(c.getCardInsignia().equalsIgnoreCase("infantry")){
					infantries.add(c);
				}
			}
			
			System.out.println("i size: " + infantries.size());			
			System.out.println("c size: " + cavalries.size());			
			System.out.println("a size: " + artilleries.size());		
				
			//if player has 1 of each insignia he/she can trade
			if(infantries.size()>=1 && artilleries.size()>=1 && cavalries.size()>=1){
				this.getOutput().updateGameInfoPanel("\nYou have one of each insignia!");
				cantrade=true;
				diffin=true;
			}
			//if player has at least 3 of one insignia he/she can trade
			if(infantries.size()>2 || artilleries.size()>2 || cavalries.size()>2){
				this.getOutput().updateGameInfoPanel("\nYou have three of the same insignia!");
				cantrade=true;
				tripin=true;
			}
			
			String response = new String();
			if(cantrade==true){
				do
					{
						this.getOutput().updateGameInfoPanel("\nWould you like to trade in your cards for reinforcements? Enter 'yes' or 'no'.");
						response = getInput().getInputCommand();
						
						if(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")){
							this.getOutput().updateGameInfoPanel("\nPlease input either 'yes' or 'no'!");
						}
					}
				while(!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));
			}
			
			String tradeins = new String();
			char[] tradeinsarr = new char[3];
			boolean badinput = false;
			if(response.equalsIgnoreCase("yes")){
				do
					{
						badinput=false;
						this.getOutput().updateGameInfoPanel("\nEnter first letter of each insignia to be traded in! (e.g. 'iii' for three infantries)");
						tradeins = this.getInput().getInputCommand().toLowerCase();
						tradeinsarr=tradeins.toCharArray();
						
						if(tradeinsarr.length>3){
							badinput=true;
						}
						
						for(char c: tradeinsarr){
							if(c!='i' && c!='a' && c!='c'){
								badinput=true;
							}
						}
						
						if(tripin==true && (tradeinsarr[0]!=tradeinsarr[1] || tradeinsarr[0]!=tradeinsarr[2] || tradeinsarr[1]!=tradeinsarr[2])){
							badinput=true;
						}
						
						if(diffin==true && (tradeinsarr[0]==tradeinsarr[1] || tradeinsarr[0]==tradeinsarr[2] || tradeinsarr[1]==tradeinsarr[2])){
							badinput=true;
						}
						if(badinput==true){
							this.getOutput().updateGameInfoPanel("Input doesn't match your cards or you haven't selected three!");
						}
					}
				while(badinput==true);
			
				//remove cards
				int i,j;
				for(i=0;i<3;i++){
					int handsize=player.getPlayerHand().size();
					for(j=0;j<handsize;j++){
						if(player.getPlayerHand().get(j).getCardInsignia().toLowerCase().charAt(0)==tradeinsarr[i]){
							player.getPlayerHand().remove(j);
							j=handsize;
						}
					}
				}
			
			//increment trade in variable
			}
		}
	}
	
	/* This method handles the turn based logic for the two players */
	public void turns() {
		//make deck object and set deck's contents
		Deck fullDeck = new Deck();
		fullDeck.setFullDeck(countrylist, MapConstants.COUNTRY_INSIGNIAS);
		
		Turns gameTurns = new Turns(this.playerlist, this);
		Combat combat = new Combat(this.playerlist, this, this.getArmyList());
		Fortify fortify = new Fortify(this.playerlist,this, this.getArmyList());
		
		int indexOfFirstPlayer = decideFirstPlayer();
		int indexOfSecondPlayer = -1;
		
		if (indexOfFirstPlayer == 0) {
			indexOfSecondPlayer = 1;
		} 
		else {
			indexOfSecondPlayer = 0;
		}
		
		String proceed = new String();
		String winner = new String();
		
		while (true) {
			System.out.println("deck size: " + fullDeck.getFullDeck().size());
			System.out.println("p1 hand size: " + gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlayerHand().size());
			System.out.println("p2 hand size: " + gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlayerHand().size());
			
			//check army sizes to identify if cards need to be drawn at the end of each turn
			int p1territories=gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlacedArmies().size();
			int p2territories=gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlacedArmies().size();
		
			// turn sequence for first player.
			
			displayCards(gameTurns.getPlayerList().get(indexOfFirstPlayer));
			cardTradeIn(gameTurns.getPlayerList().get(indexOfFirstPlayer));
			gameTurns.placeReinforcements(gameTurns.getPlayerList().get(indexOfFirstPlayer));
			
			do
				{
					combat.invasion(gameTurns.getPlayerList().get(indexOfFirstPlayer));
					
					this.getOutput().updateGameInfoPanel("\nInput 'skip' if you want to end your battle phase, and 'continue' to enter another battle!");
					do
						{
							proceed=this.getInput().getInputCommand();
							if(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue")){
								this.getOutput().updateGameInfoPanel("\nPlease input either 'continue' or 'skip'!");
							}
						}
					while(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue"));
				}
			while(proceed.equalsIgnoreCase("Continue"));
			
			//end game when player 2 runs out of armies.
			if(gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlacedArmies().size()==0){
				winner=gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlayerName();
				break;
			}
			if(gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlacedArmies().size()==0){
				winner=gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlayerName();
				break;
			}
			
			this.getOutput().updateGameInfoPanel("\nInput 'skip' if you want to skip fortification, and 'continue' to fortify!");
			do
				{
					proceed=this.getInput().getInputCommand();
					if(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue")){
						this.getOutput().updateGameInfoPanel("Please input either 'continue' or 'skip'");
					}
				}
			while(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue"));
			
			if(proceed.equalsIgnoreCase("continue")){
				fortify.moveUnits(gameTurns.getPlayerList().get(indexOfFirstPlayer));
			}
			
			if(p1territories<gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlacedArmies().size()){
				//draw
				Card card=fullDeck.getCard();
				gameTurns.getPlayerList().get(indexOfFirstPlayer).addCardToPlayerHand(card);
				this.getOutput().updateGameInfoPanel("\n"+gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlayerName() + " draws " + card.getCardTerritoryString() + "!");		
			}
			
			// turn sequence for the second player.
			displayCards(gameTurns.getPlayerList().get(indexOfSecondPlayer));
			cardTradeIn(gameTurns.getPlayerList().get(indexOfSecondPlayer));
			gameTurns.placeReinforcements(gameTurns.getPlayerList().get(indexOfSecondPlayer));

			do
				{
					combat.invasion(gameTurns.getPlayerList().get(indexOfSecondPlayer));
					this.getOutput().updateGameInfoPanel("\nInput 'skip' if you want to end your battle phase, and 'continue' to enter another battle!");
					do
						{
							proceed=this.getInput().getInputCommand();
							if(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue")){
								this.getOutput().updateGameInfoPanel("\nPlease input either 'continue' or 'skip'");
							}
						}
					while(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue"));
				}
			while(proceed.equalsIgnoreCase("Continue"));
			
			this.getOutput().updateGameInfoPanel("\nInput 'skip' if you want to skip fortification, and 'continue' to fortify!");
			do
				{
					proceed=this.getInput().getInputCommand();
					if(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue")){
						this.getOutput().updateGameInfoPanel("\nPlease input either 'continue' or 'skip'");
					}
				}
			while(!proceed.equalsIgnoreCase("skip") && !proceed.equalsIgnoreCase("continue"));
			
			if(proceed.equalsIgnoreCase("continue")){
				fortify.moveUnits(gameTurns.getPlayerList().get(indexOfSecondPlayer));
			}
			
			if(p2territories<gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlacedArmies().size()){
				//draw
				Card card=fullDeck.getCard();
				gameTurns.getPlayerList().get(indexOfSecondPlayer).addCardToPlayerHand(card);
				this.getOutput().updateGameInfoPanel("\n"+gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlayerName() + " draws " + card.getCardTerritoryString() + "!");		
			}
			
			//check after each turn if the game is over
			if(gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlacedArmies().size()==0){
				winner=gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlayerName();
				break;
			}
			if(gameTurns.getPlayerList().get(indexOfSecondPlayer).getPlacedArmies().size()==0){
				winner=gameTurns.getPlayerList().get(indexOfFirstPlayer).getPlayerName();
				break;
			}
		}
		
		output.updateGameInfoPanel("Winner is " + winner +"!\nGAME OVER");
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
		
		if (indexOfFirstPlayer == 0) {
			indexOfSecondPlayer = 1;
		} else {
			indexOfSecondPlayer = 0;
		}
		
		do {
			// These two if/else blocks handle the case of either player reinforcing first/second.
			if (playerlist.get(indexOfFirstPlayer).getAvailableArmies() > 0) {
				reinforcemechanics.setReinforcements(playerlist.get(indexOfFirstPlayer));
			} 
			else {
				playersToReinforce--;
			}
			
			if (playerlist.get(indexOfSecondPlayer).getAvailableArmies() > 0) {
				reinforcemechanics.setReinforcements(playerlist.get(indexOfSecondPlayer));
			} 
			else {
				playersToReinforce--;
			}
				
			
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
	
	/* Dice roll logic that is used to determine which human player goes first */
	public int decideFirstPlayer(){
		
		boolean draw;
		int index = 0;
		int player1die = 0;
		int player2die = 0;
		
		do{
			
			for (int i = 0; i < 2; i++){
				
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(i).getPlayerName() + " type 'roll' to roll the dice!");
				
				while (!input.getInputCommand().equals("roll")) {
					output.updateGameInfoPanel("\nInvalid command, try using 'roll'!");
				}
				
				die.roll();
				
				if(i == 0){	
					player1die = die.getFace();
				}
				
				else {
					player2die = die.getFace();
				}
				
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(i).getPlayerName() + " rolled a " + String.valueOf(die.getFace()) + "!");
			}
			
			if (player1die == player2die){
				draw = true;
				this.getOutput().updateGameInfoPanel("It's a draw! Let's roll again!");
			}
			
			else if (player1die > player2die){
				draw = false;
				index = 0;
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(index).getPlayerName() + " rolled the highest!\n");
			}
			
			else {
				draw = false;
				index = 1;
				this.getOutput().updateGameInfoPanel("\n" + playerlist.get(index).getPlayerName() + " rolled the highest!\n");
			}	
			
		} while (draw);
		
		return index;
	}
	
}
