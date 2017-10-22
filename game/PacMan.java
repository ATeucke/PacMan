package prog2.project5.game;

import static prog2.project5.enums.Direction.*;
import static prog2.project5.enums.ActorType.*;
import prog2.project5.enums.Direction;
import java.awt.Point;

/**
 * This class represents a Pac-Man.
 * 
 */
public class PacMan extends Actor {

    /**
	 * The line of sight of Pac-Man. A new Pac-Man should look to the left.
	 */
    private Direction direction;
    /**
	 * Creates a new Pac-Man object and initializes the position.
	 * 
	 * @param position
	 *            the position of the Pac-Man.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given position is null.
	 */
    public PacMan(Point position) {
    	if (position==null) throw new IllegalArgumentException("given position is null");
    	this.position= position;
    	this.direction= LEFT;
    	this.actorType = PACMAN;
    }

    /**
	 * Returns the direction of Pac-Man.
	 * 
	 * @return the direction of Pac-Man.
	 */
    public Direction getDirection() {
       return direction;
    }

    /**
	 * A setter for the direction.
	 * 
	 * @param direction
	 *            the direction to set.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given direction is null.
	 */
    public void setDirection(Direction direction) {
    	if (direction==null) throw new IllegalArgumentException("given direction is null");
    	this.direction = direction;
        return ;
    }

    /**
	 * Returns the string representation of Pac-Man. The string should contain
	 * (in this order): the type of the actor, the position, and the direction. <br/>
	 * <br/>
	 * The format should be: <br/>
	 * 
	 * <br/>
	 * [PACMAN, position: (2,4), direction: LEFT]
	 * 
	 * 
	 * @return the string representation of Pac-Man.
	 */
    @Override
    public String toString() {
    	String s = "[PACMAN, position: (" + position.x +","+ position.y +"), direction: " + direction +"]";
        return s;
    }

}