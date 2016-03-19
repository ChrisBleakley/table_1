package Game;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
	
	Class stores Army information
*/

public class Army {
	
	private Integer size;
	private Player player;
	private Country country;
	
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
}
