package Listeners;

/*
	Make sure that the team names and student numbers are included as comments in the header of all
	source files and the documentation file.
	
	CLASS COMMENT.
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


