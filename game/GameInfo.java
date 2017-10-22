package prog2.project5.game;


import java.awt.Point;
import java.util.List;


import prog2.project5.enums.Direction;

/**
 * GameInfo provides information about a {@link PacManGame} but does not allow
 * to manipulate it.
 * 
 */
public interface GameInfo {

	/**
	 * Returns the current position of Pac-Man.
	 * 
	 * @return the current position of Pac-Man.
	 */
	Point getPacManPosition();
	Point getdeadManPosition();
	/**
	 * Returns the current direction of Pac-Man.
	 * 
	 * @return the current direction of Pac-Man.
	 */
	Direction getPacManDirection();

	/**
	 * Returns the current positions of the ghosts.
	 * 
	 * @return the current positions of the ghosts.
	 */
	List<Point> getGhostPositions();

	/**
	 * Returns the number of remaining lives.
	 * 
	 * @return the number of remaining lives.
	 */
	int getLives();

	/**
	 * Adds the given observer to the underlying {@link PacManGame}.
	 * 
	 * @param observer
	 *            the observer to add.
	 */
	 void addObserver(GameObserver observer);

	/**
	 * Removes the given observer from the underlying {@link PacManGame}.
	 * 
	 * @param observer
	 *            the observer to remove.
	 */
	void removeObserver(GameObserver observer);
	 
	/**
	 * Indicates whether or not the game is over.
	 * 
	 * @return true, if the game is over.
	 */
	boolean isGameOver();

	/**
	 * Indicates whether or not Pac-Man is in the power pellets mode.
	 * 
	 * @return whether or not is Pac-Man in the power pellet mode.
	 */
	boolean isPowerPelletMode();

	/**
	 * Returns a {@link BoardInfo} object that provides information about the
	 * underlying board.
	 * 
	 * @return a {@link BoardInfo} object.
	 */
	BoardInfo getBoardInfo();

	/**
	 * Returns the current score.
	 * 
	 * @return the current score.
	 */
	long getScore();
	
    /**
	 * A getter for pacManMoveDuration.
	 * 
	 * @return  pacManMoveDuration.
	 */
    public long getPacManMoveDuration() ;
    public long getPacManMoveTime() ;
    /**
	 * A getter for ghostMoveDuration.
	 * 
	 * @return  ghostMoveDuration.
	 */
    public long getGhostMoveDuration() ;
    public long getGhostMoveTime() ;
    public long getpowerduration();
   
}