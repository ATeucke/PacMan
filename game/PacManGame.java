package prog2.project5.game;

import static java.util.concurrent.TimeUnit.*;
import static prog2.project5.enums.ActorType.*;
import static prog2.project5.enums.Direction.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import prog2.project5.autoplay.ControllerFactory;
import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.Event;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.GhostCharacter;

/**
 * This class represents the PacManGame.
 * 
 */ 
public class PacManGame implements GameObservable {

    /**
	 * The default seed for the random number generator.
	 */
    private static final long DEFAULT_SEED = 3426519374L;

    /**
	 * Default amount of Pac-Man lives on start.
	 */
    private static final int PACMAN_LIVES = 3;

    /**
	 * Time (in milliseconds) the power pellet mode will be active.
	 */
    private long powerPelletTime = SECONDS.toMillis(20);

    /**
	 * Time (in milliseconds) an extra item will be on the board.
	 */
    private long extraItemTime = SECONDS.toMillis(5);

    /**
	 * Time (in milliseconds) between the appearance of new extra items.
	 */
    private long newExtraItemTime = SECONDS.toMillis(20);

    /**
	 * Time (in milliseconds) a ghost needs for one move.
	 */
    private long ghostMoveTime = 250;

    /**
	 * Time (in milliseconds) Pac-Man needs for one move.
	 */
    private long pacManMoveTime = 250;            //TODO was 250

    /**
	 * Counts the number of milliseconds an extra item will be on the board. If
	 * it is <= 0 no extra item will be on the board.
	 */
    private long extraItemDuration = 0;

    /**
	 * Counts the time since the last placement of an extra item.
	 */
    private long newExtraItemDuration;

    /**
	 * Counts the time Pac-Man will still be in power pellet mode. If it is <= 0
	 * the power pellet mode is over.
	 */
    private long powerPelletDuration = powerPelletTime;

    /**
	 * Counts the time until the next move for the ghosts.
	 */
    private long ghostMoveDuration = 0;

    /**
	 * Counts the time until the next move for Pac-Man.
	 */
    private long pacManMoveDuration = 0;

    /**
	 * The board of this game.
	 */
    private Board board;

    /**
	 * True, if the game is in power pellet mode. E.g. Pac-Man can eat ghosts.
	 */
    private boolean powerPelletMode;

    /**
	 * True, if the game is over.
	 */
    private boolean gameOver;

    /**
	 * The points reached in the current game.
	 */
    private long score;

    /**
	 * The remaining lives of Pac-Man.
	 */
    private int lives;

    /**
	 * Indicates the current stage. The first stage has the number 1.
	 */
    private int stageCounter = 0;

    /**
	 * Counts the eaten pac-dots.
	 */
    private int pacDotCounter;

    /**
	 * The list of the observers.
	 */
    private List<GameObserver> observers;

    /**
	 * The list of the ghost.
	 */
    private List<Ghost> ghosts;

    /**
	 * Pac-Man.
	 */
    private PacMan pacMan;
    
    /**
	 * The position of the current extraItem or null when no extra item is in
	 * the game.
	 */
    private Point extraItemPosition;

    /**
	 * The random generator of the game.
	 */
    private Random random;

