package Main;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
	
	The class from which the game will run.
*/

import java.util.ArrayList;
import GUI.MapConstants;
import GUI.Output;
import Input.Input;
import Player.Player;
import Game.GameMechanics;

public class PlayGame {
	public static void main(String args[]){ 
		GameMechanics gamemechanics = new GameMechanics();
		gamemechanics.setInput(new Input(gamemechanics));
		gamemechanics.setOutput(new Output(gamemechanics));
		gamemechanics.setCountryList();
		gamemechanics.setPlayerList(createPlayerList(gamemechanics));
		gamemechanics.setDeck();
		gamemechanics.initialiseGameMap();
		gamemechanics.setReinforceMechanics();
		gamemechanics.reinforce();
	}
	
	private static ArrayList<Player> createPlayerList(GameMechanics game){
		ArrayList<Player> playerlist = new ArrayList<Player>();
		for (int i = 0; i < 6; i++){
			Player player = new Player();
			player.setPlayerName(game, i + 1);
			player.setPlayerColour(MapConstants.PLAYER_COLORS[i]);
			playerlist.add(player);
		}
		return playerlist;
	}
}