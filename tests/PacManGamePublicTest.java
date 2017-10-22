package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.Direction.*;
import static prog2.project5.enums.FieldType.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;

import org.junit.Test;

import prog2.project5.autoplay.ActorController;
import prog2.project5.autoplay.ControllerFactory;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;
import prog2.project5.game.Board;
import prog2.project5.game.Field;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GhostInfo;
import prog2.project5.game.PacManGame;
import prog2.project5.testutil.TestUtil;
import prog2.project5.testutil.TestUtil.TestControllerFactory;

public class PacManGamePublicTest {

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructorException1() {
		try {
			new PacManGame(null, null);
			fail("Expected exception if given array is null.");
		} catch (IllegalArgumentException expected) {
		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPacManMoveRightEasy() {
		Board b = getBoard(new FieldType[][] { { PACMAN_START, FREE },
				{ FREE, FREE } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(RIGHT));
		makeStep(pmg);
		assertEquals("Pac-Man moved wrong.", new Point(0, 1), pmg
				.getPacManPosition());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPacManMoveRight() {
		Board b = getBoard(new FieldType[][] { { FREE, PACMAN_START },
				{ FREE, FREE } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(RIGHT));
		makeStep(pmg);
		assertEquals("Pac-Man moved wrong.", new Point(0, 0), pmg
				.getPacManPosition());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPacManMoveRightNotPossible() {

		Board b = getBoard(new FieldType[][] { { FREE, FREE },
				{ PACMAN_START, WALL } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(RIGHT));
		makeStep(pmg);
		assertEquals("Pac-Man moved but shouldn't do so.", new Point(1, 0), pmg
				.getPacManPosition());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPacManDirection1() {
		Board b = getBoard(new FieldType[][] { { FREE, FREE },
				{ PACMAN_START, WALL } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(UP));
		makeStep(pmg);
		assertEquals("Pac-Man's direction is wrong.", UP, pmg
				.getPacManDirection());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPacDotScore() {
		Board b = getBoard(new FieldType[][] { { FREE, WALL, FREE, FREE, FREE,
				PACMAN_START } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(LEFT));
		pmg.setPacManMoveTime(1);
		makeStep(pmg);
	
		makeStep(pmg);
	
		makeStep(pmg);
		assertEquals("Score wasn't correct after 3 eaten pac-dots.", 3, pmg
				.getScore());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testDurationPowerPellet() {
		Board b = getBoard(new FieldType[][] { { FREE, WALL, POWER_PELLET,
				FREE, PACMAN_START } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(LEFT));
		pmg.setPowerPelletTime(20);
		assertFalse("PowerPelletMode should not be active.", pmg
				.isPowerPelletMode());
		makeStep(pmg);
		makeStep(pmg);
		assertTrue("PowerPelletMode should be active.", pmg.isPowerPelletMode());
		pmg.step(22);
		assertFalse("PowerPelletMode should not be active.", pmg
				.isPowerPelletMode());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testIllegalPowerPelletMode() {
		Board b = getBoard(new FieldType[][] { { FREE }, { PACMAN_START },
				{ FREE }, { POWER_PELLET }, { WALL } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(DOWN));

		assertFalse("PowerPelletMode was active, but shoud not be active", pmg
				.isPowerPelletMode());
		TestUtil.makeStep(pmg);
		assertEquals("Expect different points for power pellet", 1, pmg
				.getScore());
		TestUtil.makeStep(pmg);
		TestUtil.makeStep(pmg);
		assertTrue("PowerPelletMode was not active, but should be", pmg
				.isPowerPelletMode());
		assertEquals("Expect different points for power pellet", 31, pmg
				.getScore());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testExtraItem() {
		Board b = getBoard(new FieldType[][] { { FREE, WALL, FREE, FREE, WALL,
				PACMAN_START } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(LEFT));
		pmg.setNewExtraItemTime(10);
		assertNull("Expect no extra item on board", pmg.getExtraItemPosition());
		makeStep(pmg);
		pmg.step(22);
		assertNotNull("Expect extra item on board", pmg.getExtraItemPosition());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPowerPelletPacManRunsInGhost() {
		Board b = getBoard(new FieldType[][] { { FREE, WALL, GHOST_START, FREE,
				FREE, POWER_PELLET, PACMAN_START } });
		TestControllerFactory cf = new TestUtil.TestControllerFactory();
		PacManGame pmg = new PacManGame(b, cf);
		cf.setNextPacManMove(LEFT);
		cf.setGhostMove(RIGHT);
		makeStep(pmg);
		long score = pmg.getScore();
		cf.setNextPacManMove(LEFT);
		makeStep(pmg);
		cf.setNextPacManMove(LEFT);
		makeStep(pmg);
		assertTrue("Expected ghost to be killed ", score + 25 <= pmg.getScore());
	}

	
	@Test(timeout = DEFAULT_TIMEOUT)
	public void testExtraItemRemoveNextLevel() {
		Board b = getBoard(new FieldType[][] {
				{ FREE, FREE, FREE, WALL, PACMAN_START },
				{ WALL, WALL, FREE, WALL, WALL } });

		TestControllerFactory cf = new TestUtil.TestControllerFactory();
		PacManGame pmg = new PacManGame(b, cf);
		cf.setNextPacManMove(RIGHT);
		cf.setGhostMove(RIGHT);
		makeStep(pmg);
		makeStep(pmg);
		makeStep(pmg);
		long score = pmg.getScore();

		pmg.setNewExtraItemTime(10);
		pmg.setExtraItemTime(10);
		assertNull("Expect no extra item on board", pmg.getExtraItemPosition());
		makeStep(pmg);
		long oldScore = pmg.getScore();
		do {
			pmg.step(22);
		} while (oldScore + ExtraItem.CHERRY.getPoints() < pmg.getScore()
				|| pmg.getExtraItemPosition().x != 0
				|| pmg.getPacManPosition().equals(pmg.getExtraItemPosition()));
		assertNotNull("Expect extra item on board", pmg.getExtraItemPosition());

		int stage = pmg.getStage();
		cf.setNextPacManMove(DOWN);
		makeStep(pmg);
		assertEquals("Expected next stage afet all pac dots are eaten",
				stage + 1, pmg.getStage());
		assertNull("Expect no extra item on board", pmg.getExtraItemPosition());

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void setterGetterTest() {
		Board b = getBoard(new FieldType[][] { { FREE, FREE, FREE, WALL,
				PACMAN_START } });

		TestControllerFactory cf = new TestUtil.TestControllerFactory();
		PacManGame pmg = new PacManGame(b, cf);
		pmg.setExtraItemTime(10);
		pmg.setGhostMoveTime(11);
		pmg.setNewExtraItemTime(12);
		pmg.setPacManMoveTime(13);
		pmg.setPowerPelletTime(14);
		assertEquals("Expected different extra item time after setting it ",
				10, pmg.getExtraItemTime());
		assertEquals("Expected different ghost move  time after setting it ",
				11, pmg.getGhostMoveTime());
		assertEquals(
				"Expected different new extra item  time after setting it ",
				12, pmg.getNewExtraItemTime());
		assertEquals("Expected different Pac-Man move time after setting it ",
				13, pmg.getPacManMoveTime());
		assertEquals("Expected different power pellet time after setting it ",
				14, pmg.getPowerPelletTime());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testNoPointsForGhostMoves() {
		Board b = getBoard(new FieldType[][] { { FREE, POWER_PELLET, FREE,
				GHOST_START, WALL, PACMAN_START, WALL } });
		PacManGame pmg = new PacManGame(b, TestUtil
				.getSimpleControllerFactory(LEFT));
		makeStep(pmg);
		makeStep(pmg);
		makeStep(pmg);
		makeStep(pmg);
		assertEquals(
				"Expected score not to increase when ghosts are moving on fields.",
				0, pmg.getScore());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testInitialNumberOfGhosts1() {
		Board b = TestUtil.getBoard(new FieldType[][] { { FREE, GHOST_START,
				GHOST_START, PACMAN_START } });
		ControllerFactory cf = TestUtil.getSimpleControllerFactory(LEFT);
		PacManGame pmg = new PacManGame(b, cf);
		assertEquals("Expected different number of ghosts", 2, pmg
				.getGhostPositions().size());
	}

}
