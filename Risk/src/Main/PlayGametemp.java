package Main;

import javax.swing.JFrame;
import java.util.Scanner;

import GUI.Output;
import Listeners.TextActionListener;
import Player.Player;

//import javax.swing.JOptionPane; // Warning :Never used (Remove?)

public class PlayGametemp {
	public static void main(String args[]){ 
		
		Output gui = new Output();
		Player player1 = new Player();
		Player player2 = new Player();
		
		TextActionListener listener = new TextActionListener(gui);
	}
}

