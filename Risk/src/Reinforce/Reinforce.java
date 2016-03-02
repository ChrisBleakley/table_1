package Reinforce;

import java.util.ArrayList;
import Game.Country;
import Game.GameMechanics;

public class Reinforce implements Main.Reinforce {
	public Reinforce(GameMechanics gamemechanics){
		countrylist = gamemechanics.getCountryList();
	}
	
	public void setReinforcements(String input){
		ArrayList<Country> matches;
		for (Country country : countrylist){
			
		}
	}
	
	private ArrayList<Country> countrylist;
}
