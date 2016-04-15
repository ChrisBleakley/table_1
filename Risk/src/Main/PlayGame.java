package Main;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	The class the game runs from.
*/

import java.util.ArrayList;
import GUI.MapConstants;
import GUI.Output;
import Game.GameMechanics;
import Game.Player;
import Input.Input;

public class PlayGame {
	
	public static void main(String args[]) { 
		
		GameMechanics gamemechanics = new GameMechanics();
		
		gamemechanics.setInput(new Input(gamemechanics));
		gamemechanics.setOutput(new Output(gamemechanics));
		gamemechanics.setCountryList();
		gamemechanics.setPlayerList(createPlayerList(gamemechanics));
		gamemechanics.setDeck();
		gamemechanics.setDice();
		gamemechanics.initialiseGameMap();
		gamemechanics.setReinforceMechanics();
		gamemechanics.reinforce();
		gamemechanics.turns();
		
	}
	
	/* Create the player objects for each player in the game. */
	private static ArrayList<Player> createPlayerList(GameMechanics game){
		ArrayList<Player> playerlist = new ArrayList<Player>();
		
		for (int i = 1; i <= 6; i++){
			
			Player player = new Player(game, (i == 1 || i == 2));
			
			player.setPlayerName(i);		
			
			player.setPlayerColour(MapConstants.PLAYER_COLORS[i - 1]);
			playerlist.add(player);
		}
		
		return playerlist;
	}
}