    /**
	 * The last time the step() method was invoked (as measured by
	 * {@link System.currentTimeMillis})
	 */
    private long lastStepInvocation;
    private Point deathpos;
    /**
	 * Creates a new PacManGame and initializes the game. This consists of the
	 * following steps:<br/>
	 * Initialize the random generator with the default seed. <br/>
	 * Initialize Pac-Man with the given {@link ControllerFactory} <br/>
	 * Initialize the Ghosts with the given {@link ControllerFactory} <br/>
	 * Calls nextLife() <br/>
	 * Sets the stageCounter to 1. <br/>
	 * 
	 * @param board
	 *            The board for the game.
	 * @param controllerFactory
	 *            Controller factory to get controllers for Pac-Man and the
	 *            ghosts.
	 * @throws IllegalArgumentException
	 *             if the given board or controlerFactory is null.
	 */
    public PacManGame(Board board, ControllerFactory controllerFactory) {
    	
       if (board==null) throw new IllegalArgumentException("given board is null");
    	this.board=board;
    	board.initBoard();
    	observers = new LinkedList<GameObserver>();
    	if (controllerFactory==null) throw new IllegalArgumentException("given controlerFactory is null");
    	this.random = new Random(DEFAULT_SEED); 
    	this.pacMan = new PacMan(board.getPacManStart()); 
    	//board.getField(board.getPacManStart().x,board.getPacManStart().y).placeActor(pacMan);
    	pacMan.initController(controllerFactory.getPacManController(getGameInfo()));
    	this.ghosts = new LinkedList<Ghost>();
    	Ghost ghost ;
    	int e =0;
    	for (Point g : board.getGhostsStart()){
    		ghost = new Ghost(g,GhostCharacter.getCharacter(e));
    		//board.getField(g.x, g.y).placeActor(ghost);
    		ghost.initController(controllerFactory.getGhostController(getGameInfo() , ghost.getGhostInfo()));
    		ghosts.add(ghost);
    		e++;
    	}
    	this.stageCounter=1;
    	score = 0;
    	this.lives=PACMAN_LIVES;
    	//System.out.print(board.getPacDotsOnStart());
    	nextLife();
    	
    }

    /**
	 * 
	 * Creates a new PacManGame and initializes the game. This consists of the
	 * following steps:<br/>
	 * Initialize the random generator with the given seed. <br/>
	 * Initialize Pac-Man with the given {@link ControllerFactory} <br/>
	 * Initialize the Ghosts with the given {@link ControllerFactory} <br/>
	 * Calls nextLife() <br/>
	 * Sets the stageCounter to 1. <br/>
	 * 
	 * @param board
	 *            The board for the game.
	 * @param seed
	 *            the seed for the random generator.
	 * @param controllerFactory
	 *            Controller factory to get controllers for Pac-Man and the
	 *            ghosts.
	 * @throws IllegalArgumentException
	 *             if the given board or controlerFactory is null.
	 */
    public PacManGame(Board board, long seed, ControllerFactory controllerFactory) {
    	if (board==null) throw new IllegalArgumentException("given board is null");
    	this.board=board;
    	board.initBoard();
    	observers = new LinkedList<GameObserver>();
    	if (controllerFactory==null) throw new IllegalArgumentException("given controlerFactory is null");
    	this.random = new Random(seed);
    	this.pacMan =new PacMan(board.getPacManStart()); 
    	board.getField(board.getPacManStart().x,board.getPacManStart().y).placeActor(pacMan);
    	pacMan.initController(controllerFactory.getPacManController(getGameInfo()));
    	this.ghosts= new LinkedList<Ghost>();
    	for (Point g : board.getGhostsStart()){
    		Ghost ghost = new Ghost(g,GhostCharacter.OIKAKE);
    		board.getField(g.x, g.y).placeActor(ghost);
    		ghost.initController(controllerFactory.getGhostController(getGameInfo() , board.getField(g.x, g.y).getFieldInfo().getGhostInfo()));
    		ghosts.add(ghost);
    	}
    	this.stageCounter=1;
    	score = 0;
    	this.lives= PACMAN_LIVES;
    	nextLife();
    	
    }
    
