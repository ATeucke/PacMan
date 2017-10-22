package prog2.project5.game;

import static prog2.project5.enums.ActorType.*;
import static prog2.project5.enums.Direction.LEFT;
import prog2.project5.enums.Direction;
import prog2.project5.enums.GhostCharacter;
import java.awt.Color;
import java.awt.Point;

/**
 * Class that represents a ghost.
 * 
 */
public class Ghost extends Actor {

    /**
	 * The character of the ghost.
	 */
    private GhostCharacter character;
    private Direction direction;
    /**
	 * Constructs a new ghost object, and initializes the position, character,
	 * and actorType.
	 * 
	 * @param position
	 *            the position of the ghost.
	 * @param character
	 *            character of the ghost.
	 * 
	 * @throws IllegalArgumentException
	 *             if the given position or character is null.
	 * 
	 */
    public Ghost(Point position, GhostCharacter character) {
    	if (position==null) throw new IllegalArgumentException("given position is null");
    	if (character==null) throw new IllegalArgumentException("given character is null");
    	this.character= character;
    	this.position= position;
    	this.actorType = GHOST;
    	this.direction= LEFT;
    }

    /**
	 * Returns the color of this ghost.
	 * 
	 * @return the color of this ghost.
	 */
    public Color getColor() {
       return character.getColor();
    }

    /**
	 * Returns the character of this ghost.
	 * 
	 * @return the character of this ghost.
	 */
    public GhostCharacter getCharacter() {
        return character;
    }

    /**
	 * Returns the string representation of this ghost. The string should
	 * contain (in this order): the type of the actor, the character, the
	 * position, and the color. <br/>
	 * <br/>
	 * The format should be: <br/>
	 * 
	 * <br/>
	 * [GHOST, character: OIKAKE, position: (5,2), color: java.awt.Color[r=255,g=0,b=0]]
	 * 
	 * @return the string representation of this ghost.
	 */
    @Override
    public String toString() {
        String s = "[GHOST, character: " +character +", position: (" + position.x +","+ position.y +"), color: " + getColor() +"]";
        return s;
    }
    public GhostInfo getGhostInfo() {
		return new MyGhostInfo(this);
	}
    
    public Direction getDirection() {
        return direction;
     }
    public void setDirection(Direction direction) {
    	if (direction==null) throw new IllegalArgumentException("given direction is null");
    	this.direction = direction;
        return ;
    }

}