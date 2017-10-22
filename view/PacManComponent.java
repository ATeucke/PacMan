package prog2.project5.view;

import static java.util.concurrent.TimeUnit.SECONDS;
import static prog2.project5.enums.FieldType.*;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.FieldType;
import prog2.project5.enums.GhostCharacter;
import prog2.project5.game.BoardInfo;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GameObserverAdpater;
import prog2.project5.game.GhostInfo;
import prog2.project5.game.PacManGame;

/**
 * A swing component that displays the PacMan game.
 */
@SuppressWarnings("serial")
public class PacManComponent extends JPanel {

	//Movefields
	int startx ;
	int starty ;
	int deathstep;
	int stagestep;
	private static Image cherry; 
	private static Image banana ;
	private static Image orange ;
	private static Image strawberry ;
	private static Image redghostleft;
	private static Image redghostright;
	private static Image redghostup;
	private static Image redghostdown;
	private static Image cyanghostleft;
	private static Image cyanghostright;
	private static Image cyanghostup;
	private static Image cyanghostdown;
	private static Image pinkghostleft;
	private static Image pinkghostright;
	private static Image pinkghostup;
	private static Image pinkghostdown;
	private static Image orangeghostleft;
	private static Image orangeghostright;
	private static Image orangeghostup;
	private static Image orangeghostdown;
	private static Image eatghost ;
	private static Image eatghost2 ;
	private static Image eyesleft ;
	private static Image eyesright ;
	private static Image eyesdown ;
	private static Image eyesup ;
	private static Image df ;
	private static Image schuler ;
	private static Image hack ;
	private static Image pac1 ;
	private static Image pac2;
	private static Image pac3 ;
    private GameInfo gameInfo;
    private boolean blink =false ;
	static final int FIELDSIZE = 20;

	static final Map<FieldType, Color> colorMap = new HashMap<FieldType, Color>();

	static {
		colorMap.put(WALL, Color.BLACK);//was BlUE
		colorMap.put(POWER_PELLET, Color.BLACK);
		colorMap.put(FREE, Color.BLACK);
		colorMap.put(GHOST_START, Color.BLACK);
		colorMap.put(PACMAN_START, Color.BLACK);

	}

	/**
	 * Creates a new PacManComponent for the given game.
	 * 
	 * @param gameInfo
	 *            information about the game.
	 */
	public PacManComponent(final GameInfo gameInfo) {
		this.gameInfo = gameInfo;
		final BoardInfo b = gameInfo.getBoardInfo();
		setPreferredSize(new Dimension(FIELDSIZE * b.getNumberOfColumns(),
				FIELDSIZE * b.getNumberOfRows()));
		  cherry = createImageIcon("CherryBonus.gif", "a Cherry").getImage();
          banana  = createImageIcon("BananaBonus.gif", "a Banana").getImage();
		  orange  =createImageIcon("OrangeBonus.gif", "an Orange").getImage();
		  strawberry  = createImageIcon("StrawberryBonus.gif", "a Strawberry").getImage();
		  redghostleft = createImageIcon("REDleft.GIF", "a redghost").getImage();
		  redghostright = createImageIcon("REDright.gif", "a redghost").getImage();
		  redghostup = createImageIcon("REDup.GIF", "a redghost").getImage();
		  redghostdown = createImageIcon("REDdown.GIF", "a cyanghost").getImage();
		  cyanghostleft = createImageIcon("CYANleft.gif", "a cyanghost").getImage();
		  cyanghostright = createImageIcon("CYANright.GIF", "a cyanghost").getImage();
		  cyanghostup = createImageIcon("CYANup.GIF", "a cyanghost").getImage();
		  cyanghostdown = createImageIcon("CYANdown.GIF", "a cyanghost").getImage();
		  pinkghostleft = createImageIcon("PINKleft.GIF", "a pinkghost").getImage();
		  pinkghostright = createImageIcon("PINKright.GIF", "a pinkghost").getImage();
		  pinkghostup = createImageIcon("PINKup.GIF", "a pinkghost").getImage();
		  pinkghostdown = createImageIcon("PINKdown.gif", "a pinkghost").getImage();
		  orangeghostleft = createImageIcon("YELLOWleft.GIF", "a orangeghost").getImage();
		  orangeghostright = createImageIcon("YELLOWright.GIF", "a orangeghost").getImage();
		  orangeghostup = createImageIcon("YELLOWup.gif", "a orangeghost").getImage();
		  orangeghostdown = createImageIcon("YELLOWdown.GIF", "a orangeghost").getImage();
		  eatghost = createImageIcon("EdibleGhost.gif", "a EdibleGhost").getImage();
		  eatghost2 = createImageIcon("EdibleGhost2.gif", "a EdibleGhost2").getImage();
		  df = createImageIcon("double-facepalm.jpg", "a doublefacepalm").getImage();
		  schuler = createImageIcon("david.jpg", "a schuler").getImage();
		  hack = createImageIcon("hack3.jpg", "a hack").getImage();
	       eyesleft= createImageIcon("EYESleft.gif", "an eye").getImage(); 
			 eyesright = createImageIcon("EYESright.gif", "an eye").getImage();
			 eyesdown = createImageIcon("EYESdown.gif", "an eye").getImage();
			eyesup = createImageIcon("EYESup.gif", "an eye").getImage();
			pac1 = createImageIcon("pac1.jpg", "an eye").getImage();
			pac2 = createImageIcon("pac2.gif", "an eye").getImage();
			pac3 = createImageIcon("p3.jpg", "an eye").getImage();
			
		  gameInfo.addObserver(new GameObserverAdpater(){
			  private  boolean mode ; 
			  //@Override
				public void startPowerPelletMode() {
				    blink=false;
					mode=true;
				}
//				@Override
				public void endPowerPelletMode() {
					blink=false;
					mode=false;
				}
//				@Override
				public void stepDone() {
					if(gameInfo.getpowerduration() < SECONDS.toMillis(5) && mode)
						blink=!blink;
				}
//				@Override
				public void nextStage() {
					blink=false;
					mode=false;
					stagestep=b.getNumberOfRows()+1;
				}
//				@Override
				public void pacManDied() {
					deathstep=17;
				}
		  });
	}
	
