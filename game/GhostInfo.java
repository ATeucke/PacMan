package prog2.project5.game;


import java.awt.Color;
import java.awt.Point;


import prog2.project5.enums.GhostCharacter;

/**
 * GhostInfo provides information about a {@link Ghost} but does not allow to
 * manipulate it.
 */
public interface GhostInfo {

	/**
	 * Returns the current position of the ghost.
	 * 
	 * @return the position of the ghost.
	 */
	Point getPosition();

	/**
	 * Returns the color of this ghost.
	 * 
	 * @return the color of this ghost.
	 */
	Color getColor();

	/**
	 * Returns the character of this ghost.
	 * 
	 * @return the character of this ghost.
	 */
	GhostCharacter getCharacter();
	 /**
	 * A getter for the former position.
	 * 
	 * @return the former position.
	 */
    
}