    /** 
     * Carries out one step in the game. A detailed documentation of a step can
 	 * be found in the project documentation.
 	 * 
+ 	 * This calculates the time since the last invocation and calls the
+ 	 * step(duration) method using the time as argument.
+ 	 * 
 	 */
     public synchronized void step() {
         long duration = 1;
         if (lastStepInvocation != 0) {
             duration = System.currentTimeMillis() - lastStepInvocation;
         }
         lastStepInvocation = System.currentTimeMillis();
         step(duration);
     }
     
     
    /**
    + 	 * Carries out one step in the game. A detailed documentation of a step can
    + 	 * be found in the project documentation.
    + 	 * 
    + 	 * @param duration
    + 	 *            The time (in milliseconds) since the last invocation.
    + 	 */
    public synchronized void step(long duration) {
    	//System.out.println("Beginnstep");
    	//1.time
    	long time = duration;
    	
    	/* old
    	long now = System.currentTimeMillis();
    	System.out.println("now:" +now);
    	System.out.println("last:" + lastStepInvocation );
    	long time = (lastStepInvocation==0)? 1 : now - lastStepInvocation;
    	this.lastStepInvocation= now;
    	*/
    	//2.PowerPellet
    	if(this.powerPelletMode) {
    		this.powerPelletDuration -= time;
    		if (powerPelletDuration <= 0)  {
    			powerPelletMode= false;
    			powerPelletDuration=0;
    			this.notifyObserversEndPowerPelletMode();
    		}
    	}
    	//3.a)extraItem
    	if(this.extraItemDuration > 0) {
    		extraItemDuration -= time;
    		if (extraItemDuration <=0) {
    			Point pos= this.extraItemPosition;
    			board.getField(pos.x,pos.y).setExtraItem(null);
    			this.notifyObserversExtraItemVanished();
    			this.extraItemPosition = null;
    			extraItemDuration=0;
    		}
    	}
    	//3.a)new extraItem
    	this.newExtraItemDuration += time;
    	if(newExtraItemDuration >= this.newExtraItemTime){
           //sollte aus irgendeinem grund noch ein extraitem da sein, wird dieses entfernt
    		if(this.extraItemPosition!=null){
    		Point pos= this.extraItemPosition;
			board.getField(pos.x,pos.y).setExtraItem(null);
			this.notifyObserversExtraItemVanished();
			this.extraItemPosition = null;}
			//neues extraitem
    		this.extraItemDuration= this.extraItemTime;
    		this.placeExtraItem();
    		newExtraItemDuration -= newExtraItemTime;
    		this.notifyObserversExtraItemPlaced(this.extraItemPosition);
    	}
    	//4.pacman move
    	this.pacManMoveDuration += time;
    	//while(pacManMoveDuration>= this.pacManMoveTime){
    		if(pacManMoveDuration>= this.pacManMoveTime){
    		pacManMoveDuration -= pacManMoveTime;
    		Direction move = pacMan.getMove();
    		pacMan.setOldPosition(pacMan.getPosition());
    		//System.out.println("pacManmove:" + move );
    		 if(move(pacMan,move)){
    			 pacMan.setDirection(move);
    			 this.notifyObserversActorSet(PACMAN, pacMan.getPosition().x,pacMan.getPosition().y);
    		 }
    		 else if(move(pacMan,pacMan.getDirection())) this.notifyObserversActorSet(PACMAN, pacMan.getPosition().x,pacMan.getPosition().y);
    		 
    	}
    	
    	//5. GHOST move
    	this.ghostMoveDuration += time;
    	while(ghostMoveDuration>= this.ghostMoveTime){
    		//if(ghostMoveDuration>= this.ghostMoveTime){
    		ghostMoveDuration -= ghostMoveTime;
    		for(Ghost g : ghosts){
    			g.setOldPosition(g.getPosition());
    		Direction move = g.getMove();
    		 if(move(g,move)){
    		 this.notifyObserversActorSet(GHOST, g.getPosition().x,g.getPosition().y);
    		 g.setDirection(move);
    		 }
    		}
    	}
    	
    	//6. Pac-dots
    	if(this.pacDotCounter == board.getPacDotsOnStart()){
    		nextStage();
    		//System.out.println("Stage:" + this.stageCounter );
    		this.notifyObserversNextStage();
    	}
    	//7. observer
    	this.notifyObserversStepDone();
    	
    	//Ausgaben
    	/*
    	System.out.println("time:" + time );
    	System.out.println("pacManposition:" + pacMan.toString() );
    	System.out.println("power:" + isPowerPelletMode() );
        Point pos = this.extraItemPosition;
        String extra;
        if(pos ==null) extra = "nichts";
        else extra = board.getField(pos.x, pos.y).getExtraItem()==null ?"nichts" : board.getField(pos.x, pos.y).getExtraItem().name();
    	System.out.println("ExtraItem:" +extra +" an pos: "+ pos );
    	System.out.println("Score:" + score );
    	System.out.println("EndStep" );
    	System.out.println(); */
        //idle
    }

