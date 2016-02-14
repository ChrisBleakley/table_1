package Main;

import java.awt.Color;

import GUI.*;
import Player.Player;

public class PlayGame {
	public static void main(String args[]){ 
		
		Output gui = new Output();
		Player player1 = new Player();
		Player player2 = new Player();
		
		//player1.setPlayerName("DerekLad");
		player1.setPlayerName(gui);
		player1.setPlayerColour(Color.BLUE);
		
		player2.setPlayerName("roflcopter");
		player2.setPlayerColour(Color.RED);
		
		int i = 0, j = 0;
		while (i < GUI.MapConstants.COUNTRY_COORD.length){
			gui.setArmies(player1, i++, j);
			gui.setArmies(player2, i++, j++);
		}
		
		//gui.updateGameInfoPanel(player1.getPlayerName());
		
		System.out.println("Player name: " + player1.getPlayerName());
	}
}