package prog2.project5.game;

import java.awt.Point;
import prog2.project5.autoplay.ActorController;
import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;

/**
 * Abstract class that represents the actors (Pac-Man and ghosts) of the game.
 */
public abstract class Actor {

    /**
	 * The type of the actor.
	 */
    protected ActorType actorType;

    /**
	 * The position of the actor.
	 */
    protected Point position;
    
    /**
	 * The former position of the actor.
	 */
    protected Point oldPosition;

    /**
	 * The auto player for the ghost.
	 */
    private ActorController autoplayer = null;

    /**
	 * A getter for the position.
	 * 
	 * @return the position.
	 */
    public Point getPosition() {
        return  position;
    }
    /**
	 * A getter for the former position.
	 * 
	 * @return the former position.
	 */
    public Point getOldPosition() {
    	if(oldPosition==null)return position;
        return  oldPosition;
    }
    /**
	 * A setter for the former position.
	 * 
	 * @return the former position.
	 */
    public void setOldPosition(Point p) {
       oldPosition=p;
    }
    /**
	 * A getter for the actorType.
	 * 
	 * @return the actorType.
	 */
    public ActorType getType() {
        return actorType;
    }

    /**
	 * A setter for the position.
	 * 
	 * @param position
	 *            the position to set.
	 * @throws IllegalArgumentException
	 *             if the given position is null.
	 * 
	 */
    public void setPosition(Point position) {
    	if (position==null) throw new IllegalArgumentException("given position is null");
        this.position  = position;
        return ;
    }

    /**
	 * A setter for the position.
	 * 
	 * @param x
	 *            the x coordinate.
	 * @param y
	 *            the y coordinate.
	 */
    public void setPosition(int x, int y) {
        this.position = new Point(x,y);
        return ;
    }

    /**
	 * Sets the {@link ActorController} for this Actor.
	 * 
	 * @param controller
	 *            {@link ActorController} for this Actor.
	 */
    public void initController(ActorController controller) {
    	if (controller==null) throw new IllegalArgumentException("given controller is null");
    	this.autoplayer = controller;
        return ;
    }

    /**
	 * Calculates a move.
	 * 
	 * @return The move the auto player calculates or null if no auto player is
	 *         available.
	 */
    public Direction getMove() {
    	if (autoplayer==null) return null;
    	return autoplayer.getMove();
    }
}