    /**
	 * This method should be called if Pac-Man dies. If at least one life is
	 * left, it prepares the game for the next cycle, i.e places Pac-Man and the
	 * ghosts on their start fields and updates the counters. Otherwise game has
	 * to be set game over. Furthermore, old actors have to be removed and if
	 * there is an extraItem, it has to be removed, too.
	 */
    private void nextLife() {
    	if(this.lives==0) {
    		setGameOver(); 
    		this.notifyObserversGameOver();
    		return;
    	}
    	else{
    		//EXTRAITEM
    		if(this.extraItemDuration > 0) {
        			Point pos= this.extraItemPosition;
        			board.getField(pos.x,pos.y).setExtraItem(null);
        			this.notifyObserversExtraItemVanished();
        			this.extraItemPosition = null;
        			extraItemDuration=0;	
        	}
    		//GHOSTS remove
    		Point pos;
    		for (Ghost g:ghosts) {
    			pos = g.getPosition();
        		board.getField(pos.x, pos.y).removeActor();
        		this.notifyObserversActorRemoved(g.getType(), pos.x, pos.y);
        	}
    		//PACMAN
    	    pos = pacMan.getPosition();
    		board.getField(pos.x, pos.y).removeActor();
    		this.notifyObserversActorRemoved(pacMan.getType(), pos.x, pos.y);
    		pos= board.getPacManStart();
	    	board.getField(pos.x,pos.y).placeActor(pacMan);
	    	pacMan.setOldPosition(pos);
	    	pacMan.setPosition(pos);
	    	pacMan.setDirection(LEFT);
	    	this.notifyObserversActorSet(PACMAN,pos.x,pos.y);
	    	//Ghost set
    		Iterator<Ghost> iterg = ghosts.iterator();
    		Ghost ghost;
    		for (Point g : board.getGhostsStart()){
    			ghost = iterg.next();
        		board.getField(g.x, g.y).placeActor(ghost);
        		ghost.setPosition(g.x, g.y);
        		ghost.setOldPosition(g);
        		this.notifyObserversActorRemoved(GHOST, g.x, g.y);
        	}
    		//COUNTER
    		this.newExtraItemDuration=0;
    		this.ghostMoveDuration=0;
    		this.pacManMoveDuration=0;
    		this.lastStepInvocation=0;
    	}
        return ;
    }

    /**
	 * If there are no more pac-dots on the board, the stage is done. This
	 * method starts a new stage, i.e the board has to be reset, the counters
	 * have to be updated and the observers must be informed. The moveTime of
	 * the ghosts is updated according to the following formula:<br/>
	 * max(250 - (stageCounter * 10), 10)
	 */
    private void nextStage() {
    	board.initBoard();
    	Point start =(Point) board.getPacManStart().clone();
    	board.getField(start.x,start.y).placeActor(pacMan);
    	pacMan.setPosition(start);
    	pacMan.setOldPosition(start);
    	pacMan.setDirection(LEFT);
    	//this.notifyObserversActorSet(pacMan.getType(),start.x,start.y);
    	Iterator<Ghost> iterg = ghosts.iterator();
    	Ghost ghost ;
		for (Point g : board.getGhostsStart()){
			ghost = iterg.next();
    		board.getField(g.x, g.y).placeActor(ghost);
    		ghost.setPosition(g);
    		ghost.setOldPosition(g);
    		//this.notifyObserversActorSet(ghost.getType(),g.x,g.y);
    	}
		this.powerPelletMode=false;
		this.extraItemPosition=null;
		this.extraItemDuration=0;
    	this.newExtraItemDuration=0;
		this.ghostMoveDuration=0;
		this.pacManMoveDuration=0;
		extraItemDuration=0;
		this.powerPelletDuration =0;
		this.stageCounter++;
		this.pacDotCounter=0;
		this.ghostMoveTime = Math.max(250-(stageCounter * 10 ), 10);
		score = score + 100 * lives;
		this.lastStepInvocation=0;
		return ;
    }

