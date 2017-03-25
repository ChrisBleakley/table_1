package GUI;

/*
	Team Name: table_1
	Student Numbers: 14480278, 14461158.
	
	The class that draws the armies
*/

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

import Game.Army;
//import Game.GameMechanics;
import Game.Player;

public class Armies extends JComponent {
	private Output output;
	//private GameMechanics gamemechanics;
	private static final long serialVersionUID = 1L;
	
	public Armies(Output output){
		this.output = output;
		this.setPreferredSize(output.getPanelSize());
		this.setLayout(new BorderLayout());
		output.setContinents(new Continents(output));
		this.add(output.getContinents(), BorderLayout.WEST);
		output.setPlayerKey(new PlayerComponent(output));
		this.add(output.getPlayerKey(), BorderLayout.EAST);
	}
	
	@Override
	public void paintComponent(Graphics g){
		this.drawArmies(this.initialiseGFX2D(g));
	}
	
	private Graphics2D initialiseGFX2D(Graphics g){	
		super.paintComponent(g);
		Graphics2D gfx2d = (Graphics2D)g;	
		gfx2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);	
		return gfx2d;
	}	
	
	private void drawRemainingArmies(Graphics2D gfx2d) {
		int x = (int) (output.getPanelSize().getWidth() / 9);
		int ystart = (int) ((output.getPanelSize().getHeight() / 12) * 9);
		int i = 0, y;
		int remainingFrameWidth = (int) output.getPanelSize().getWidth() / 11;
		int remainingFrameHeight = (int) output.getPanelSize().getHeight() / 5;
		
		gfx2d.setPaint(Color.LIGHT_GRAY);
		gfx2d.fill(new Rectangle(x - 5, ystart - 20, remainingFrameWidth, remainingFrameHeight));
		gfx2d.setPaint(Color.white);
		gfx2d.setStroke(new BasicStroke(2));
		gfx2d.drawRect(x - 5, ystart - 20, remainingFrameWidth, remainingFrameHeight);
		
		for (Player player: output.getPlayerList()) {
			y = ystart + ((int) (20 * MapConstants.SCALING_CONSTANT) * i);
			gfx2d.setPaint(MapConstants.PLAYER_COLORS[i++]);
			gfx2d.setFont(new Font("ARIAL", Font.BOLD, remainingFrameWidth / 10));	
			gfx2d.setStroke(new BasicStroke(2));
			gfx2d.drawString(player.getPlayerName() + " " + player.getAvailableArmies().toString(), x, y);
		}
	}
	
	private void drawArmies(Graphics2D gfx2d) {
		int c = 0;
		for (Army army : output.getArmyList()) {
			if (army.getSize() > 0){
				Integer diameter = army.getCountry().getRadius() +
						((army.getCountry().getRadius() / 32) * army.getSize());
				int x = army.getCountry().getXCoords() - (diameter/2);
				int y = army.getCountry().getYCoords() - (diameter/2);
				Color color = army.getPlayer().getPlayerColour();
				//draw scalable circle for army
				gfx2d.setPaint(color);
				Integer stroke = army.getSize();
				gfx2d.setStroke(new BasicStroke(stroke + 1));
				gfx2d.draw(new Ellipse2D.Double(x, y, diameter, diameter));
				//Draw circle for army
				diameter = army.getCountry().getRadius();
				x = army.getCountry().getXCoords() - (diameter/2);
				y = army.getCountry().getYCoords() - (diameter/2);
				gfx2d.setPaint(color.darker());
				gfx2d.fill(new Ellipse2D.Double(x, y, diameter, diameter));
				//Draw army number
				x = army.getCountry().getXCoords() - (int)(4*MapConstants.SCALING_CONSTANT);
				y = army.getCountry().getYCoords() + (int)(2*MapConstants.SCALING_CONSTANT);
				gfx2d.setFont(army.getCountry().getFont());	
				String size = String.valueOf(army.getSize());
				this.drawSizeOutline(gfx2d, size, x, y);
				gfx2d.setPaint(Color.white);
				gfx2d.drawString(size, x, y);
				
				if(c == 0) {
					this.drawRemainingArmies(gfx2d);
					c++;
				}
			}
		}
	}
	
	private void drawSizeOutline(Graphics2D gfx2d, String name, Integer x, Integer y) {
		gfx2d.setPaint(Color.black);
		gfx2d.drawString(name, x - 1, y - 1);
		gfx2d.drawString(name, x - 1, y + 1);
		gfx2d.drawString(name, x + 1, y - 1);
		gfx2d.drawString(name, x + 1, y + 1);
	}
}

