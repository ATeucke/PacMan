package prog2.project5.game;


import java.awt.Point;


import prog2.project5.enums.ActorType;
import prog2.project5.view.PacManComponent;

/**
 * Class provides default implementations for all {@link GameObserver} methods.
 * This class can be used as a super class for other observers, such that they
 * only have to overwrite the necessary methods.
 * 
 */
public abstract class GameObserverAdpater implements GameObserver {

	
	
//	@Override
	public void actorRemoved(ActorType actortype, int x, int y) {
	}

	//@Override
	public void actorSet(ActorType actortype, int x, int y) {
	}

//	@Override
	public void endPowerPelletMode() {
	}

	//@Override
	public void extraItemPlaced(Point p) {
	}

//	@Override
	public void extraItemVanished() {
	}

//	@Override
	public void gameOver() {
	}

//	@Override
	public void nextStage() {
	}

//	@Override
	public void pacManDied() {
	}

	//@Override
	public void startPowerPelletMode() {
	}

//	@Override
	public void stepDone() {
	}
    
//	@Override
	public void actorTurn() {
	}
}