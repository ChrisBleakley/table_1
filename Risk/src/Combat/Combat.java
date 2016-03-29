package Combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import GUI.MapConstants;
import Game.Army;
import Game.GameMechanics;
import Game.Player;

public class Combat {

	private ArrayList<Player> playerList;
	private GameMechanics gameMechanics = new GameMechanics();
	private ArrayList<Army> armies = new ArrayList<Army>();
	private Army attackingarmy = null;
	private Army defendingarmy = null;
	
	public Combat(ArrayList<Player> _playerList, GameMechanics _gameMechanics, ArrayList<Army> armies) {
		this.playerList = _playerList;
		this.gameMechanics = _gameMechanics;
		this.armies=armies;
		
		gameMechanics.getOutput().updateGameInfoPanel("\n--- Game Turns ---");
	}
	
	public void invasion(Player player){
		gameMechanics.getOutput().updateGameInfoPanel("\n<----------Combat--------->\n");
		for(Army w: armies){
			System.out.println(w.getPlayer().getPlayerName() + " " + w.getSize() + " " + w.getCountry().getName());
		}
		battle(player);
		
		//ask user to input next move i.e. invade or skip 
		//if invade call this method again
		//if skip next player's turn
	}
	public void battle(Player player){
		LinkedList<Integer> player1rolls = new LinkedList<Integer>();
		LinkedList<Integer> player2rolls = new LinkedList<Integer>();
		int attunits=attack(player);
		int defunits = defend(defendingarmy);
		
		//roll attack() dice
		int c;
		
		for(c=0;c<attunits;c++){
			gameMechanics.getDice().roll();
			player1rolls.push(gameMechanics.getDice().getFace());
			gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() +" rolls " + player1rolls.peekFirst());
		}
		
		gameMechanics.getOutput().updateGameInfoPanel(" ");
		
		//roll defend() dice		
		for(c=0;c<defunits;c++){
			gameMechanics.getDice().roll();
			player2rolls.push(gameMechanics.getDice().getFace());
			gameMechanics.getOutput().updateGameInfoPanel(defendingarmy.getPlayer().getPlayerName() +" rolls " + player2rolls.peekFirst());
		}
		
		//sort lists in order starting with highest
		Collections.sort(player1rolls);
		Collections.sort(player2rolls);
		
		//compare lists
		int matches;
		
		if(player1rolls.size()>player2rolls.size()){
			matches=player2rolls.size();
		}
		else{
			matches=player1rolls.size();
		}
		
		int removep1=0;
		int removep2=0;
		
		for(c=0;c<matches;c++){
			if(player1rolls.get(c)<player2rolls.get(c)){
				removep1++;
			}
			if(player1rolls.get(c)>player2rolls.get(c)){
				removep2++;
			}
		}
		
		//decrease player1 and player2 units accordingly
		attackingarmy.setSize(attackingarmy.getSize()-removep1);
		defendingarmy.setSize(attackingarmy.getSize()-removep2);
		
		gameMechanics.getOutput().updateMapPanel();
		
		//if defender runs out of units attacker gets defender's territory
		//TO DO
	}
	
	public int attack(Player player){
		//user enters attacking country
		boolean owned = false;
		String defending=new String();
		String attacking=new String();
		
		do 
			{
				gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() +", enter country you'd like to attack with!");
				attacking = gameMechanics.getInput().getInputCommand();
				//check if user has that army
				owned=false;
				for(Army a: player.getPlacedArmies()){
					if(a.getCountry().getName().equalsIgnoreCase(attacking)){
						attackingarmy=a;
						owned=true;
					}
				}
				if(owned==false){
						gameMechanics.getOutput().updateGameInfoPanel("You don't own that country!");
				}
			}
		while (owned==false);
		
		//user enters country to target
		boolean exists=false;
		do
			{
				gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + ". enter country you'd like to attack!");
				defending = gameMechanics.getInput().getInputCommand();
				
				owned=false;
				exists=false;
				for(Army a: player.getPlacedArmies()){
					if(a.getCountry().getName().equalsIgnoreCase(defending)){
						attackingarmy=a;
						owned=true;
					}
				}
				
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.equalsIgnoreCase(defending)){
						exists=true;
					}
				}
			}
		while(owned==true || exists==false);	
		
		//get defending army
		int x;
		for(x=0;x<armies.size();x++){
			if(armies.get(x).getCountry().getName().equalsIgnoreCase(defending)){
				defendingarmy=armies.get(x);
			}
		}
		
		//check if countries border
//		if(!countriesBorder(attacking, defending)){
//			gameMechanics.getOutput().updateGameInfoPanel("Countries do not border!");
//			attack(player1,player2);
//		}
		
		//user enters how many units he/she will attack with
		int unitsnum;
		String units;
		do
			{
				do
					{
						gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + ", enter number of units you'd like to attack with.");
						units = gameMechanics.getInput().getInputCommand();
						
						if(!NumberUtils.isNumber(units)){
							gameMechanics.getOutput().updateGameInfoPanel("Please enter a number.");
						}
					}
				while(!NumberUtils.isNumber(units));
				
				unitsnum = Integer.parseInt(units);
				
				if(unitsnum>attackingarmy.getSize() || unitsnum<1){
					gameMechanics.getOutput().updateGameInfoPanel("Please enter suitable number of units to attack with.");
				}
			}
		while(unitsnum>attackingarmy.getSize() || unitsnum<1);
			
		//return number of units as int
		return unitsnum;
	}
	
	public int defend(Army army){
		//user inputs how many units he/she would like to defend with, either 1 or 2
		int unitint;
		if(army.getPlayer().getHuman()==true){
			gameMechanics.getOutput().updateGameInfoPanel(army.getPlayer().getPlayerName() + ", enter number of units you'd like to defend with!");
			String units = gameMechanics.getInput().getInputCommand();
			unitint=Integer.parseInt(units);
		}
		else{
			unitint=(int)(Math.random() * army.getSize())+1;
		}
		//return number of units
		return unitint;
	}
	
	//check if countries boarder each other
	boolean countriesBorder(String country1, String country2){
		int country1position = 0;
		int country2position = 0;
		int p=0;
		boolean pos1 = false;
		boolean pos2 = false;
		
		for(String name:MapConstants.COUNTRY_NAMES){
			if(country1.equalsIgnoreCase(name)){
				country1position=p;
			}
			if(country2.equalsIgnoreCase(name)){
				country2position=p;
			}
			p++;
		}
		
		for(int[] positions: MapConstants.ADJACENT){
			if(ArrayUtils.contains(positions, country1position)){
				pos1=true;
			}
			if(ArrayUtils.contains(positions, country2position)){
				pos2=true;
			}
			if(pos1==true && pos2==true){
				return true;
			}
		} 
		return false;
	}
	
	boolean enoughUnits(int armies,Player player){
		if (player.getAvailableArmies()<armies){
			return false;
		}
		return true;
	}
}
