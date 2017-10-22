package prog2.project5.autoplay;

import prog2.project5.enums.Direction;

/**
 * An ActorController controls one actor in the Pac-Man game.
 */
public interface ActorController {

	/**
	 * Calculates a move for the actor.
	 * 
	 * @return the next move the actor should do.
	 */
	public Direction getMove();

}