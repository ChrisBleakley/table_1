/*
===========================================================================================
||	Project:			Risk Game														||
||	Module:				COMP20050														||
||	Author(s):			Rory Buckley													||
||	Contact:			Rory.Buckley.89@gmail.com										||
||	Contact 2:			14745991@ucdconnect.ie											||
||	Description:		Countries class using JComponent								||
||	Version:			0.1																||
||																						||
===========================================================================================
 */

package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JComponent;

public class Countries extends JComponent{

	public Countries(int radius, Dimension panel_size){
		//Set size
		this.setPreferredSize(panel_size);
		
		//Initialise constant private references
		this.coords = MapConstants.COUNTRY_COORD;
		this.names = MapConstants.COUNTRY_NAMES;
		this.radius = radius;
		this.continents = MapConstants.CONTINENT_IDS;
		this.colors = MapConstants.colors;
		
	}
	@Override
	public void paintComponent(Graphics g){
		
		this.drawCountries(this.initialiseGFX2D(g));
		
	}
	
	private Graphics2D initialiseGFX2D(Graphics g){
		
		super.paintComponent(g);
		
		//Turn on 2d Graphics
		Graphics2D gfx2d = (Graphics2D)g;
		
		//Initialise Anti Aliasing
		gfx2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		return gfx2d;
	}
	
	private void drawCountries(Graphics2D gfx2d){
		
		for (int i = 0; i < coords.length; i++){
			//Initialise Circle coordinates
			int 	x = coords[i][0] - radius,
					y = coords[i][1] - radius;
			
			//Initialise Country Name
			String name = names[i];
			
			//Initialise Color
			int 	continent = continents[i],
					R = colors[continent][0],
					G = colors[continent][1],
					B = colors[continent][2];
			Color c = new Color(R, G, B);
			
			//Draw circles
			Ellipse2D.Double circle = new Ellipse2D.Double(
					x,
					y,
					radius * 2,
					radius * 2);
			
			//Draw Country Names
			gfx2d.setPaint(Color.black);
			gfx2d.drawString(
					name,
					x - 4,
					y - 4);
			
			gfx2d.setPaint(c);
			gfx2d.fill(circle);
		}
	}
	
	private int[][] coords;
	private String[] names;
	private int radius;
	private int[] continents;
	private int[][] colors;
	private static final long serialVersionUID = 1L;
}