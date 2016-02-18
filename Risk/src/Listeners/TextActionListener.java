package Listeners;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158, 14745991
	
	Listener we assign to the JTextField in the GUI.
	When an action event occurs, the actionPerformed method is invoked.
	The action we listen for is the user hitting the ENTER key.
*/

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import GUI.Output;

public class TextActionListener implements ActionListener {
	
	private Output gui;
	
	// This Method is used in order to make JTextField from output class accessible.
	public TextActionListener(Output gui){
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		// If the user hits ENTER and there is content in the TextField,
		// we retrieve this content, storing it in the input command stack.
		if (!gui.getTextField().getText().isEmpty()) {
		
			String text = gui.getTextField().getText();
			gui.addInputToHistory(text);
			gui.getTextField().setText(""); 
		}
	}
	
}
