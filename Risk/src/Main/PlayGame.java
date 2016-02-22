package Main;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
	
	The class from which the game will run.
*/

import java.awt.Color;

import Dice.Die;
import GUI.*;
import Player.Player;

public class PlayGame {
	public static void main(String args[]){ 	
		Output gui = new Output();
		
		Player player1 = new Player();
		Player player2 = new Player();
		Player player3 = new Player();
		Player player4 = new Player();
		Player player5 = new Player();
		Player player6 = new Player();	
		player1.setPlayerName(gui, 1);
		player1.setPlayerColour(Color.BLUE);	
		player2.setPlayerName(gui, 2);
		player2.setPlayerColour(Color.RED);	
		player3.setPlayerColour(Color.GRAY);
		player4.setPlayerColour(Color.GRAY.darker());
		player5.setPlayerColour(Color.GRAY.brighter());
		player6.setPlayerColour(Color.GRAY.darker().darker());
		int i = 0, p1max= 9, p2max = 9, p3max = 6, p4max = 6, p5max = 6, p6max = 6;
		while (i < GUI.MapConstants.COUNTRY_COORD.length){
			if (p1max > 0){
				gui.setArmies(player1, i++, 1);
				p1max--;
			}
			if (p2max > 0){
				gui.setArmies(player2, i++, 1);
				p2max--;
			}
			if (p3max > 0) {
				gui.setArmies(player3, i++, 1);
				p3max--;
			}
			if (p4max > 0) {
				gui.setArmies(player4, i++, 1);
				p4max--;
			}
			if (p5max > 0) {
				gui.setArmies(player5, i++, 1);
				p5max--;
			}
			if (p6max > 0) {
				gui.setArmies(player6, i++, 1);
				p6max--;
			}			
		}	
		gui.updateGameInfoPanel("Welcome to RISK,  " + player1.getPlayerName() + " (Player 1), and " + player2.getPlayerName() + " (Player 2).");
	}
}