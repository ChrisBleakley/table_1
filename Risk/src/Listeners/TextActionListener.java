package Listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import GUI.Output;

public class TextActionListener implements ActionListener {
	private Output gui;
	
	//method used in order to make JTextField from output class accessible.
	public TextActionListener(Output gui){
		this.gui=gui;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String text = new String();
		
		//store contents of JTextField to string, print string to console and empty JTextField.
		text=gui.getTextField().getText();
		System.out.println(text);
		gui.getTextField().setText("");
	}
}
