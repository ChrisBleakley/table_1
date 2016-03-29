package Combat;

import java.util.ArrayList;

import org.apache.commons.lang3.math.NumberUtils;

import GUI.MapConstants;
import Game.Army;
import Game.Country;
import Game.GameMechanics;
import Game.Player;

public class Fortify {
	private ArrayList<Player> playerList;
	private GameMechanics gameMechanics = new GameMechanics();
	private ArrayList<Army> armies = new ArrayList<Army>();
	private Army movefrom = null;
	private Army moveto = null;
	
	public Fortify(ArrayList<Player> _playerList, GameMechanics _gameMechanics, ArrayList<Army> armies) {
		this.playerList = _playerList;
		this.gameMechanics = _gameMechanics;
		this.armies=armies;
	}
	
	public void moveUnits(Player player){
		gameMechanics.getOutput().updateGameInfoPanel("\n<----------Fortification--------->\n");
		int unitsmoved=unitsToMove(player);
		
		if(unitsmoved>0){
			movefrom.setSize(movefrom.getSize()-unitsmoved);
			gameMechanics.getOutput().updateGameInfoPanel(movefrom.getCountry().getName()+ "now has " + movefrom.getSize() + " units!");
			moveto.setSize(moveto.getSize()+unitsmoved);
			gameMechanics.getOutput().updateGameInfoPanel(moveto.getCountry().getName()+ "now has " + moveto.getSize() + " units!");
		}
		else{
			gameMechanics.getOutput().updateGameInfoPanel("\nNo bordering countries!\n");
		}
	}
	
	public int unitsToMove(Player player){
		//user enters country to move units from
		boolean owned = false;
		String movetostr=new String();
		String movefromstr=new String();
		
		boolean enoughunits = true;
		do 
			{
				gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() +", enter country you'd like to move units from!");
				movefromstr = gameMechanics.getInput().getInputCommand();
				//check if user has that army
				enoughunits=true;
				owned=false;
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.toLowerCase().contains(movefromstr.toLowerCase())&&movefromstr.length()>3){
						movefromstr=s;
						System.out.println(movefromstr);
					}
				}
				for(Army a: player.getPlacedArmies()){
					if(a.getCountry().getName().equalsIgnoreCase(movefromstr)){
						movefrom=a;
						owned=true;
					}
				}
				
				if(owned==true){
					gameMechanics.getOutput().updateGameInfoPanel("\nYou selected " + movefromstr.toUpperCase()+ "\n");
					if(movefrom.getSize()<2){
						enoughunits=false;
					}
				}
				if(owned==false){
					gameMechanics.getOutput().updateGameInfoPanel("You don't own that country!");
				}
				if(enoughunits==false && owned==true){
					gameMechanics.getOutput().updateGameInfoPanel("Territory must have at least two units!");
				}
				
				System.out.println("owned: " + owned + " enough: " + enoughunits);
			}
		while (owned==false || enoughunits==false);
		
		if(!hasBorder(movefromstr)){
			return 0;
		}
		
		//user enters country to move to
		boolean exists=false;
		boolean borders=true;
		do
			{
				gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + ". enter country you'd like to move units to!");
				movetostr = gameMechanics.getInput().getInputCommand();
				
				owned=false;
				exists=false;
				borders=true;
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.toLowerCase().contains(movetostr.toLowerCase())&&movetostr.length()>3){
						movetostr=s;
					}
				}
				for(Army a: player.getPlacedArmies()){
					if(a.getCountry().getName().equalsIgnoreCase(movetostr)){
						movefrom=a;
						owned=true;
					}
				}
				
				if(owned==false){
						gameMechanics.getOutput().updateGameInfoPanel("\nYou selected " + movetostr.toUpperCase()+"\n");
				}
				//check if countries border
				if(!countriesBorder(movetostr)){
					borders=false;
				}
				for(String s: MapConstants.COUNTRY_NAMES){
					if(s.equalsIgnoreCase(movetostr)){
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
					gameMechanics.getOutput().updateGameInfoPanel("\nYou don't own this territory!");
					System.out.println("borders: " + borders+ " owned: " + owned + " exists: " + exists);
				}
			}
		while(owned==false || exists==false || borders==true);	
		
		//get army units are moved to
		int x;
		for(x=0;x<armies.size();x++){
			if(armies.get(x).getCountry().getName().equalsIgnoreCase(movetostr)){
				moveto=armies.get(x);
			}
		}
			
		//user enters how many units he/she will move
		int unitsnum;
		String units;
		do
			{
				do
					{
						gameMechanics.getOutput().updateGameInfoPanel(player.getPlayerName() + ", enter number of units you'd like to move.");
						units = gameMechanics.getInput().getInputCommand();
						
						if(!NumberUtils.isNumber(units)){
							gameMechanics.getOutput().updateGameInfoPanel("Please enter a number.");
						}
					}
				while(!NumberUtils.isNumber(units));
				
				unitsnum = Integer.parseInt(units);
				
				if(unitsnum>movefrom.getSize() || unitsnum<1){
					gameMechanics.getOutput().updateGameInfoPanel("Number of units moved must be at least one and no bigger than your army size!");
				}
				
				System.out.println("army size: " + movefrom.getSize());
				System.out.println("unitsnum: " + unitsnum);
			}
		while(unitsnum>=movefrom.getSize() || unitsnum<1);
			
		//return number of units as int
		return unitsnum;
	}

	private boolean countriesBorder(String movetostr) {
		for(Country x: movefrom.getCountry().getAdjacentCountries()){
			System.out.println(x.getName());
			System.out.println(movetostr);
			if(movetostr.equalsIgnoreCase(x.getName())){
				System.out.println("bop" + " " +x.getName() + " " + movetostr);
				return true;
			}
		}
		System.out.println("dude" + " " + movetostr);
		return false;
	}
	
	private boolean hasBorder(String movetostr){
		for(Country x:movefrom.getCountry().getAdjacentCountries()){
			for(Army a: movefrom.getPlayer().getPlacedArmies()){
				if(a.getCountry().getName().equalsIgnoreCase(x.getName())){
					return true;
				}
			}
		}
		return false;
	}
}
