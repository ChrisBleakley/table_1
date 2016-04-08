package Combat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

import org.apache.commons.lang3.math.NumberUtils;

import GUI.MapConstants;
import Game.Army;
import Game.Country;
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
	}
	
	public void invasion(Player player){
		gameMechanics.getOutput().updateGameInfoPanel("\n<----------Combat--------->\n");
		battle(player);
		
	}
	public void battle(Player player){
		LinkedList<Integer> player1rolls = new LinkedList<Integer>();
		LinkedList<Integer> player2rolls = new LinkedList<Integer>();	
		int attunits = attack(player);
		int defunits = defend(defendingarmy);
		
		//identify number of dice pairs
		int pairs;
		if(attunits>defunits){
			pairs=defunits;
		}
		else{
			pairs=attunits;
		}
		
		System.out.println(pairs);
		
		//roll attacker dice
		int c;
		String command = new String();
		
		do
			{
				gameMechanics.getOutput().updateGameInfoPanel("Enter command 'roll' to begin battle rolls!");
				command = gameMechanics.getInput().getInputCommand();
				if(command.equalsIgnoreCase("roll")){
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
				}
				if(!command.equalsIgnoreCase("roll")){
					gameMechanics.getOutput().updateGameInfoPanel("You need to enter command 'roll'!");
				}
			}
		while(!command.equalsIgnoreCase("roll"));
		
		//sort lists in order starting with highest
		Collections.sort(player1rolls);
		Collections.sort(player2rolls);
		
		//calculate units to be removed
		int removep1=0;
		int removep2=0;
		
		for(c=0;c<pairs;c++){
			if(player1rolls.get(player1rolls.size()-1-c)<player2rolls.get(pairs-1-c)){
				System.out.println("player1: " + player1rolls.get(player1rolls.size()-1-c));
				System.out.println("player2: " + player2rolls.get(pairs-1-c));
				removep1++;
			}
			if(player1rolls.get(player1rolls.size()-1-c)>player2rolls.get(pairs-1-c)){
				System.out.println("player1: " + player1rolls.get(player1rolls.size()-1-c));
				System.out.println("player2: " + player2rolls.get(pairs-1-c));
				removep2++;
			}
		}
		
		System.out.println("player1: " + removep1);
		System.out.println("player2: " + removep2);
		
		//decrease player1 and player2 units accordingly
		attackingarmy.setSize(attackingarmy.getSize()-removep1);
		defendingarmy.setSize(defendingarmy.getSize()-removep2);
		
		gameMechanics.getOutput().updateMapPanel();
		
		//if defender runs out of units attacker gets defender's territory
		if(defendingarmy.getSize()==0){
			Player temp=defendingarmy.getPlayer();
			
			defendingarmy.setSize(player1rolls.size()-removep1);
			defendingarmy.setPlayer(attackingarmy.getPlayer());
			attackingarmy.setSize(attackingarmy.getSize()-player1rolls.size()-removep1);
			
			attackingarmy.getPlayer().getPlacedArmies().add(defendingarmy);
			
			temp.getPlacedArmies().remove(defendingarmy);
		}
		if(attackingarmy.getSize()==0){
			Player temp=attackingarmy.getPlayer();
			attackingarmy.setSize(player2rolls.size()-removep2);
			attackingarmy.setPlayer(defendingarmy.getPlayer());
			defendingarmy.setSize(defendingarmy.getSize()-player2rolls.size()-removep2);
			
			defendingarmy.getPlayer().getPlacedArmies().add(attackingarmy);
			temp.getPlacedArmies().remove(defendingarmy);
		}
	}
	
	public int attack(Player player){
		//user enters attacking country
		boolean owned = false;
		String defending=new String();
		String attacking=new String();
		
		boolean enoughunits = true;
		do 
			{
				gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() +", enter country you'd like to attack with!");
				attacking = gameMechanics.getInput().getInputCommand();
				
				System.out.println(attacking);
				//check if user has that army
				enoughunits=true;
				owned=false;
				System.out.println(attacking);
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.toLowerCase().contains(attacking.toLowerCase())&&attacking.length()>3){
						attacking=s;
						System.out.println(attacking);
					}
				}
				for(Army a: player.getPlacedArmies()){
					if(a.getCountry().getName().equalsIgnoreCase(attacking)){
						attackingarmy=a;
						owned=true;
					}
				}
				
				if(owned==true){
					gameMechanics.getOutput().updateGameInfoPanel("\nYou selected " + attacking.toUpperCase()+ "\n");
					if(attackingarmy.getSize()<2){
						enoughunits=false;
					}
				}
				if(owned==false){
					gameMechanics.getOutput().updateGameInfoPanel("That's not a country you own!");
				}
				if(enoughunits==false && owned==true){
					gameMechanics.getOutput().updateGameInfoPanel("Territory must have at least two units!");
				}
			}
		while (owned==false || enoughunits==false);
				
		//user enters country to target
		boolean exists=false;
		boolean borders=true;
		do
			{
				gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + ". enter country you'd like to attack!");
				defending = gameMechanics.getInput().getInputCommand();
				
				owned=false;
				exists=false;
				borders=true;
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.toLowerCase().contains(defending.toLowerCase())&&defending.length()>3){
						defending=s;
					}
				}
				for(Army a: player.getPlacedArmies()){
					if(a.getCountry().getName().equalsIgnoreCase(defending)){
						//attackingarmy=a;
						owned=true;
					}
				}
				
				if(owned==false){
						gameMechanics.getOutput().updateGameInfoPanel("\nYou selected " + defending.toUpperCase()+ " to attack!"+"\n");
				}
				
				//check if countries border
				if(!countriesBorder(defending)){
					borders=false;
				}
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.equalsIgnoreCase(defending)){
						exists=true;
					}
				}
				if(exists==false){
					gameMechanics.getOutput().updateGameInfoPanel("\nterritory name not recognised!");
					System.out.println("borders: " + borders+ " owned: " + owned + " exists: " + exists);
				}
				if(borders==false && owned==false && exists==true){
					gameMechanics.getOutput().updateGameInfoPanel("\nTarget must be a bordering territory!");
					System.out.println("borders: " + borders+ " owned: " + owned + " exists: " + exists);
				}
				if(owned==true){
					gameMechanics.getOutput().updateGameInfoPanel("\nYou own this territory!");
					System.out.println("borders: " + borders+ " owned: " + owned + " exists: " + exists);
				}
			}
		while(owned==true || exists==false || borders==false);	
		
		//get defending army
		int x;
		for(x=0;x<armies.size();x++){
			if(armies.get(x).getCountry().getName().equalsIgnoreCase(defending)){
				defendingarmy=armies.get(x);
			}
		}
			
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
					gameMechanics.getOutput().updateGameInfoPanel("Number of attacking units must be at least one and no bigger than your army size! (Also three at max!)");
				}
				
				System.out.println("army size: " + attackingarmy.getSize());
				System.out.println("unitsnum: " + unitsnum);
			}
		while(unitsnum>=attackingarmy.getSize() || unitsnum>3 || unitsnum<1);
			
		//return number of units as int
		return unitsnum;
	}
	
	public int defend(Army army){
		//user inputs how many units he/she would like to defend with, either 1 or 2
		int unitsnum;
		String units;
		
		if(army.getPlayer().getHuman()==true){
			do
				{
					do
						{
							gameMechanics.getOutput().updateGameInfoPanel(defendingarmy.getPlayer().getPlayerName() + ", enter number of units you'd like to defend with.");
							units = gameMechanics.getInput().getInputCommand();
							
							if(!NumberUtils.isNumber(units)){
								gameMechanics.getOutput().updateGameInfoPanel("Please enter a number.");
							}
						}
					while(!NumberUtils.isNumber(units));
					
					unitsnum = Integer.parseInt(units);
					
					if(unitsnum>attackingarmy.getSize() || unitsnum<1){
						gameMechanics.getOutput().updateGameInfoPanel("Number of attacking units must be at least one and no bigger than your army size! (Also three at max!)");
					}
				}
			while(unitsnum>=attackingarmy.getSize() || unitsnum>2 || unitsnum<1);
		}
		else{
			unitsnum=(int)(Math.random() * army.getSize())+1;
		}
		//return number of units
		return unitsnum;
	}
	
	//check if countries boarder each other
	boolean countriesBorder(String country){
		for(Country x: attackingarmy.getCountry().getAdjacentCountries()){
			if(country.equalsIgnoreCase(x.getName())){
				System.out.println("bop" + " " +x.getName() + " " + country);
				return true;
			}
		}
		System.out.println("dude" + " " + country);
		return false;
	}
	
}
