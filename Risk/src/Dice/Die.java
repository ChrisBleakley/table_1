package Dice;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
*/

import Main.Dice;

public class Die implements Dice {
	
	private int diceFace;
	
	public Die(){
		roll();
	}
	
	/* Rolling the dice generates a random number between 1-6. */
	public void roll(){
		diceFace = (int)(Math.random() * 6)+1;
	}
	
	public int getFace(){
		return diceFace;
	}
}
