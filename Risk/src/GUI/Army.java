package GUI;

import Player.Player;

public class Army {
	
	public Army(Integer size, Player player, Country country){
		this.size = size;
		this.player = player;
		this.country = country;
	}
	public void setSize(Integer size){
		this.size = size;
	}
	public Integer getSize(){
		return size;
	}
	public void setPlayer(Player player){
		this.player = player;
	}
	public Player getPlayer(){
		return player;
	}
	public Country getCountry(){
		return country;
	}
	private Integer size;
	private Player player;
	private Country country;
}
