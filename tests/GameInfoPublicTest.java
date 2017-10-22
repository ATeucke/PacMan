package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.Direction.*;
import static prog2.project5.enums.FieldType.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import prog2.project5.enums.FieldType;
import prog2.project5.game.Board;
import prog2.project5.game.Field;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GameObserverAdpater;
import prog2.project5.game.PacManGame;
import prog2.project5.testutil.TestUtil;

public class GameInfoPublicTest {

	private GameInfo info;
	private PacManGame pmg;

	private static final class TestObserver extends GameObserverAdpater {

		int steps;

		@Override
		public void stepDone() {
			steps++;
		}
	}

	@Before
	public void setUp() throws Exception {
		Field[][] board = TestUtil.getField(new FieldType[][] {
				{ WALL, FREE, PACMAN_START, FREE, GHOST_START },
				{ WALL, GHOST_START, WALL, POWER_PELLET, WALL } });
		pmg = new PacManGame(new Board(board), getSimpleControllerFactory(LEFT));
		info = pmg.getGameInfo();
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void getFieldInfoExceptionTest() {
		try {
			info.getBoardInfo().getFieldInfo(null);
			fail("Expected an exception.");
		} catch (IllegalArgumentException e) {

		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testGameOver() {
		assertFalse("Expected game not to be over.", info.isGameOver());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPowerPelletMode() {
		assertFalse(info.isPowerPelletMode());
	}

}
