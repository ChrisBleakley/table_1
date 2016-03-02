package Main;

import java.util.ArrayList;

import javax.swing.JTextField;

import GUI.Output;
import Game.Army;
import Game.Country;
import Input.Input;
import Player.Player;

public interface GameMechanics {
	public JTextField getInputField();
	public void setOutput(Output output);
	public Output getOutput();
	public void setInput(Input input);
	public Input getInput();
	public void setCountryList();
	public ArrayList<Country> getCountryList();
	public void setArmyList(Player player, Country country, Integer armysize);
	public ArrayList<Army> getArmyList();
	public void setPlayerList(ArrayList<Player> playerlist);
	public ArrayList<Player> getPlayerList();
	public void setDeck();
	public void initialiseGameMap();
	public void setReinforceMechanics();
	public void reinforce();
}
