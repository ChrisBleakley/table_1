package GUI;

/*
Team Name: table_1
Student Numbers: 14480278, 14461158, 14745991

A GUI test class
*/

//THIS IS A GUI TEST class. Use Main.PlayGame to play game!
public class Test {
	public static void main(String[] args) {
		Output gui = new Output();	
		int i = 0, j = 1;
		while (i < GUI.MapConstants.COUNTRY_COORD.length){
			gui.setArmies(null, i++, j);
			gui.setArmies(null, i++, j++);
			gui.setArmies(null, i++, 1);
		}
	}
}
