package prog2.project5.autoplay;

import prog2.project5.game.GameInfo;
import prog2.project5.game.GhostInfo;

/**
 * ControllerFactory provides controllers for the actors in the game.
 */
public interface ControllerFactory {

	/**
	 * Returns a controller for a ghost.
	 * 
	 * @param gameInfo
	 *            information about the game.
	 * @param ghostInfo
	 *            information about the ghost.
	 * @return a controller for a ghost.
	 */
	ActorController getGhostController(GameInfo gameInfo, GhostInfo ghostInfo);

	/**
	 * Returns a controller for Pac-Man.
	 * 
	 * @param gameInfo
	 *            information about the game.
	 * @return a controller for Pac-Man.
	 */
	ActorController getPacManController(GameInfo gameInfo);
}