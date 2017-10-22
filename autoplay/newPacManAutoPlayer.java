package prog2.project5.autoplay;
import static java.util.concurrent.TimeUnit.SECONDS;
import static prog2.project5.enums.Direction.DOWN;
import static prog2.project5.enums.Direction.LEFT;
import static prog2.project5.enums.Direction.RIGHT;
import static prog2.project5.enums.Direction.UP;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;

import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.FieldType;
import prog2.project5.enums.GhostCharacter;
import prog2.project5.game.BoardInfo;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GameObserverAdpater;
import prog2.project5.game.GhostInfo;
import prog2.project5.enums.Direction;

/**
 * A PacManAutoplayer can play Pac-Man automatically.
 */
public class newPacManAutoPlayer implements ActorController {

	    private final GameInfo info;
    	private final BoardInfo board;
    	final private int rows;
    	final private int columns;
    	private Graph graph;
	    private boolean extraitem ;
	    private Point extrapos;
	    private int pacMovetime = 250; //TODO soll 250
	    private int stepcount;
	    private int extratime = (int)SECONDS.toMillis(5);
	    private int powertime = (int)SECONDS.toMillis(18);
	    private int ghosttime = 250;
	    //private List<GhostInfo> ghosts ;
	    private int stageCounter=1;
		public int powerpellets;
		public boolean powermode;
    	/**
	 * Creates a new PacManAutoplayer for the given game.
	 * 
	 * @param info
	 *            Information about the current game.
	 */
    public newPacManAutoPlayer(GameInfo info) {
    	this.info = info;
    	this.info.addObserver(new MyGameObserver());
		board = info.getBoardInfo();
		rows = board.getNumberOfRows();
		columns = board.getNumberOfColumns();
		graph = new Graph(board);
		for (Knoten k : graph.getKnoten(info.getPacManPosition()).distance) {
		       if(k.field.hasPowerPellet())  {
		    	   powerpellets++;
		       }
			}
		//TODO Complete implementation
    }
    
	//@Override
	public Direction getMove() {
		
		stepcount++;
		Knoten pk = graph.getKnoten(info.getPacManPosition());
		if(!pk.isfork()) return pk.nextDir(info.getPacManDirection());
		
		//keine Geister?
		if(info.getGhostPositions().isEmpty()) return getPowerMove();
		
		else if (powermode) return  getPowerMove();
		else  return getNormalMove();
	}

	private double getGhostDistance(Knoten k) {	
	if(info.getGhostPositions().isEmpty())return -1.0;
		double dist=rows*columns;
	 for(Point g : info.getGhostPositions())
		{
		 if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) 
			dist=Math.min( Math.sqrt(Math.pow((g.x-k.pos.x),2.0)+Math.pow((g.y-k.pos.y),2.0)), dist);
		}
	    	
