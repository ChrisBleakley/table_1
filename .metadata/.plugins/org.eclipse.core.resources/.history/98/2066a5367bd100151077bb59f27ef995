package GUI;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
 
public class CreatePanels extends JFrame {
	public CreatePanels() {
		setTitle("Risk");
		setSize(800, 600);
	
		//create panels
		JPanel game_info_panel= new JPanel();
		JPanel input_panel = new JPanel();
		JPanel map_panel = new JPanel();
		
		//create labels to identify each panel
		JLabel game_info = new JLabel("Game Information", SwingConstants.CENTER);
		JLabel input = new JLabel("User input");
		JLabel map = new JLabel("Map");
		
		//create text field to be added to user input panel.
		JTextField tf = new JTextField();
		tf.setPreferredSize(new Dimension(400,24));
		
		//add the labels to the panels
		game_info_panel.add(game_info);
		input_panel.add(input);
		map_panel.add(map);
		
		//add text field to user input panel.
		input_panel.add(tf);
		
		//create a new panel which consists of panels "game_info_panel" and "input_panel" on top of one another
		JSplitPane bottom_panels = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
		        true, game_info_panel, input_panel);
		        
		//create a new panel which consists of "bottom_panels" beneath "map_panel"
		JSplitPane full_gui= new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
		            true, map_panel, bottom_panels);
		
		//set dimensions for panels
		map_panel.setPreferredSize(new Dimension(800, 400));
		game_info.setPreferredSize(new Dimension(800, 100));
		
		//prevent panels from being resizeable
		bottom_panels.setEnabled(false);
		game_info_panel.setEnabled(false);
		full_gui.setEnabled(false);
	 	
	 	//add gui to jframe
		getContentPane().add(full_gui);
	}
}
