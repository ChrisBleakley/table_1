package Input;

import javax.swing.JTextField;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

public class Input {
	
	// DOES NOTHING AT THE MOMENT!
	
	private JTextField inputField = new JTextField();
	private Stack<String> inputBuffer = new Stack<String>();
	
	Input() {
		
		class AddActionListener implements ActionListener {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				synchronized (inputBuffer) {
					inputBuffer.add(inputField.getText());
					inputField.setText("");
					inputBuffer.notify();
				}
			}
		}


		ActionListener listener = new AddActionListener();
		inputField.addActionListener(listener);
		
		
	}
}
