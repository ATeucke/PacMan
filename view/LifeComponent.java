package prog2.project5.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;

import javax.swing.JPanel;

import prog2.project5.enums.ActorType;
import prog2.project5.game.BoardInfo;
import prog2.project5.game.GameInfo;

public class LifeComponent extends JPanel {

	
	private GameInfo gameInfo;
	final private int FIELDSIZE = 50;

	/**
	 * Creates a new LifeComponent for the given game.
	 * 
	 * @param gameInfo
	 *            information about the game.
	 */
	public LifeComponent(GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		BoardInfo b = gameInfo.getBoardInfo();
		setPreferredSize(new Dimension(FIELDSIZE  * gameInfo.getLives(),
				FIELDSIZE ));
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		
	    
		//Untergrund
	    g.setColor(Color.BLACK);
		g.fillRect(0, 0, FIELDSIZE * 3,FIELDSIZE); 
		
		//PACMAN
		g.setColor(Color.YELLOW);
		for(int l = 0 ; l< gameInfo.getLives(); l++){
			//drawDot(g, 0 ,l, 0.8);
			int x = l*FIELDSIZE +FIELDSIZE/2 ;
			int y = FIELDSIZE/2;
			int r= FIELDSIZE/2-1;
			int d= (int)Math.round(Math.sqrt(((FIELDSIZE/2-1)*(FIELDSIZE/2-1))/2));
			g.drawLine(x-d, y-d, x, y-r);
			g.drawLine(x, y-r,x+d, y-d);
			g.drawLine(x+d, y-d, x+r, y);
			g.drawLine(x+r, y, x+d, y+d);
			g.drawLine(x+d, y+d, x, y+r);
			g.drawLine(x, y+r, x-d, y+d);
			//innen
			g.drawLine(x-d, y-d, x, y);
			g.drawLine(x, y-r, x, y);
			g.drawLine(x+d, y-d, x, y);
			g.drawLine(x+r, y, x, y);
			g.drawLine(x+d, y+d, x, y);
			g.drawLine(x, y+r, x, y);
			g.drawLine(x-d, y+d, x, y);
			} 
		//RIP
		g.setColor(Color.WHITE);
		for(int l = 0  ; l < 3 - gameInfo.getLives(); l++){
			//Laengs
			g.fillRect(FIELDSIZE * 2 + 2*FIELDSIZE/5 - l*FIELDSIZE, 0, FIELDSIZE/5, FIELDSIZE);
			//Quer
			g.fillRect(FIELDSIZE * 2 + FIELDSIZE/5 - l*FIELDSIZE, FIELDSIZE/5, 3*FIELDSIZE/5, FIELDSIZE/5);
			} 
		//RIP
		
		//Mund
		/*g.setColor(Color.BLACK);
		Polygon p = new Polygon(); 
		for(int l = 0 ; l< gameInfo.getLives(); l++){
			p.reset();
				p.addPoint(l * FIELDSIZE, 0);
				p.addPoint(l * FIELDSIZE + FIELDSIZE/2 , FIELDSIZE/2);
				p.addPoint(l * FIELDSIZE , FIELDSIZE);
				g.fillPolygon(p);
				} */
		}
	
	/**
	 * Draws a dot with given scale on given position.
	 * 
	 * @param g
	 *            the graphics object to use for painting.
	 * @param row
	 *            the row of the dot.
	 * @param column
	 *            the column of the dot.
	 * @param scale
	 *            the scale of the dot in the interval [0,1]
	 */
	private void drawDot(Graphics g, int row, int column, double scale) {
		g.fillOval((int) (column * FIELDSIZE + (1 - scale) / 2 * FIELDSIZE),
				(int) (row * FIELDSIZE + (1 - scale) / 2 * FIELDSIZE),
				(int) (FIELDSIZE * scale), (int) (FIELDSIZE * scale));
	}
	
}
