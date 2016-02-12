package Main;

import java.awt.Color;

import GUI.*;
import Listeners.TextActionListener;
import Player.Player;

public class PlayGame {
	public static void main(String args[]){ 
		
		Output gui = new Output();
		Player player1 = new Player();
		Player player2 = new Player();
		
		player1.setPlayerName("DerekLad");
		player1.setPlayerColour(Color.BLUE);
		
		gui.updateGameInfoPanel(player1.getPlayerName());
	}
}