	/** Returns an ImageIcon, or null if the path was invalid. */
    protected static ImageIcon createImageIcon(String path,
                                               String description) {
        java.net.URL imgURL = PacManComponent.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL, description);
        } else {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (gameInfo.isGameOver()) {
			paintGameOver(g, gameInfo);
		} else {
			paintBoard(g, gameInfo);
		}
	}
	
	public void newpaint() {
		repaint();
	}
	
	
	/**
	 * Routine that paints the board on given graphics object.
	 * 
	 * @param g
	 *            the graphics object to use for painting.
	 * @param gameInfo
	 *            the game to paint.
	 */
	private void paintBoard(Graphics g, GameInfo gameInfo) {
		BoardInfo boardInfo = gameInfo.getBoardInfo();
		int columns = boardInfo.getNumberOfColumns();
		int rows =boardInfo.getNumberOfRows();
		if(stagestep>0) {
			stagestep--;
			for (int row = 0; row < rows; row++) {
				for (int column = 0; column < columns; column++) {
					g.setColor(Color.BLACK); //FIELD
					g.fillRect(column * FIELDSIZE, row * FIELDSIZE, FIELDSIZE,
							FIELDSIZE); 
				}
			}
		}
		for (int row = 0; row < rows-stagestep; row++) {
			for (int column = 0; column < columns-stagestep; column++) {
				FieldInfo field = boardInfo.getFieldInfo(row, column);
				//g.setColor(Color.BLACK);
				 //FIELD
				g.setColor(colorMap.get(field.getType())); //FIELD
				g.fillRect(column * FIELDSIZE, row * FIELDSIZE, FIELDSIZE,
						FIELDSIZE); 
				g.setColor(Color.RED); 
				if(field.getType()==POWER_PELLET){
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, row*FIELDSIZE+1);
					g.drawLine(column*FIELDSIZE+1, (row+1)*FIELDSIZE-1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, column*FIELDSIZE+1, (row+1)*FIELDSIZE-1);
					g.drawLine((column+1)*FIELDSIZE-1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine((column+1)*FIELDSIZE-1, row*FIELDSIZE+1, column*FIELDSIZE+1, (row+1)*FIELDSIZE-1);
					
				}
				g.setColor(Color.BLUE.brighter().brighter()); 
				if(field.getType()==PACMAN_START){
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, row*FIELDSIZE+1);
					g.drawLine(column*FIELDSIZE+1, (row+1)*FIELDSIZE-1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, column*FIELDSIZE+1, (row+1)*FIELDSIZE-1);
					g.drawLine((column+1)*FIELDSIZE-1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine((column+1)*FIELDSIZE-1, row*FIELDSIZE+1, column*FIELDSIZE+1, (row+1)*FIELDSIZE-1);
					
				}
				g.setColor(Color.MAGENTA.darker().darker()); 
				if(field.getType()==GHOST_START){
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, row*FIELDSIZE+1);
					g.drawLine(column*FIELDSIZE+1, (row+1)*FIELDSIZE-1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, column*FIELDSIZE+1, (row+1)*FIELDSIZE-1);
					g.drawLine((column+1)*FIELDSIZE-1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine(column*FIELDSIZE+1, row*FIELDSIZE+1, (column+1)*FIELDSIZE-1, (row+1)*FIELDSIZE-1);
					g.drawLine((column+1)*FIELDSIZE-1, row*FIELDSIZE+1, column*FIELDSIZE+1, (row+1)*FIELDSIZE-1);
					
				}
				g.setColor(Color.WHITE); 
				
				//Powerpellet
				if (field.hasPowerPellet())
				{
					drawDot(g, row, column, 0.6);
				}
				g.setColor(Color.YELLOW); 
				//PacDot
				if (field.hasPacDot()) 
				{
					drawDot(g, row, column, 0.3);
				}
				//char[] cc =(""+row +","+column).toCharArray();
				//g.setFont(new Font(null, 1,7));
				//g.drawChars(cc, 0, cc.length, column*FIELDSIZE, row*FIELDSIZE+FIELDSIZE);
				//ExtraItem
				if (field.getExtraItem()!= null) 
				{
					switch(field.getExtraItem())
					{
					case CHERRY: g.drawImage(cherry, column*FIELDSIZE, row*FIELDSIZE, FIELDSIZE, FIELDSIZE, new ImageObserver(){

					//	@Override
						public boolean imageUpdate(Image img, int infoflags,
								int x, int y, int width, int height) {return false;}}) ;
						//g.setColor(Color.RED); 
						break; 
					case BANANA:g.drawImage(banana, column*FIELDSIZE, row*FIELDSIZE, FIELDSIZE, FIELDSIZE, new ImageObserver(){

			//			@Override
						public boolean imageUpdate(Image img, int infoflags,
								int x, int y, int width, int height) {return false;}}) ; 
					   //g.setColor(Color.YELLOW); 
					break; 
					case ORANGE: g.drawImage(orange, column*FIELDSIZE, row*FIELDSIZE, FIELDSIZE, FIELDSIZE, new ImageObserver(){

			//			@Override
						public boolean imageUpdate(Image img, int infoflags,
								int x, int y, int width, int height) {return false;}});
					//g.setColor(Color.ORANGE); 
					break; 
					case STRAWBERRY:g.drawImage(strawberry, column*FIELDSIZE, row*FIELDSIZE, FIELDSIZE, FIELDSIZE, new ImageObserver(){

//						@Override
						public boolean imageUpdate(Image img, int infoflags,
								int x, int y, int width, int height) {return false;}}); 
					//g.setColor(Color.PINK); 
					break; 
					}
					//drawDot(g, row, column, 0.5);
				}
			
			}
		}
		
	/*	for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				FieldInfo field = boardInfo.getFieldInfo(row, column);
			    g.setColor(Color.BLUE); 
				if(field.getType()==WALL){
				if(boardInfo.getFieldInfo(row, (column+columns-1)%columns).getType()!=WALL){
					g.drawLine(column*FIELDSIZE, row*FIELDSIZE, column*FIELDSIZE, (row+1)*FIELDSIZE);
					g.drawLine(column*FIELDSIZE+3, row*FIELDSIZE, column*FIELDSIZE+3, (row+1)*FIELDSIZE);
				}
				if(boardInfo.getFieldInfo((row+rows-1)%rows, column).getType()!=WALL){
					g.drawLine(column*FIELDSIZE, row*FIELDSIZE, (column+1)*FIELDSIZE, row*FIELDSIZE);
					g.drawLine(column*FIELDSIZE, row*FIELDSIZE+3, (column+1)*FIELDSIZE, row*FIELDSIZE+3);
				}
				if(boardInfo.getFieldInfo(row, (column+1)%columns).getType()!=WALL){
					g.drawLine((column+1)*FIELDSIZE, row*FIELDSIZE, (column+1)*FIELDSIZE, (row+1)*FIELDSIZE);
					g.drawLine((column+1)*FIELDSIZE-3, row*FIELDSIZE, (column+1)*FIELDSIZE-3, (row+1)*FIELDSIZE);
				}
				if(boardInfo.getFieldInfo((row+1)%rows, column).getType()!=WALL){
					g.drawLine(column*FIELDSIZE, (row+1)*FIELDSIZE, (column+1)*FIELDSIZE, (row+1)*FIELDSIZE);
					g.drawLine(column*FIELDSIZE, (row+1)*FIELDSIZE-3, (column+1)*FIELDSIZE, (row+1)*FIELDSIZE-3);
				}
			}
			if(field.getType()==GHOST_START){
				if(boardInfo.getFieldInfo(row, (column+columns-1)%columns).getType()==FREE){
					g.drawLine(column*FIELDSIZE, row*FIELDSIZE, column*FIELDSIZE, (row+1)*FIELDSIZE);
				}
				if(boardInfo.getFieldInfo((row+rows-1)%rows, column).getType()==FREE){
					g.drawLine(column*FIELDSIZE, row*FIELDSIZE, (column+1)*FIELDSIZE, row*FIELDSIZE);
				}
				if(boardInfo.getFieldInfo(row, (column+1)%columns).getType()==FREE){
					g.drawLine((column+1)*FIELDSIZE, row*FIELDSIZE, (column+1)*FIELDSIZE, (row+1)*FIELDSIZE);
				}
				if(boardInfo.getFieldInfo((row+1)%rows, column).getType()==FREE){
					g.drawLine(column*FIELDSIZE, (row+1)*FIELDSIZE, (column+1)*FIELDSIZE, (row+1)*FIELDSIZE);
				}
			}
			}
			}*/
		for (int row = 0; row < rows; row++) {
			for (int column = 0; column < columns; column++) {
				FieldInfo field = boardInfo.getFieldInfo(row, column);
			    g.setColor(Color.BLUE); 
				if(field.getType()==WALL){
					//links
					if(boardInfo.getFieldInfo(row, (column+columns-1)%columns).getType()!=WALL){
						if(boardInfo.getFieldInfo((row+rows-1)%rows, column).getType()!=WALL){
						g.drawArc(column*FIELDSIZE +3,row*FIELDSIZE +3, 14, 14, 90, 90);
						g.drawArc(column*FIELDSIZE +6,row*FIELDSIZE +6, 14, 14, 90, 90);
						
						}	
						else
						{
							g.drawLine(column*FIELDSIZE+3, row*FIELDSIZE, column*FIELDSIZE+3, (row+1)*FIELDSIZE-10);
							g.drawLine(column*FIELDSIZE+6, row*FIELDSIZE, column*FIELDSIZE+6, (row+1)*FIELDSIZE-10);
						}
					}
					else{ if(boardInfo.getFieldInfo((row+rows-1)%rows, column).getType()!=WALL){
						   g.drawLine(column*FIELDSIZE, row*FIELDSIZE+3, (column+1)*FIELDSIZE-10, row*FIELDSIZE+3);
						   g.drawLine(column*FIELDSIZE, row*FIELDSIZE+6, (column+1)*FIELDSIZE-10, row*FIELDSIZE+6);	
						}
					     else
					     {
					    	 if(boardInfo.getFieldInfo((row+rows-1)%rows, (column+columns-1)%columns).getType()!=WALL){
					    		 g.drawArc(column*FIELDSIZE -3,row*FIELDSIZE -3, 6, 6, 0, -90);
					    		 g.drawArc(column*FIELDSIZE -3,row*FIELDSIZE -3, 9, 9, 10, -110);
							}
					     }	
						}
					//oben
					if(boardInfo.getFieldInfo((row+rows-1)%rows, column).getType()!=WALL){
						if(boardInfo.getFieldInfo(row, (column+1) %columns).getType()!=WALL){
						g.drawArc(column*FIELDSIZE +3,row*FIELDSIZE +3, 14, 14, 0, 90);
						g.drawArc(column*FIELDSIZE +0,row*FIELDSIZE +6, 14, 14, 0, 90);
						}	
						else
						{
							 g.drawLine(column*FIELDSIZE+10, row*FIELDSIZE+3, (column+1)*FIELDSIZE, row*FIELDSIZE+3);
							 g.drawLine(column*FIELDSIZE+10, row*FIELDSIZE+6, (column+1)*FIELDSIZE, row*FIELDSIZE+6);
						}
					}
					else{ if(boardInfo.getFieldInfo(row, (column+1) %columns).getType()!=WALL){
						  g.drawLine((column+1)%columns *FIELDSIZE-3, row*FIELDSIZE, (column+1)%columns *FIELDSIZE-3, (row+1)*FIELDSIZE-10);
						  g.drawLine((column+1)%columns *FIELDSIZE-6, row*FIELDSIZE, (column+1)%columns *FIELDSIZE-6, (row+1)*FIELDSIZE-10);
						}
					     else
					     {
					    	 if(boardInfo.getFieldInfo((row+rows-1)%rows, (column+columns+1)%columns).getType()!=WALL){
					    		 g.drawArc((column+1) %columns*FIELDSIZE -3,row*FIELDSIZE -3, 6, 6, -90, -90);
					    		 g.drawArc((column+1) %columns*FIELDSIZE -6,row*FIELDSIZE -3, 9, 9, -80, -110);
							}
					     }	
						}
					//rechts
					if(boardInfo.getFieldInfo(row, (column+1) %columns).getType()!=WALL){
						if(boardInfo.getFieldInfo((row+1)%rows,column).getType()!=WALL){
						g.drawArc(column*FIELDSIZE +3,row*FIELDSIZE +3, 14, 14, 0, -90);
						g.drawArc(column*FIELDSIZE +0,row*FIELDSIZE +0, 14, 14, 0, -90);
						}	
						else
						{
							 g.drawLine(column*FIELDSIZE+17, row*FIELDSIZE+10, column*FIELDSIZE+17, row*FIELDSIZE+20);
							 g.drawLine(column*FIELDSIZE+14, row*FIELDSIZE+10, column*FIELDSIZE+14, row*FIELDSIZE+20);
						}
					}
					else{ if(boardInfo.getFieldInfo((row+1)%rows,column).getType()!=WALL){
						  g.drawLine(column*FIELDSIZE+10, row*FIELDSIZE+17, column*FIELDSIZE+20, row*FIELDSIZE+17);
						  g.drawLine(column*FIELDSIZE+10, row*FIELDSIZE+14, column*FIELDSIZE+20, row*FIELDSIZE+14);
						}
					     else
					     {
					    	 if(boardInfo.getFieldInfo((row+1)%rows, (column+1)%columns).getType()!=WALL){
					    		 g.drawArc(column*FIELDSIZE +17,row*FIELDSIZE +17, 6, 6, 90, 90);
					    		 g.drawArc(column*FIELDSIZE +14,row*FIELDSIZE +14, 9, 9, 80, 110);
							}
					     }	
						}
					//unten
					if(boardInfo.getFieldInfo((row+1)%rows,column).getType()!=WALL){
						if(boardInfo.getFieldInfo(row, (column+columns-1)%columns).getType()!=WALL){
						g.drawArc(column*FIELDSIZE +3,row*FIELDSIZE +3, 14, 14, -90, -90);
						g.drawArc(column*FIELDSIZE +6,row*FIELDSIZE +0, 14, 14, -90, -90);
						}	
						else
						{
							 g.drawLine(column*FIELDSIZE, row*FIELDSIZE+17, column*FIELDSIZE+10, row*FIELDSIZE+17);
							 g.drawLine(column*FIELDSIZE, row*FIELDSIZE+14, column*FIELDSIZE+10, row*FIELDSIZE+14);
						}
					}
					else{ if(boardInfo.getFieldInfo(row, (column+columns-1)%columns).getType()!=WALL){
						  g.drawLine(column*FIELDSIZE+3, row*FIELDSIZE+10, column*FIELDSIZE+3, row*FIELDSIZE+20);
						  g.drawLine(column*FIELDSIZE+6, row*FIELDSIZE+10, column*FIELDSIZE+6, row*FIELDSIZE+20);
						}
					     else
					     {
					    	 if(boardInfo.getFieldInfo((row+1)%rows, (column+columns-1)%columns).getType()!=WALL){
					    		 g.drawArc(column*FIELDSIZE -3,row*FIELDSIZE +17, 6, 6, 0, 90);
					    		 g.drawArc(column*FIELDSIZE -3,row*FIELDSIZE +14, 9, 9, -10,110 );
							}
					     }	
						}
				}
			}
		}
		
		long duration; 
		long time ;
		int offset;
		Point old;
		//PACMAN
		if(deathstep<=0){
		Point pac = gameInfo.getPacManPosition();
		    g.setColor(Color.YELLOW);
		    old = (Point) gameInfo.getBoardInfo().getFieldInfo(pac).getOldPosition().clone();	
		    duration = gameInfo.getPacManMoveDuration() + 100;//TODO
			time = gameInfo.getPacManMoveTime();
			offset =(int)( (duration *FIELDSIZE)/time);
			//drawActor(g, old.x*FIELDSIZE+ offset*(pac.x-old.x), old.y*FIELDSIZE+ offset*(pac.y-old.y), 0.8);
			if( (pac.y + pac.x) % 2==1)
			drawPacMan(g, old.x*FIELDSIZE+ offset*(pac.x-old.x), old.y*FIELDSIZE+ offset*(pac.y-old.y), gameInfo.getPacManDirection());
			else drawPacMan(g, old.x*FIELDSIZE+ offset*(pac.x-old.x), old.y*FIELDSIZE+ offset*(pac.y-old.y), null);
		}else{
			deathstep--;
			Point deadpos= gameInfo.getdeadManPosition();
			drawPacMandies(g, deadpos.x*FIELDSIZE,deadpos.y*FIELDSIZE, deathstep);
		}
			
			//GHOST
			int ghostcol ;
			for(Point ghost : gameInfo.getGhostPositions()){
			if(gameInfo.isPowerPelletMode() && gameInfo.getBoardInfo().getFieldInfo(ghost).getType()==GHOST_START ) ghostcol =6;
			else if(blink) ghostcol =5;
			else {if(gameInfo.isPowerPelletMode())ghostcol=4;
				else{ FieldInfo finf = gameInfo.getBoardInfo().getFieldInfo(ghost);
					   GhostInfo ginf = finf.getGhostInfo();
					   if(ginf == null) ghostcol = 0;
					   else { GhostCharacter car = ginf.getCharacter();
					   ghostcol=car.ordinal();}}}
			 old = (Point) gameInfo.getBoardInfo().getFieldInfo(ghost).getOldPosition().clone();	
			duration = gameInfo.getGhostMoveDuration()+100;
			time = gameInfo.getGhostMoveTime();
		    offset =(int)( (duration *FIELDSIZE)/time);
			//drawActor(g, old.x*FIELDSIZE+ offset*(ghost.x-old.x), old.y*FIELDSIZE+ offset*(ghost.y-old.y), 0.8);
			Direction gdir = gameInfo.getBoardInfo().getFieldInfo(ghost).getActorDirection();
			gdir = gdir==null? Direction.LEFT : gdir;
		    switch(ghostcol){
			case 0 : switch(gdir){
			case LEFT:g.drawImage(redghostleft, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

	//			@Override
				public boolean imageUpdate(Image img, int infoflags,
						int x, int y, int width, int height) {return false;}}) ;break;
			case RIGHT:g.drawImage(redghostright, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
			case UP:g.drawImage(redghostup, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
			case DOWN:g.drawImage(redghostdown, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
				
			}break;
			case 1 : switch(gdir){
			case LEFT:g.drawImage(pinkghostleft, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
						case RIGHT:g.drawImage(pinkghostright, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case UP:g.drawImage(pinkghostup, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case DOWN:g.drawImage(pinkghostdown, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
							
						}break;
			case 2 : switch(gdir){
			case LEFT:g.drawImage(cyanghostleft, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
						case RIGHT:g.drawImage(cyanghostright, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case UP:g.drawImage(cyanghostup, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case DOWN:g.drawImage(cyanghostdown, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
							
						}break;
			case 3 :switch(gdir){
			case LEFT:g.drawImage(orangeghostleft, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
						case RIGHT:g.drawImage(orangeghostright, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case UP:g.drawImage(orangeghostup, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case DOWN:g.drawImage(orangeghostdown, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
							
						} break;
			case 4 : g.drawImage(eatghost2, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

//				@Override
				public boolean imageUpdate(Image img, int infoflags,
						int x, int y, int width, int height) {return false;}}) ;
				break;
			case 5 : g.drawImage(eatghost, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

//				@Override
				public boolean imageUpdate(Image img, int infoflags,
						int x, int y, int width, int height) {return false;}}) ;
				
			  break;
			case 6 :switch(gdir){
			case LEFT:g.drawImage(eyesleft, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;break;
						case RIGHT:g.drawImage(eyesright, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case UP:g.drawImage(eyesup, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
						case DOWN:g.drawImage(eyesdown, old.y*FIELDSIZE+ offset*(ghost.y-old.y),old.x*FIELDSIZE+ offset*(ghost.x-old.x), FIELDSIZE, FIELDSIZE, new ImageObserver(){

							//			@Override
										public boolean imageUpdate(Image img, int infoflags,
												int x, int y, int width, int height) {return false;}}) ;break;
							
						} break;
			}
			}
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
		g.drawOval((int) (column * FIELDSIZE + (1 - scale) / 2 * FIELDSIZE),
				(int) (row * FIELDSIZE + (1 - scale) / 2 * FIELDSIZE),
				(int) (FIELDSIZE * scale), (int) (FIELDSIZE * scale));
	}
	private static void drawActor(Graphics g, int x, int y, double scale) {
		g.fillOval((int) (y + (1 - scale) / 2 * FIELDSIZE),
				(int) (x + (1 - scale) / 2 * FIELDSIZE),
				(int) (FIELDSIZE * scale), (int) (FIELDSIZE * scale));
	}
	private static void drawPacMan(Graphics g, int a, int b,Direction d) { 
		int f=FIELDSIZE;
		int x=b+f/2;
		int y=a+f/2;
		if(d!=null)
		{switch(d){
		case LEFT://aussen
			g.drawLine(x-6, y-6, x, y-8);
			g.drawLine(x, y-8,x+6, y-6);
			g.drawLine(x+6, y-6, x+8, y);
			g.drawLine(x+8, y, x+6, y+6);
			g.drawLine(x+6, y+6, x, y+8);
			g.drawLine(x, y+8, x-6, y+6);
			//innen
			g.drawLine(x-6, y-6, x, y);
			g.drawLine(x, y-8, x, y);
			g.drawLine(x+6, y-6, x, y);
			g.drawLine(x+8, y, x, y);
			g.drawLine(x+6, y+6, x, y);
			g.drawLine(x, y+8, x, y);
			g.drawLine(x-6, y+6, x, y);
			break;
		case RIGHT://aussen
			g.drawLine(x-6, y-6, x, y-8);
			g.drawLine(x, y-8,x+6, y-6);
			g.drawLine(x+6, y+6, x, y+8);
			g.drawLine(x, y+8, x-6, y+6);
			g.drawLine(x-6, y+6, x-8, y);
			g.drawLine(x-8, y, x-6, y-6);
			//innen
			g.drawLine(x-6, y-6, x, y);
			g.drawLine(x, y-8, x, y);
			g.drawLine(x+6, y-6, x, y);
			g.drawLine(x+6, y+6, x, y);
			g.drawLine(x, y+8, x, y);
			g.drawLine(x-6, y+6, x, y);
			g.drawLine(x-8, y, x, y); break;
		case UP://aussen
			g.drawLine(x+6, y-6, x+8, y);
			g.drawLine(x+8, y, x+6, y+6);
			g.drawLine(x+6, y+6, x, y+8);
			g.drawLine(x, y+8, x-6, y+6);
			g.drawLine(x-6, y+6, x-8, y);
			g.drawLine(x-8, y, x-6, y-6);
			//innen
			g.drawLine(x-6, y-6, x, y);
			g.drawLine(x+6, y-6, x, y);
			g.drawLine(x+8, y, x, y);
			g.drawLine(x+6, y+6, x, y);
			g.drawLine(x, y+8, x, y);
			g.drawLine(x-6, y+6, x, y);
			g.drawLine(x-8, y, x, y); break;
		case DOWN: //aussen
			g.drawLine(x-6, y-6, x, y-8);
			g.drawLine(x, y-8,x+6, y-6);
			g.drawLine(x+6, y-6, x+8, y);
			g.drawLine(x+8, y, x+6, y+6);
			g.drawLine(x-6, y+6, x-8, y);
			g.drawLine(x-8, y, x-6, y-6);
			//innen
			g.drawLine(x-6, y-6, x, y);
			g.drawLine(x, y-8, x, y);
			g.drawLine(x+6, y-6, x, y);
			g.drawLine(x+8, y, x, y);
			g.drawLine(x+6, y+6, x, y);
			g.drawLine(x-6, y+6, x, y);
			g.drawLine(x-8, y, x, y);break;
	    
		}}
		else{
	//aussen
		g.drawLine(x-6, y-6, x, y-8);
		g.drawLine(x, y-8,x+6, y-6);
		g.drawLine(x+6, y-6, x+8, y);
		g.drawLine(x+8, y, x+6, y+6);
		g.drawLine(x+6, y+6, x, y+8);
		g.drawLine(x, y+8, x-6, y+6);
		g.drawLine(x-6, y+6, x-8, y);
		g.drawLine(x-8, y, x-6, y-6);
		//innen
		g.drawLine(x-6, y-6, x, y);
		g.drawLine(x, y-8, x, y);
		g.drawLine(x+6, y-6, x, y);
		g.drawLine(x+8, y, x, y);
		g.drawLine(x+6, y+6, x, y);
		g.drawLine(x, y+8, x, y);
		g.drawLine(x-6, y+6, x, y);
		g.drawLine(x-8, y, x, y);
		}
	}
	private static void drawPacMandies(Graphics g, int a, int b,int death) { 
		g.setColor(Color.YELLOW);
		int f=FIELDSIZE;
		int x=b+f/2;
		int y=a+f/2;
		int d=death/2;
		switch(d){
		case 8:g.drawLine(x-8, y, x-6, y-6);
		case 7:g.drawLine(x-6, y-6, x, y);g.drawLine(x-6, y-6, x, y-8);
		case 6:g.drawLine(x, y-8, x, y);g.drawLine(x, y-8,x+6, y-6);
		case 5:g.drawLine(x+6, y-6, x, y);g.drawLine(x+6, y-6, x+8, y);
		case 4:g.drawLine(x+8, y, x, y);g.drawLine(x+8, y, x+6, y+6);
		case 3:g.drawLine(x+6, y+6, x, y);g.drawLine(x+6, y+6, x, y+8);
		case 2:g.drawLine(x, y+8, x, y);g.drawLine(x, y+8, x-6, y+6);
		case 1:g.drawLine(x-6, y+6, x-8, y);g.drawLine(x-6, y+6, x, y);g.drawLine(x-8, y, x, y);
		case 0: break;
	}
	}
	
	/**
	 * Routine that paints a game over screen on given graphics object.
	 * 
	 * @param g
	 *            the graphics object to use for painting.
	 * @param board
	 *            the board to paint the game over message for.
	 */
	private void paintGameOver(Graphics g, GameInfo gameInfo) {
		g.setColor(Color.BLACK);
		BoardInfo board = gameInfo.getBoardInfo();
		g.fillRect(0, 0, board.getNumberOfColumns() * FIELDSIZE, board
				.getNumberOfRows()
				* FIELDSIZE);
		g.setColor(Color.WHITE);
		g.setFont(new Font(null, 0, 50));
		int x = board.getNumberOfColumns() * FIELDSIZE / 2 - 150;
		int y = 4 * FIELDSIZE ;
		g.drawString("GAME OVER", x, y);
		x += 1*FIELDSIZE;
		y += 3*FIELDSIZE;
		g.setFont(new Font(null, 0, 20));
		String s ="";
		if (gameInfo.getScore()<500) s="AFK";
		if (gameInfo.getScore()>=500 && gameInfo.getScore()<2000) s="Anfaenger";
		if (gameInfo.getScore()>=2000 && gameInfo.getScore()<3000) s="PacMan-Schuler";
		if (gameInfo.getScore()>=3000) s="Hack-er";
		g.drawString("Score: " + gameInfo.getScore() +"      Rang: "+s, x, y);
		x = 2*FIELDSIZE;
		y += 1*FIELDSIZE;
		if(gameInfo.getScore()<500){
		g.drawImage(df, x,y, (board.getNumberOfColumns()-4) * FIELDSIZE,(board.getNumberOfRows()-10) * FIELDSIZE, new ImageObserver(){

			//			@Override
						public boolean imageUpdate(Image img, int infoflags,
								int x, int y, int width, int height) {return false;}}) ;
		}	
		if(gameInfo.getScore()>=500 && gameInfo.getScore()<1000){
			g.drawImage(pac3, x,y, (board.getNumberOfColumns()-4) * FIELDSIZE,(board.getNumberOfRows()-12) * FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;
			}	
		if(gameInfo.getScore()>=1000 && gameInfo.getScore()<1500){
			g.drawImage(pac2, x,y, (board.getNumberOfColumns()-4) * FIELDSIZE,(board.getNumberOfRows()-12) * FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;
			}
		if(gameInfo.getScore()>=1500 && gameInfo.getScore()<2000){
			g.drawImage(pac1, x,y, (board.getNumberOfColumns()-4) * FIELDSIZE,(board.getNumberOfRows()-12) * FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;
			}
		if(gameInfo.getScore()>=2000 && gameInfo.getScore()<3000){
			g.drawImage(schuler, x,y, (board.getNumberOfColumns()-4) * FIELDSIZE,(board.getNumberOfRows()-12) * FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;
			}	
		if(gameInfo.getScore()>=3000){
			g.drawImage(hack, x,y, (board.getNumberOfColumns()-4) * FIELDSIZE,(board.getNumberOfRows()-12) * FIELDSIZE, new ImageObserver(){

				//			@Override
							public boolean imageUpdate(Image img, int infoflags,
									int x, int y, int width, int height) {return false;}}) ;
			}	
	}

}