    /**
	 * This method chooses the coordinates for a new extra item. The extra item
	 * is placed randomly on one of the fields as returned by {@link
	 * Board.getExtraItemFields()}.
	 */
    private void placeExtraItem() { 
    	Random rand = new Random(System.currentTimeMillis());
        int i = Math.abs(rand.nextInt()) % board.getExtraItemFields().size();
        Point p = (Point) board.getExtraItemFields().get(i).clone();
        while(pacMan.getPosition().equals(p)){
        	i = Math.abs(rand.nextInt()) % board.getExtraItemFields().size();
        	p= (Point) board.getExtraItemFields().get(i).clone(); 
        }
         int x = p.x; int y = p.y;
        int item = Math.abs(rand.nextInt()) % 4;
        ExtraItem extraItem;
        switch (item) {
        case 0 : extraItem = ExtraItem.CHERRY; break;
        case 1 : extraItem = ExtraItem.BANANA; break;
        case 2 : extraItem = ExtraItem.ORANGE; break;
        case 3 : extraItem = ExtraItem.STRAWBERRY; break;
        default : extraItem = null; break;
        }
        board.getField(x, y).setExtraItem(extraItem);
        this.extraItemPosition=(Point) p.clone();
    	return ; 
    }

    /**
	 * This method tries to move the given actor to the field specified by the
	 * given coordinates. If an enemy is on the target field and not
	 * {@link PacManGame#isPowerPelletMode()} Pac-Man dies immediately -
	 * otherwise the ghost dies.
	 * 
	 * @param actor
	 *            The given actor to be moved.
	 * @param newXPosition
	 *            The new x-Position, the actor has to be moved to.
	 * @param newYPosition
	 *            The new y-Position, the actor has to be moved to.
	 * 
	 * @return True, if the move could be made (the move was not impossible and
	 *         pac-man did not die), and false else.
	 */
    private boolean turn(Actor actor, int newXPosition, int newYPosition) {
    	//urzustand feld
    	boolean dot = board.getField(newXPosition,newYPosition).hasPacDot();
    	boolean pellet = board.getField(newXPosition,newYPosition).hasPowerPellet();
    	ExtraItem extra = board.getField(newXPosition,newYPosition).getExtraItem();
    	//Move
    	switch(board.getField(newXPosition,newYPosition).checkActor(actor)){
    	case MOVE_IMPOSSIBLE: return false; 
    	case MOVE_POSSIBLE: Point apos = actor.getPosition();
    	    board.getField(apos.x,apos.y).removeActor();
    		this.notifyObserversActorRemoved(actor.getType(), apos.x, apos.y);
    		actor.setPosition(newXPosition,newYPosition);
    		board.getField(newXPosition,newYPosition).placeActor(actor); 
    		break;
    	case PACMAN_GHOST_COLLISION: Actor dead = checkActor(board.getField(newXPosition,newYPosition).getActor(),actor);
    	     if (dead instanceof PacMan) {
    	    	 lives--; pacManMoveDuration=0;deathpos=actor.getPosition(); this.notifyObserversPacManDied();  nextLife(); return false;
    	     } else {
    	    	 //place dead Ghost
    	    	 Point dpos = dead.getPosition();
    	    	 board.getField(dpos.x,dpos.y).removeActor();
    	    	 this.notifyObserversActorRemoved(dead.getType(), dpos.x, dpos.y);
    	    	 Iterator<Point> iterg = board.getGhostsStart().iterator();
    	    	 Point gstart = iterg.next();
    	    	 while(board.getField(gstart.x,gstart.y).getActor()!= null) { gstart = iterg.next(); }
    	    	 dead.setPosition(gstart.x,gstart.y);
    	    	 dead.setOldPosition(gstart);
 	    		 board.getField(gstart.x,gstart.y).placeActor(dead);
 	    		this.notifyObserversActorSet(dead.getType(), gstart.x,gstart.y);
    	    	 //place Pacman
 	    		 Point ppos = pacMan.getPosition();
    	    		board.getField(ppos.x,ppos.y).removeActor();
    	    		pacMan.setOldPosition(ppos);
    	    		this.notifyObserversActorRemoved(pacMan.getType(), ppos.x, ppos.y);
       	    	    actor.setPosition(newXPosition,newYPosition);
    	    		board.getField(newXPosition,newYPosition).placeActor(pacMan);
    	    		this.notifyObserversActorSet(pacMan.getType(),newXPosition,newYPosition);
    	    	//points for dead Ghost
    	    	score = score + 25 * stageCounter;	
    	    	break;
    	     }
    	}
    	//Punkteabrechnung
    	if(board.getField(newXPosition,newYPosition).getActor() instanceof PacMan){
    	if(dot) {
    		pacDotCounter++;
    		this.score += 1;
    		}
    	if(pellet) {
    		this.powerPelletDuration= this.powerPelletTime;
    		this.powerPelletMode = true;
    		this.score += 30 ;
    		this.notifyObserversStartPowerPelletMode();
    	}
    	if(extra != null){
    		this.extraItemDuration = 0;
    		this.extraItemPosition = null;
    		this.score = score + extra.getPoints();
    		this.notifyObserversExtraItemVanished();
    	}}
       return true;
    }

