package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Output;

public class TextActionListener implements ActionListener {
	
	private Output gui;
	
	//method used in order to make JTextField from output class accessible.
	public TextActionListener(Output gui){
		this.gui = gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		//store contents of JTextField to string, print string to console and empty JTextField.
		String text = gui.getTextField().getText();
		gui.updateGameInfoPanel(text);
		gui.addInputToHistory(text);
		gui.printStack();
		gui.getTextField().setText("");
	}
}
