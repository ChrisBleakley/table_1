package Dice;

public class Die {
	private int diceFace;
	
	//constructor
	public Die(){
		roll();
	}
	
	//assign random number between 1 and 6 to diceFace
	public void roll(){
		diceFace = (int)(Math.random() * 6)+1;
	}
	
	//return diceFace
	public int getFace(){
		return diceFace;
	}
}