    /**
	 * Check if two actors would be on the field and returns the one that would
	 * not survive the encounter. If one of the actors is null or the two actors
	 * are ghosts null is returned.
	 * 
	 * @param actor
	 *            the actor on a field.
	 * @param incomingActor
	 *            the actor that is entering the field.
	 * 
	 * @return The actor that would not survive the encounter, or null.
	 */
    private Actor checkActor(Actor actor, Actor incomingActor) {
    	
    	if(actor instanceof PacMan) {
    		if(incomingActor==null) return null; 
    		if (incomingActor instanceof Ghost && this.isPowerPelletMode()) return incomingActor;
    		else return actor;
    	}
    	if(actor instanceof Ghost) {
    		if(incomingActor instanceof Ghost) return null;
    		if(incomingActor instanceof PacMan){
    		if (!this.isPowerPelletMode()) return incomingActor;
    		else return actor;	
    		}
    	}
        return null;
    }

    /**
	 * This method tries to move the given actor in the given direction. If an
	 * enemy is on the target field and not
	 * {@link PacManGame#isPowerPelletMode()} Pac-Man dies immediately -
	 * otherwise the ghost dies.
	 * 
	 * @param actor
	 *            The given actor to be moved.
	 * @param direction
	 *            The direction of the actor.
	 * 
	 * @return true, if the move could be carried out, else false.
	 */
    private boolean move(Actor actor, Direction direction) {
    	Point pos = (Point) actor.getPosition().clone() ;
    	if(direction==null) return false;
    	else
    	{
    	switch (direction)
	    {
	    case LEFT: pos.y--;break;
	    case RIGHT: pos.y++; break;
	    case UP: pos.x--;    break;
	    case DOWN: pos.x++; break;
	    }}
    	if(pos.x < 0 ||pos.x >= board.getNumberOfRows()||pos.y < 0||pos.y >= board.getNumberOfColumns()){
    	if(pos.x < 0) pos.x += board.getNumberOfRows();
    	if(pos.x >= board.getNumberOfRows()) pos.x -= board.getNumberOfRows();
    	if(pos.y < 0) pos.y += board.getNumberOfColumns();
    	if(pos.y >= board.getNumberOfColumns()) pos.y -= board.getNumberOfColumns();
    	actor.setOldPosition(pos);
    	}
    	return turn(actor,pos.x,pos.y);
    }

    /**
	 * Set the speed of the ghosts.
	 * 
	 * @param time
	 *            the time the ghosts need for one move.
	 */
    public void setGhostMoveTime(long time) {
        this.ghostMoveTime= time;
        return ;
    }

    /**
	 * Returns the time the ghosts need for one move.
	 * 
	 * @return the time the ghosts need for one move.
	 */
    public long getGhostMoveTime() {
        return this.ghostMoveTime;
    }

    /**
	 * Sets the time Pac-Man needs for one move.
	 * 
	 * @param time
	 *            the time Pac-Man needs for one move.
	 */
    public void setPacManMoveTime(long time) {
        this.pacManMoveTime= time;
        return ;
    }

    /**
	 * Returns the time Pac-Man need for one move.
	 * 
	 * @return the time Pac-Man need for one move.
	 */
    public long getPacManMoveTime() {
        return this.pacManMoveTime;
    }

    /**
	 * Return a GameInfo Object representing this game.
	 * 
	 * @return a GameInfo Object representing this game.
	 */
    public GameInfo getGameInfo() {
        return new MyGameInfo(this);
    }

