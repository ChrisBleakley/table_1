package GUI;

/*
Team Name: table_1
Student Numbers: 14480278, 14461158, 14745991

The class that draws the Player key
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;

import javax.swing.JComponent;

public class PlayerComponent extends JComponent{
	public PlayerComponent(Dimension panel_size){
		this.panel_size = panel_size;
		this.setPreferredSize(new Dimension((int)panel_size.getWidth()/2, (int)panel_size.getHeight()));
	}
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D gfx2d = (Graphics2D)g;
		gfx2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		this.drawPlayerKey(gfx2d);
	}
	private void drawPlayerKey(Graphics2D gfx2d){
		Integer x = (int)(panel_size.getWidth()/2 - 192);
		Integer ystart = (int)(panel_size.getHeight() * 44 / 100);
		gfx2d.setFont(new Font("Arial", Font.PLAIN, 20));
		int i = 0, y;
		for (String name : MapConstants.PLAYER_NAMES){
			y = ystart + (height * i);
			this.drawNameOutline(gfx2d, name, x, y);
			gfx2d.setPaint(MapConstants.PLAYER_COLORS[i++]);
			gfx2d.drawString(name, x, y);
			this.drawRectangle(gfx2d, x, y);
		}
	}
	private void drawRectangle(Graphics2D gfx2d, int x, int y){
		gfx2d.fill(new Rectangle(x + (int)(64 * MapConstants.SCALING_CONSTANT),
				y - (int)(16 * MapConstants.SCALING_CONSTANT),
				(int)(32 * MapConstants.SCALING_CONSTANT),
				(int)(16 * MapConstants.SCALING_CONSTANT)));
	}
	private void drawNameOutline(Graphics2D gfx2d, String name, Integer x, Integer y){
		gfx2d.setPaint(Color.black);
		gfx2d.drawString(name, x - 1, y - 1);
		gfx2d.drawString(name, x - 1, y + 1);
		gfx2d.drawString(name, x + 1, y - 1);
		gfx2d.drawString(name, x + 1, y + 1);
	}
	private Dimension panel_size;
	private Integer height = 32;
	private static final long serialVersionUID = 1L;
}