		return dist;
	}
	private Direction getNormalMove() {
		Direction move =null;
		Knoten pk = graph.getKnoten(info.getPacManPosition());
	
		/*double dist=0.0;
		for(Direction d : Direction.values())
		{
			if(pk.getKnoten(d)!=null ){
			double ghostd =getGhostDistance(pk.getFork(d));
	        if(ghostd>dist){
	        dist =ghostd;
	        move = d;
	        }
	        }
		}
		if(move!=null) return move; */
		
		
		if(advance(pk,0)< 510){
			// flucht zum pellet,wenn geister zu nah
			int ghostdist;
			int advance=0;
			for (Knoten k : pk.distance) {
			       if(k.field.hasPowerPellet())  {
			    	   ghostdist=Integer.MAX_VALUE;
				       for(Point g : info.getGhostPositions())
				{
					if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) {
					ghostdist = Math.min(getDistance(graph.getKnoten(g),k), ghostdist);	
					}
				}
			    	advance=  Math.max((ghostdist*ghosttime -(getDistance(pk,k))*pacMovetime  ) ,  advance);
			      if(advance >0&& safe(k)) return pk.forkdir.get(k);
			       }
				}
		}
		
		if(extraitem)//Friss ExtraItems in Reichweite,wenn der Weg sicher ist.
		{
			Knoten ek =graph.getKnoten(extrapos);
			if(getDistance(pk, ek)*pacMovetime < extratime ) {
				Direction extradir = getDirection(pk, ek);
				if(safe(pk.getFork(extradir))) return extradir ;
			}
		}
		
	/*	//weg von den Geistern
		int distance =0;
		int dotdistance=rows*columns;
		Direction next=null;
		
		for(Direction d : Direction.values())
		{
			int dotdist=0;
			int ghostdist=rows*columns;
		if (pk.getKnoten(d)!=null && safe(pk.getFork(d))&& !pk.getFork(d).field.hasPowerPellet()){
			 for(Point g : info.getGhostPositions())
				{
				 if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) 
					ghostdist=Math.min(getDistance(graph.getKnoten(g), pk.getKnoten(d)), ghostdist);
				}
			 for(Knoten f : pk.getFork(d).distance)
				{   
					if(safe(f)){
					boolean dot =f.field.hasPacDot();
					if (!dot){
					for(Direction dir : Direction.values()){
						if (f.getKnoten(dir)!=null && f.getKnoten(dir).field.hasPacDot() && !f.getFork(dir).field.hasPowerPellet() && !f.field.hasPowerPellet()) dot=true; 
					}}
					if(dot) dotdist= pk.forkdist.get(f);break;
					}
				}
			 if(pk.getKnoten(d).field.hasPacDot()) dotdist = 0;
			if (distance < ghostdist){
				distance = ghostdist;
				dotdistance = dotdist;
				next = d;
			}
			if(distance == ghostdist){
				if(dotdistance > dotdist){
					distance = ghostdist;
					dotdistance = dotdist;
					next = d;
				}
			}
			
		}
	     }
		if(next!=null) return next;
		
		
		int ghostdist;
		int advance=0;
		for (Knoten k : pk.distance) {
		       if(k.field.hasPowerPellet())  {
		    	   ghostdist=Integer.MAX_VALUE;
			       for(Point g : info.getGhostPositions())
			{
				if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) {
				ghostdist = Math.min(getDistance(graph.getKnoten(g),k), ghostdist);	
				}
			}
		    	advance=  Math.max((ghostdist*ghosttime -(getDistance(pk,k))*pacMovetime  ) ,  advance);
		      if(advance >0) return pk.forkdir.get(k);
		       }
			}
		*/
	     //erst sichere Wege mit dots, aber ohne pellets	
		for(Direction d : Direction.values())
		{
		if (pk.getKnoten(d)!=null && pk.getKnoten(d).field.hasPacDot() && safe(pk.getFork(d))&& !pk.getFork(d).field.hasPowerPellet()) return d; 
		}
		for(Knoten f : pk.distance)
		{   
			if(safe(f)){
			boolean dot =f.field.hasPacDot();
			if (!dot){
			for(Direction d : Direction.values()){
				if (f.getKnoten(d)!=null && f.getKnoten(d).field.hasPacDot() && !f.getFork(d).field.hasPowerPellet() && !f.field.hasPowerPellet()) dot=true; 
			}}
			if(dot) return pk.forkdir.get(f);
			}
		}
		
		//dann sichere Wege mit dots und pellets
		for(Direction d : Direction.values())
		{
		if (pk.getKnoten(d)!=null && pk.getKnoten(d).field.hasPacDot() && safe(pk.getFork(d))) return d; 
		}	
		for(Knoten f : pk.distance)
		{
			if(safe(f)){
			boolean dot =f.field.hasPacDot();
			if (!dot){
			for(Direction d : Direction.values()){
				if (f.getKnoten(d)!=null && f.getKnoten(d).field.hasPacDot() ) dot=true; 
			}}
			if(dot) return pk.forkdir.get(f);
	}} 
	//zuletzt wieder flucht zum powerpellet
		int ghostdist;
		int advance=0;
		for (Knoten k : pk.distance) {
		       if(k.field.hasPowerPellet())  {
		    	   ghostdist=Integer.MAX_VALUE;
			       for(Point g : info.getGhostPositions())
			{
				if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) {
				ghostdist = Math.min(getDistance(graph.getKnoten(g),k), ghostdist);	
				}
			}
		    	advance=  Math.max((ghostdist*ghosttime -(getDistance(pk,k))*pacMovetime  ) ,  advance);
		      if(advance >0) return pk.forkdir.get(k);
		       }
			}
		
		return getPowerMove();
	}

	private int advance(Knoten pac , int weg){
		//if(true) return 1;//TODO test
		if(powerpellets ==0) return 1;
		int ghostdist;
		int advance=0;
		for (Knoten k : pac.distance) {
	       if(k.field.hasPowerPellet())  {
	    	    ghostdist=Integer.MAX_VALUE;
		       for(Point g : info.getGhostPositions())
		{
			if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) {
			ghostdist = Math.min(getDistance(graph.getKnoten(g),k), ghostdist);	
			}
		}
	    	advance=  Math.max((ghostdist*ghosttime -(getDistance(pac,k)+weg)*pacMovetime  ) ,  advance);
	       }
		}
		
		return advance;
		//TODO uberarbeiten mit ghosttime
	}
	private boolean safe(Knoten to){
		Knoten pk = graph.getKnoten(info.getPacManPosition());
		int dist = getDistance(pk, to);
		for(Point g : info.getGhostPositions())
		{
			if(!(board.getFieldInfo(g).getType()==FieldType.GHOST_START)) 
			{if(getDistance(graph.getKnoten(g),to)*ghosttime<= dist*pacMovetime) return false;}
				//Ziel nicht sicher wenn es f�r Geister n�her ist oder kein Vorsprung
		}
		return  advance(to,dist) >= 0;
	}
	
	
	private Direction getPowerMove() {
		Direction move =null;
		Knoten pk = graph.getKnoten(info.getPacManPosition());
		
		//Friss Geister Nachbarfeld 
		for(Direction d : Direction.values())
		{
			if (pk.getKnoten(d)!=null && pk.getKnoten(d).field.getActorType()==ActorType.GHOST) return d; 
		}
		
		//Friss ExtraItems in Reichweite
		if(extraitem)
		{
			Knoten ek =graph.getKnoten(extrapos);
			if(getDistance(pk, ek)*pacMovetime <= extratime  ) {
				return getDirection(pk, ek);
			}
		}
	
		//if(!pk.isfork()) return pk.nextDir(info.getPacManDirection());
		
		for(Knoten f : graph.pellet)
		{
		for(Direction d : Direction.values())
		{
		if (pk.getFork(d)==f && (pk.getKnoten(d).field.hasPacDot()|| pk.getKnoten(d).field.hasPowerPellet()) ) return d; 
		}
	}
		for(Knoten f : graph.pellet)
		{
			boolean dot =f.field.hasPacDot();
			if (!dot){
			for(Direction d : Direction.values()){
				if(f.getKnoten(d)!=null && f.getKnoten(d).field.hasPacDot()&& pk.equals(f) )return d;
				if (f.getKnoten(d)!=null && f.getKnoten(d).field.hasPacDot() ) dot=true; 
			}}
			if(dot) return pk.forkdir.get(f);
	}
	
		
		//keine Powerpellets wenn schon aktiv
		for(Direction d : Direction.values())
			{
				if (pk.getKnoten(d)!=null && pk.getKnoten(d).field.hasPacDot() && !pk.getFork(d).field.hasPowerPellet()) return d; 
			}
			for(Knoten f : pk.distance)
			{
				boolean dot =f.field.hasPacDot();
				if (!dot){
				for(Direction d : Direction.values()){
					if (f.getKnoten(d)!=null && f.getKnoten(d).field.hasPacDot() && !f.getFork(d).field.hasPowerPellet() && !f.field.hasPowerPellet()) dot=true; 
				}}
				if(dot) return pk.forkdir.get(f);
			}
			
			for(Direction d : Direction.values())//ausser wenn sonnst nichts mehr da ist
			{
				if (pk.getKnoten(d)!=null && pk.getKnoten(d).field.hasPacDot() ) return d; 
			}
			for(Knoten f : pk.distance)
			{
				boolean dot =f.field.hasPacDot();
				if (!dot){
				for(Direction d : Direction.values()){
					if (f.getKnoten(d)!=null && f.getKnoten(d).field.hasPacDot() ) dot=true; 
				}}
				if(dot) return pk.forkdir.get(f);
		}
		
		return move;
	}

	public int getDistance(Knoten from,Knoten to){
		if (to.isfork()){
			return from.forkdist.get(to);
		}
		if (from.isfork()){
			return to.forkdist.get(from);
		}
		int dist=0;
		for(Knoten f : to.fork){
			dist = from.forkdist.get(f) + to.forkdist.get(f) < dist ? from.forkdist.get(f) + to.forkdist.get(f): dist;
		}
		return dist;
	}
	
	public Direction getDirection(Knoten from,Knoten to){
		if (to.isfork()){
			return from.forkdir.get(to);
		}

		int dist=0;
		Knoten g =to.fork.get(0);
		for(Knoten f : to.fork){
			if ( from.forkdist.get(f) + to.forkdist.get(f) < dist){
			dist = from.forkdist.get(f) + to.forkdist.get(f);	
			g= f;
			}
		} 
		if(!from.equals(g))return from.forkdir.get(g);
		else {
			for(Knoten f : to.fork){
				if (!from.equals(f)) return  from.forkdir.get(f);
				}
		}
		return null;
	}
	/**
	 * A vertex.
	 */
	 public class Knoten 
	{
		final private Point pos ;
		final private FieldInfo field;
		private  Knoten up ; private  Knoten upfork ; private    int  updist=0 ;
		private  Knoten right ;private  Knoten rightfork ;private    int  rightdist=0 ;
		private  Knoten down ;private  Knoten downfork ;private    int  downdist=0 ;
		private  Knoten left ;private  Knoten leftfork ;private    int  leftdist=0 ;
		private    int  ways=0 ;
		private List<Knoten> fork=new ArrayList<Knoten>()  ;
		private HashMap<Knoten,Direction> forkdir ;
		private HashMap<Knoten,Integer> forkdist ;
		private ArrayList<Knoten> distance;
	
		public Knoten(FieldInfo field, Point point){
			this.field=field;
			this.pos=point;
			forkdir = new HashMap<Knoten,Direction>();
			forkdist = new HashMap<Knoten,Integer>();
		    distance = new ArrayList<Knoten>();
		
		}
       	@Override
		public int hashCode(){
			return pos.x<<16 | pos.y;
		}
		@Override
		public boolean equals(Object obj){
			if(!(obj instanceof Knoten))return false;
			Knoten k =(Knoten) obj;
			return pos.equals(k.pos);
		}
		//TODO forks?
		public boolean isfork(){return ways!=2 || field.getType()==FieldType.POWER_PELLET ;}
		public void setUp(Knoten up) {this.up = up;ways++;}
        public void setRight(Knoten right) {this.right = right;ways++;}
        public void setDown(Knoten down) {this.down = down;ways++;}
		public void setLeft(Knoten left) {this.left = left;ways++;}
		public void setFork(Direction dir,Knoten k){
			switch (dir){
			case UP : upfork = k;forkdir.put(k, UP);break;
			case DOWN:downfork = k;forkdir.put(k, DOWN);break;
			case LEFT:leftfork = k;forkdir.put(k, LEFT);break;
			case RIGHT:rightfork = k;forkdir.put(k, RIGHT);break;
			}
			if(k!=null)fork.add(k);
		}
		public void setDist(Direction dir,int dist){
			switch (dir){
			case UP : updist = dist;forkdist.put(upfork, dist);break;
			case DOWN:downdist = dist;forkdist.put(downfork, dist);break;
			case LEFT:leftdist = dist;forkdist.put(leftfork, dist);break;
			case RIGHT:rightdist = dist;forkdist.put(rightfork, dist);break;
			}
		}
		
		public int getForkDist(Knoten k){
			if (k.equals(upfork)) return updist;
			if (k.equals(downfork)) return downdist;
			if (k.equals(leftfork)) return leftdist;
			if (k.equals(rightfork)) return rightdist;
			return 0;
		}
		public Direction getForkDir(Knoten k){
			if (k.equals(upfork)) return UP;
			if (k.equals(downfork)) return DOWN;
			if (k.equals(leftfork)) return LEFT;
			if (k.equals(rightfork)) return RIGHT;
			return null;
		}
		public Knoten getFork(Direction dir){
			switch (dir){
			case UP : return upfork;
			case DOWN:return downfork;
			case LEFT:return leftfork;
			case RIGHT:return rightfork;
			default: return null;
			}
		}
		public Point getPos() {return pos;}
		public FieldInfo getField() {return field;}
		/**
		 * returns next vertex in given direction.
		 * @param   direction .
     	 */
		public Knoten getKnoten(Direction dir){
			switch (dir){
			case UP : return up;
			case DOWN:return down;
			case LEFT:return left;
			case RIGHT:return right;
			default: return null;
			}
		}
		/**
		 * returns next vertex ,if less than three ways are possible else null.
		 * @param   direction coming from.
     	 */
		public Knoten next(Direction dir){
			if (ways==1) return getKnoten(Direction.values()[((dir.ordinal()+2)%4)]);
			if (ways==2){
				for(Direction d : Direction.values()) {
					if(getKnoten(d)!=null && (d.ordinal()+2)%4!=dir.ordinal())
					{return getKnoten(d);}
				}
			}
			return null;
		}
		/**
		 * returns next direction,if less than three ways are possible else null.
		 * @param   direction coming from.
     	 */
		public Direction nextDir(Direction dir)
		   {
			//System.out.println("ways " +ways);
			if (ways==1) return Direction.values()[((dir.ordinal()+2)%4)];
			if (ways==2){
				for(Direction d : Direction.values()) {
					if(getKnoten(d)!=null && (d.ordinal()+2)%4!=dir.ordinal())
					{return d;}
				}
			}
			return null;
		}
		public String toString(){
			return "("+ pos.x +","+ pos.y +")";
		}
		
	}
	 
	/**
	 * A graph.
	 */
	public class Graph {
		
		private Knoten[][] graph = new Knoten[rows][columns]; 
		private List<Knoten> forks = new ArrayList<Knoten>();
		private List<Knoten> pellet = new ArrayList<Knoten>();
		
		public Graph(BoardInfo boardInfo){
			//ERSTELLE KNOTEN
			for (int row = 0; row <rows; row++) 
			{
				for (int column = 0; column < columns; column++) 
				{
					FieldInfo field = boardInfo.getFieldInfo(row, column);
					switch(field.getType()){
					case FREE:
					case PACMAN_START:	
					case POWER_PELLET: 
						graph[row][column]= new Knoten(field,new Point(row,column));break;
					case	WALL:
					case GHOST_START:	
						graph[row][column] =null ;break;
					}    
				}
			}
			//SETZE NACHBARN
			for (int row = 0; row < rows; row++) 
			{
				for (int column = 0; column < columns; column++) 
				{
					if(graph[row][column]!=null){
					if  (graph[(row+rows-1)%rows ][column]!=null) 
						graph[row][column].setUp(graph[(row+rows-1)%rows ][column]) ;
					if  (graph[row][(column+1)%columns]!=null)
						graph[row][column].setRight(graph[row][(column+1)%columns]) ;
					if  (graph[(row+1)%rows][column]!=null)
						graph[row][column].setDown(graph[(row+1)%rows][column]) ;
					if  (graph[row][(column+columns-1)%columns]!=null)
						graph[row][column].setLeft(graph[row][(column+columns-1)%columns]) ;
					}
				}
			}
			//ERSTELLE GABELUNG LISTE, powerpellet wird mit aufgenommen.
			for (int row = 0; row < rows; row++) 
			{
				for (int column = 0; column < columns; column++) 
				{
					if(graph[row][column]!=null){
				  if(graph[row][column].isfork() ) forks.add(graph[row ][column]);		
				}}
			}
			//Initialisiere Entfernungs und Richtungs Maps
			//System.out.println("Initialisiere Entfernungs und Richtungs Maps");
			for (int row = 0; row < rows; row++) 
			{
				for (int column = 0; column < columns; column++) 
				{
					if(graph[row][column]!=null){
					for(Knoten k : forks){
						graph[row][column].forkdir.put(k, null);
						graph[row][column].forkdist.put(k, rows*columns);
					}}
				}
			}
			//SETZE NAECHSTEGABELUNGEN
			//System.out.println("SETZE NAECHSTEGABELUNGEN");
			for (int row = 0; row < rows; row++) 
			{
				for (int column = 0; column < columns; column++) 
				{
				if(graph[row][column]!=null){
						
						//System.out.println("bearbeite Knoten  "+ row+","+column +graph[row][column].field.getType());
						int dist;
						Knoten k;
						Direction dir;
						Direction dir2;
					for(Direction d : Direction.values())
					{   //System.out.println("beaebeite Richtung"+d);
						dist =1;
					    k=graph[row ][column].getKnoten(d);
					    dir =d;
						if (k!=null)
						{   
							//System.out.println("enter while");
						while(!k.isfork())
						{ //  System.out.println("gehe von knoten  " +k.pos.x+","+k.pos.y);
							dist++;	
							//System.out.println("Entfernung " +dist);
							dir2=k.nextDir(dir);
							//System.out.println("next richtung " +dir);
							k=k.next(dir);	
							dir=dir2;
						}
						
						//System.out.println("leave while");
						graph[row][column].setFork(d,k);
						graph[row][column].setDist(d, dist);
					    } 
						
					} 
					}
				}
			}
			
			
			//Berechne Entfernungs und Richtungs Maps
			//System.out.println("beginne maps berechnung");
			for (int row = 0; row < rows; row++) 
			{
				for (int column = 0; column < columns; column++) 
				{
					if(graph[row][column]!=null){
					//System.out.println("Knoten: "+ row +","+ column+"maps begin");
					putupmaps(graph[row][column]);
					//System.out.println("Knoten: "+ graph[row][column]+"maps fertig");
					//System.out.println(graph[row][column].forkdir.toString());
					for(Knoten f :graph[row][column].distance){
					//System.out.println(f.toString() +" " +graph[row][column].forkdist.get(f));	
					}
					}
				}
			}
		}
		
		//Berechnet fUEr einen Knoten  Entfernungs und Richtungs Maps
		private void putupmaps(Knoten k){
			 LinkedList<Knoten> sforks = new LinkedList<Knoten>();//Liste zu Untersuchender Gabelungen
			 k.forkdist.put(k, 0); //der Knoten selbst hat entfernung 0
			for(Knoten f :k.fork) {sforks.addAll(f.fork); }// Man beginnt die suche mit den benachbarten Gabelungen der nachbarn
			 
					while(!sforks.isEmpty()) // solange die Liste nicht leer ist wird gesucht
					{
						boolean neu=false; // ein testwert ,ob sich etwas geaendert hat
						Knoten f = sforks.poll(); //untersuche das erste element der Liste f
						for(Knoten n : f.fork) // Betrachte dazu alle angrenzend Gabelungen n
						{ 
							if (k.forkdist.get(n)+n.getForkDist(f)<k.forkdist.get(f))//ist der weg zu f ueber n kuerzer als der bereits bekannte?
							{
								neu = true;  // ja er ist kuerzer , es wird geaendert!
								k.forkdist.put(f,k.forkdist.get(n)+n.getForkDist(f)); //die neue entfernung kommt in die entfernungsmap.
								k.forkdir.put(f, k.forkdir.get(n));// der neue Weg fuehrt ueber n ,also muss man auch in die selbe Richtung.
							}
						}
						if(neu) sforks.addAll(f.fork); // wenn etwas geaendert wurde, muessen die NAchbarn von f erneut untersucht werden.
					}
					//erstellt liste aller forks geordnet nach entfernung
					sforks.clear();
					for(Knoten f :k.forkdist.keySet()){
					sforks.add(f);
					}
					while(!sforks.isEmpty())
					{
						Knoten nearest=sforks.peek();
						for(Knoten f : sforks){
							nearest = k.forkdist.get(f) < k.forkdist.get(nearest)? f : nearest;
						}
						k.distance.add(nearest);
						sforks.remove(nearest);
					}
					//erstellt liste aller forks geordnet nach entfernung von den pellets
					this.remapPellets();
		}
		
		public void remapPellets(){
			pellet.clear();
			HashMap<Knoten,Integer> pilldist =new HashMap<Knoten,Integer>();
			LinkedList<Knoten> pills = new LinkedList<Knoten>();
			for (Knoten k : forks) {
			       if(k.field.hasPowerPellet())  {
			    	 pills.add(k);
			       }
				}
			for (Knoten k : pills) {
			      if(pilldist.isEmpty())pilldist.putAll(k.forkdist);
			      else{
			    	  for(Knoten f:k.forkdist.keySet()){
			    		  if(pilldist.get(f)>k.forkdist.get(f)) pilldist.put(f, k.forkdist.get(f));
			    	  }
			      }
				}
			while(!pilldist.isEmpty())
			{
				Knoten farthest=pilldist.keySet().iterator().next();
				for(Knoten f : pilldist.keySet()){
					farthest = pilldist.get(f) > pilldist.get(farthest)? f : farthest;
				}
				pellet.add(farthest);
				pilldist.remove(farthest);
			}
		}
		public Knoten getKnoten(Point p){return graph[p.x][p.y];}
		public Knoten getKnoten(int x,int y){return graph[x][y];}
		
	}
	
	public class MyGameObserver extends GameObserverAdpater {
		
		//@Override
		public void extraItemPlaced(Point p) {
			extraitem=true;
			extrapos = p;
			
		}

//		@Override
		public void extraItemVanished() {
			extraitem=false;
			extrapos=null;
		}
		//@Override
		public void startPowerPelletMode() {
		stepcount=0;
		powermode = true;
			graph.remapPellets();
		}
//		@Override
		public void nextStage() {
			stageCounter++;
			ghosttime =  Math.min(250-(stageCounter * 10 ), 10);
			graph.remapPellets();
		}
//		@Override
		public void stepDone() {
			if(stepcount*pacMovetime > powertime ) powermode = false;
		}
//		@Override
		public void endPowerPelletMode() {
			powerpellets=0;
			for (Knoten k : graph.getKnoten(info.getPacManPosition()).distance) {
			       if(k.field.hasPowerPellet())  {
			    	   powerpellets++;
			       }
				}
		}
	}
}