    /**
	 * Returns the speed at which new extra items appear.
	 * 
	 * @return the speed at which new extra items appear.
	 */
    public long getNewExtraItemTime() {
        return this.newExtraItemTime;
    }

    /**
	 * Sets the speed at which new extra items appear.
	 * 
	 * @param millis
	 *            the speed at which new extra items appear.
	 */
    public void setNewExtraItemTime(long millis) {
        this.newExtraItemTime= millis;
        return ;
    }

    /**
	 * Returns the time how long an extra item stays on the board without being
	 * eaten.
	 * 
	 * @return the time how long an extra item stays on the board.
	 */
    public long getExtraItemTime() {
        return this.extraItemTime;
    }

    /**
	 * Sets the time how long an extra item stays on the board without being
	 * eaten.
	 * 
	 * @param millis
	 *            the time how long an extra item stays on the board.
	 */
    public void setExtraItemTime(long millis) {
        this.extraItemTime=millis;
        return ;
    }

    /**
	 * Returns how long a power pellet lasts.
	 * 
	 * @return how long a power pellet lasts.
	 */
    public long getPowerPelletTime() {
        return this.powerPelletTime;
    }

    /**
	 * Sets the time how long a power pellet is active.
	 * 
	 * @param millis
	 *            the time how long a power pellet is active.
	 */
    public void setPowerPelletTime(long millis) {
        this.powerPelletTime=millis;
        return ;
    }

    /**
	 * Returns a {@link BoardInfo} object for the underlying board.
	 * 
	 * @return a {@link BoardInfo} object for the underlying board.
	 */
    public BoardInfo getBoardInfo() {
        return new MyBoardInfo(board);
    }

    /**
	 * Returns the actual board of this game.
	 * 
	 * @return the actual board of this game.
	 */
    public Board getBoard() {
        return this.board;
    }

    /**
	 * Returns the current stage.
	 * 
	 * @return the current stage.
	 */
    public int getStage() {
       return this.stageCounter;
    }

    /**
	 * Returns the extraItemPosition or null.
	 * 
	 * @return the extraItemPosition or null.
	 */
    public Point getExtraItemPosition() {
        return this.extraItemPosition;
    }

    /**
	 * Returns the coordinates of the field Pac-Man stands on.
	 * 
	 * @return the coordinates of the field Pac-Man stands on.
	 */
    public Point getPacManPosition() {
        return this.pacMan.getPosition();
    }

    /**
	 * Returns the coordinates of the fields the ghosts stand on.
	 * 
	 * @return the coordinates of the fields the ghosts stand on.
	 */
    public List<Point> getGhostPositions() {
    	ArrayList<Point> pos = new ArrayList<Point>();  
    	if(ghosts!=null && !ghosts.isEmpty()){
    	for(Ghost g : ghosts) 
    		pos.add(g.getPosition());}
    	return pos;
    }

    /**
	 * Returns the number of lives left.
	 * 
	 * @return the number of lives left.
	 */
    public int getLives() {
        return this.lives;
    }

    /**
	 * Sets the state of the game to game over.
	 */
    public void setGameOver() {
        this.gameOver=true;
        return ;
    }

    public void resetLastStepInvocation() {
        this.lastStepInvocation=0;
        return ;
    }
    /**
	 * Returns true, if the game is over.
	 * 
	 * @return true, if the game is over.
	 */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
	 * Returns Pac-Man.
	 * 
	 * @return Pac-Man.
	 */
    private PacMan getPacMan() {
        return this.pacMan;
    }

    /**
	 * Returns the current amount of points.
	 * 
	 * @return the amount of points.
	 */
    public long getScore() {
        return this.score;
    }
    public int getPacDots() {
        return this.pacDotCounter;
    }
    /**
	 * Indicates whether or not Pac-Man is in the power pellets mode.
	 * 
	 * @return whether or not is Pac-Man in the power pellet mode.
	 */
    public boolean isPowerPelletMode() {
        return this.powerPelletMode;
    }

    /**
	 * Returns the direction Pac-Man looks into.
	 * 
	 * @return the direction Pac-Man looks into.
	 */
    public Direction getPacManDirection() {
        return this.pacMan.getDirection();
    }
   
