package GUI;

/*
Team Name: table_1
Student Numbers: 14480278, 14461158, 14745991

The class that draws the armies
*/

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class Armies extends JComponent {
	
	public Armies(Dimension panel_size, ArrayList<Army> armies){
		this.panel_size = panel_size;
		this.setPreferredSize(this.panel_size);
		this.armies = armies;
		this.setLayout(new BorderLayout());
		Continents continentkey = new Continents(this.panel_size);
		this.add(continentkey, BorderLayout.WEST);
		PlayerComponent playerkey = new PlayerComponent(this.panel_size);
		this.add(playerkey, BorderLayout.EAST);
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
	private void drawArmies(Graphics2D gfx2d){
		for (Army army : armies){
			if (army.getSize() > 0){
				Integer diameter = army.getCountry().getRadius() +
						((army.getCountry().getRadius() / 32) * army.getSize());
				Integer x = army.getCountry().getXCoords() - (diameter/2);
				Integer y = army.getCountry().getYCoords() - (diameter/2);
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
			}
		}
	}
	private void drawSizeOutline(Graphics2D gfx2d, String name, Integer x, Integer y){
		gfx2d.setPaint(Color.black);
		gfx2d.drawString(name, x - 1, y - 1);
		gfx2d.drawString(name, x - 1, y + 1);
		gfx2d.drawString(name, x + 1, y - 1);
		gfx2d.drawString(name, x + 1, y + 1);
	}
	private ArrayList<Army> armies;
	private Dimension panel_size;
	private static final long serialVersionUID = 1L;
}
