package prog2.project5.game;


import java.awt.Point;
import java.util.List;


/**
 * BoardInfo provides information about a {@link Board} but does not allow to
 * manipulate it.
 */
public interface BoardInfo {

	/**
	 ** Returns the field information for a given position.
	 * 
	 * @param p
	 *            the position of the field.
	 * 
	 * @return the information of the field at position p.
	 * 
	 * @throws IllegalArgumentException
	 *             if null is given, or the coordinates are not on the board.
	 */
	FieldInfo getFieldInfo(Point p);

	/**
	 * Returns the field information for a given position.
	 * 
	 * @param x
	 *            the x coordinate of the field.
	 * @param y
	 *            the y coordinate of the field.
	 * 
	 * 
	 * @return the information on the field at position x,y.
	 * 
	 */
	FieldInfo getFieldInfo(int x, int y);

	/**
	 * Returns the position of the start fields of the ghosts.
	 * 
	 * @return the position of the start fields of the ghosts.
	 */
	List<Point> getGhostsStart();

	/**
	 * Returns the amount of pac-dots at the start of the game.
	 * 
	 * @return the amount of pac-dots at the start of the game.
	 */
	int getPacDotsOnStart();

	/**
	 * Returns the position of the Pac-Man start field.
	 * 
	 * @return the position of the Pac-Man start field.
	 */
	Point getPacManStart();

	/**
	 * Returns the number of columns of the board.
	 * 
	 * @return the number of columns of the board.
	 */
	int getNumberOfColumns();

	/**
	 * Returns the number of rows of the board.
	 * 
	 * @return the number of rows of the board.
	 */
	int getNumberOfRows();

	/**
	 * Returns a list of all fields where an extra item can be placed.
	 * 
	 * @return a list of all fields where an extra item can be placed.
	 */
	List<Point> getExtraItemFields();

}