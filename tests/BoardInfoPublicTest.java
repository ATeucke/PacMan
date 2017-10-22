package prog2.project5.tests;

import java.awt.Point;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import prog2.project5.enums.FieldType;
import prog2.project5.game.Board;
import prog2.project5.game.BoardInfo;
import prog2.project5.game.Field;
import prog2.project5.testutil.TestUtil;
import static org.junit.Assert.*;
import static prog2.project5.enums.FieldType.*;
import static prog2.project5.testutil.TestUtil.*;

public class BoardInfoPublicTest {

	private BoardInfo info;

	@Before
	public void setUp() {
		Field[][] field = TestUtil.getField(new FieldType[][] {
				{ FREE, FREE, PACMAN_START, FREE },
				{ GHOST_START, FREE, WALL, POWER_PELLET } });
		Board b = new Board(field);
		info = b.getBoardInfo();
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testGetGhostStart() {
		List<Point> ghostsStart = info.getGhostsStart();
		assertEquals("Expected different size", 1, ghostsStart.size());
		assertEquals(new Point(1, 0), ghostsStart.get(0));
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testRows() {
		assertEquals("Expected different number of rows", 2, info
				.getNumberOfRows());

	}

}
