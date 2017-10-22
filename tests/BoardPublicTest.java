package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.FieldType.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;
import prog2.project5.enums.GhostCharacter;
import prog2.project5.game.Board;
import prog2.project5.game.Field;
import prog2.project5.game.Ghost;
import prog2.project5.game.PacMan;

public class BoardPublicTest {
	private Field[][] board1;
	private Field[][] board2;
	private Field[][] board3;

	@Before
	public void setUp() throws Exception {
		board1 = new Field[3][3];
		board2 = new Field[3][3];
		board3 = new Field[3][3];

		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				board1[row][column] = new Field(FieldType.FREE);
				board2[row][column] = new Field(FieldType.GHOST_START);
				board3[row][column] = new Field(FieldType.FREE);

			}

		}
		board1[0][0] = new Field(FieldType.PACMAN_START);
		board2[0][0] = new Field(FieldType.PACMAN_START);
		board2[0][2] = new Field(FieldType.FREE);
		board3[0][0] = new Field(FieldType.PACMAN_START);
		board3[2][2] = new Field(FieldType.GHOST_START);
		board3[0][2] = new Field(FieldType.POWER_PELLET);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructorException1() {
		try {
			new Board(null);
			fail("You should throw an exception if a null is given.");
		} catch (IllegalArgumentException e) {

		}

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructorException4() {
		Field[][] board = new Field[][] { { new Field(FieldType.FREE) },
				{ new Field(FieldType.FREE) } };

		try {
			new Board(board);
			fail("Expected exception if no Pac-Man start field is available.");
		} catch (IllegalArgumentException expected) {

		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructorException7() {
		Field[][] board = new Field[][] {
				{ new Field(FieldType.FREE), new Field(FieldType.PACMAN_START) },
				{ null, new Field(FieldType.FREE) } };

		try {
			new Board(board);
			fail("The given board contains a null - Exception expected.");
		} catch (IllegalArgumentException expected) {

		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructor() {
		Board b = new Board(board1);
		assertEquals("You should set the position of the Pac-Man start field",
				new Point(0, 0), b.getPacManStart());
		assertEquals("Wrong number of pac-dots.", 8, b.getPacDotsOnStart());
		assertNotNull("Expected a board that is  not null.", b.getBoard());

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructor2() {
		Board b = new Board(board2);
		List<Point> l = b.getGhostsStart();

		assertEquals("You should set the position of the Pac-Man start field",
				new Point(0, 0), b.getPacManStart());
		assertEquals("The amount of ghost-starts is wrong.", 7, l.size());

		int i = 0;
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (!(row == 0 && column == 0) && !(row == 0 && column == 2)) {
					assertEquals("Wrong position saved.",
							new Point(row, column), l.get(i));
					i++;
				}

			}
		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void parseTest1() {
		Field[][] expectedField = new Field[][] { { new Field(WALL),
				new Field(FREE), new Field(PACMAN_START), new Field(FREE),
				new Field(POWER_PELLET), new Field(FREE),
				new Field(GHOST_START), new Field(FREE), new Field(WALL) } };
		String boardString = "#-P-X-G-#";
		Board board = Board.parse(new String[] { boardString });
		assertEquals(1, board.getNumberOfRows());
		assertEquals(9, board.getNumberOfColumns());
		for (int i = 0; i < expectedField[0].length; i++) {
			Field field = board.getField(0, i);
			assertEquals("Expected other field type for parsed board", field
					.getType(), expectedField[0][i].getType());
		}
	}

}