    /**
	 * A getter for pacManMoveDuration.
	 * 
	 * @return  pacManMoveDuration.
	 */
    public long getPacManMoveDuration() {
        return  pacManMoveDuration;
    }
    /**
	 * A getter for ghostMoveDuration.
	 * 
	 * @return  ghostMoveDuration.
	 */
    public long getGhostMoveDuration() {
        return  ghostMoveDuration;
    }
    public long getpowerduration() {
        return  powerPelletDuration;
    }
   
//   @Override
    public void addObserver(GameObserver observer) {
    	if (observer==null) throw new IllegalArgumentException("given observer is null");
    	this.observers.add(observer);
        return ;
    }

//    @Override
    public void removeObserver(GameObserver observer) {
    	if (observer==null) throw new IllegalArgumentException("given observer is null");
        this.observers.remove(observer);
        return ;
    }

    /**
	 * This method informs all observers about the end of PowerPelletMode.
	 * 
	 */
    private void notifyObserversEndPowerPelletMode() {
        for (GameObserver o : this.observers){
        o.endPowerPelletMode();	
        }
    	return ;
    }

    /**
	 * This method informs all observers about a removed actor.
	 * 
	 * @param actortype
	 *            The type of the removed actor.
	 * 
	 * @param x
	 *            The x-coordinate of the field where the actor has been
	 *            removed.
	 * @param y
	 *            The y-coordinate of the field where the actor has been
	 *            removed.
	 */
    private void notifyObserversActorRemoved(ActorType actortype, int x, int y) {
    	 for (GameObserver o : this.observers){
    	        o.actorRemoved(actortype, x, y);
    	        }
        return ;
    }

    /**
	 * This method informs all observers about a set actor.
	 * 
	 * @param actortype
	 *            The type of the set actor.
	 * @param x
	 *            The x-coordinate of the field where the actor has been set.
	 * @param y
	 *            The y-coordinate of the field where the actor has been set.
	 */
    private void notifyObserversActorSet(ActorType actortype, int x, int y) {
    	 for (GameObserver o : this.observers){
    	        o.actorSet(actortype, x, y)	;
    	        }
        return ;
    }

    /**
	 * This method informs all observers about a new extra item.
	 * 
	 * @param p
	 *            The coordinates where the extra item has been placed.
	 * 
	 */
    private void notifyObserversExtraItemPlaced(Point p) {
    	 for (GameObserver o : this.observers){
    	        o.extraItemPlaced(p);	
    	        }
        return ;
    }

    /**
	 * This method informs all observers about a vanished extra item.
	 * 
	 */
    private void notifyObserversExtraItemVanished() {
    	 for (GameObserver o : this.observers){
    	        o.extraItemVanished();	
    	        }
        return ;
    }

    /**
	 * This method informs all observers about the end of game.
	 * 
	 */
    private void notifyObserversGameOver() {
    	board.initBoard();
    	for (GameObserver o : this.observers){
	        o.gameOver();	
	        }
        return ;
    }

    /**
	 * This method informs all observers about Pac-Man's death.
	 * 
	 */
    private void notifyObserversPacManDied() {
    	for (GameObserver o : this.observers){
	        o.pacManDied();	
	        }
        return ;
    }

    /**
	 * This method informs all observers about the begin of PowerPelletMode.
	 * 
	 */
    private void notifyObserversStartPowerPelletMode() {
    	for (GameObserver o : this.observers){
	        o.startPowerPelletMode();	
	        }
        return ;
    }

    /**
	 * This method informs all observers that a step was made.
	 * 
	 */
    private void notifyObserversStepDone() {
    	for (GameObserver o : this.observers){
	        o.stepDone();	
	        }
        return ;
    }

    /**
	 * This method informs all observers that a new stage has come up.
	 * 
	 */
    private void notifyObserversNextStage() {
    	for (GameObserver o : this.observers){
	        o.nextStage();	
	        }
        return ;
    }
    /*
	 * This method is new.
	 * 
	 
    private void notifyObserversActorTurn() {
    	for (GameObserver o : this.observers){
	        o.actorTurn();	
	        }
        return ;
    } */

	public Point getDeathpos() {
		return deathpos;
	}
}