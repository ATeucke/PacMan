package prog2.project5.view;

import javax.swing.JComponent;
import prog2.project5.enums.Direction;
import prog2.project5.game.GameObserverAdpater;
import prog2.project5.game.PacManGame;
import prog2.project5.autoplay.ActorController;

/**
 * An ActorController that allows to move an actor in the given direction that
 * is set via setDirection. This ActorController can be used to control Pac-Man
 * by a user.
 * 
 * Additionally it has references to a {@link PacManGame} and a
 * {@link JComponent}. Such that it can execute a step on the Pac-Man game and
 * update the component.
 */
public class ControlPlayer implements ActorController {

    /**
	 * The direction the actor should move.
	 */
    private Direction direction = Direction.LEFT;

    /**
	 * The Pac-Man game.
	 */
    private PacManGame game;

    /**
	 * The component to update after a step was made.
	 */
    private JComponent component;

   // @Override
    public Direction getMove() {
        return direction;
    }

    /**
	 * Sets the direction for the next move, takes out a step in the game, and
	 * updates the component.
	 * 
	 * @param direction
	 *            the next direction for the move.
	 */
    public void setDirection(Direction direction) {
        this.direction=direction;
        return ;
    }

    /**
	 * Sets the game.
	 * 
	 * @param game
	 *            the game to set.
	 */
    public void setGame(PacManGame game) {
    	this.game=game;
    	game.addObserver(new ControllerObserver());
        return ;
    }

    /**
	 * Sets the component.
	 * 
	 * @param component
	 *            the component to set.
	 */
    public void setComponent(JComponent component) {
        this.component=component;
        return ;
    }
    
    private class ControllerObserver extends GameObserverAdpater{
//    	@Override
    	public void nextStage() {
    		direction=Direction.LEFT;
    	}

//    	@Override
    	public void pacManDied() {
    		direction=Direction.LEFT;
    	}
    }
}