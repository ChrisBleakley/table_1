package Main;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
	
	The class from which the game will run.
*/

import java.awt.Color;
import GUI.*;
import Player.Player;

public class PlayGame {
	
	public static void main(String args[]){ 
		
		Output gui = new Output();
		Player player1 = new Player();
		Player player2 = new Player();
		
		player1.setPlayerName(gui);
		player1.setPlayerColour(Color.BLUE);
		
		player2.setPlayerName(gui);
		player2.setPlayerColour(Color.RED);
		
		int i = 0, j = 0;
		while (i < GUI.MapConstants.COUNTRY_COORD.length){
			gui.setArmies(player1, i++, j);
			gui.setArmies(player2, i++, j++);
		}
		
		gui.updateGameInfoPanel("Player 1: " + player1.getPlayerName() + "  Player 2: " + player2.getPlayerName());
		
		// remove before submission
		System.out.println("Player one name: " + player1.getPlayerName());
		System.out.println("Player two name: " + player2.getPlayerName());
		System.out.println("Player one colour: " + player1.getPlayerColour());
		System.out.println("Player two colour: " + player2.getPlayerColour());
	}
}