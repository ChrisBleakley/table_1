package Main;

import javax.swing.JFrame;
import GUI.CreatePanels;
import Player.Player;

public class PlayGame {
	public static void main(String args[]){ 
		
		CreatePanels gui = new CreatePanels();
		Player player1 = new Player();
		Player player2 = new Player();
		
		gui.setResizable(false); //ensures window can't be resized and dimensions aren't ruined.
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //set what happens when "X" is clicked in right top corner.
		gui.setVisible(true); //make gui visible  
	}
}

