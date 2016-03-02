package Main;

import java.awt.Dimension;

import javax.swing.JTextField;

public interface Output {
	public void updateMapPanel();
	public void updateGameInfoPanel(String input);
	public Dimension getPanelSize();
}
