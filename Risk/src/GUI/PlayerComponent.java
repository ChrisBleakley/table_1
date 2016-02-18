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
		Integer y = (int)(panel_size.getHeight() * 3 / 5);
		//Hardcoded players so player key is hardcoded for now
		gfx2d.setFont(new Font("Arial", Font.BOLD, 18));
		gfx2d.setPaint(Color.RED);
		gfx2d.drawString("Player 2", x, y - 32);
		this.drawRectangle(gfx2d, x, y - 32);
		gfx2d.setPaint(Color.BLUE);
		gfx2d.drawString("Player 1", x, y - 64);
		this.drawRectangle(gfx2d, x, y - 64);
		gfx2d.setPaint(Color.GRAY);
		gfx2d.drawString("Neutral", x, y - 96);
		this.drawRectangle(gfx2d, x, y - 96);
		
	}
	private void drawRectangle(Graphics2D gfx2d, int x, int y){
		gfx2d.fill(new Rectangle(x + (int)(64 * MapConstants.SCALING_CONSTANT),
				y - (int)(16 * MapConstants.SCALING_CONSTANT),
				(int)(32 * MapConstants.SCALING_CONSTANT),
				(int)(16 * MapConstants.SCALING_CONSTANT)));
	}
	private Dimension panel_size;
	private static final long serialVersionUID = 1L;
}