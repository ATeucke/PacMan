package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.Direction.*;
import static prog2.project5.enums.FieldType.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import prog2.project5.autoplay.ActorController;
import prog2.project5.autoplay.ControllerFactory;
import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;
import prog2.project5.game.Board;
import prog2.project5.game.Field;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GameObserver;
import prog2.project5.game.GhostInfo;
import prog2.project5.game.PacManGame;
import prog2.project5.testutil.TestUtil;
import prog2.project5.testutil.TestUtil.TestControllerFactory;

public class GameObserverPublicTest {

	private static class MyObserver implements GameObserver {

		int actorRemoved;
		int actorSet;
		int endPowerPelletMode;
		int extraItemPlaced;
		int extraItemVanished;
		int gameOver;
		int pacManDied;
		int startPowerPelletMode;
		int stepDone;
		int nextStage;
		Point extraItemPos;
		ActorType removedActorType;
		Point removedActorPos;
		ActorType setActorType;
		Point setActorPos;

		//@Override
		public void actorRemoved(ActorType actortype, int x, int y) {
			actorRemoved++;
			removedActorType = actortype;
			removedActorPos = new Point(x, y);
		}

		//@Override
		public void actorSet(ActorType actortype, int x, int y) {
			actorSet++;
			setActorType = actortype;
			setActorPos = new Point(x, y);
		}

		//@Override
		public void endPowerPelletMode() {

			endPowerPelletMode++;
		}

	//	@Override
		public void extraItemPlaced(Point p) {
			extraItemPlaced++;
			extraItemPos = p;
		}

		//@Override
		public void extraItemVanished() {
			extraItemVanished++;
		}

	//	@Override
		public void gameOver() {
			gameOver++;
		}

	//	@Override
		public void pacManDied() {
			pacManDied++;
		}

	//	@Override
		public void startPowerPelletMode() {
			startPowerPelletMode++;
		}

		//@Override
		public void stepDone() {
			stepDone++;
		}

		//@Override
		public void nextStage() {
			nextStage++;
		}

	}

	private final class SimpleAutoPlayer implements ActorController {
		private Direction direction;

		//@Override
		public Direction getMove() {
			return direction;
		}

		public void setDirection(Direction direction) {
			this.direction = direction;
		}
	}

	private MyObserver observer;
	private PacManGame pmg;
	private PacManGame ghostGame;
	private SimpleAutoPlayer simpleAutoplayer;

	@Before
	public void setUp() throws Exception {
		Field[][] field = TestUtil.getField(new FieldType[][] { { FREE },
				{ POWER_PELLET }, { FREE }, { PACMAN_START } });
		simpleAutoplayer = new SimpleAutoPlayer();
		pmg = new PacManGame(new Board(field), new ControllerFactory() {
		//@Override
			public ActorController getGhostController(GameInfo info,
					GhostInfo ghost) {
				return simpleAutoplayer;
			}

		//	@Override
			public ActorController getPacManController(GameInfo info) {
				return simpleAutoplayer;
			}
		});

		Field[][] ghostField = new Field[][] { { new Field(FieldType.FREE),
				new Field(FieldType.GHOST_START),
				new Field(FieldType.PACMAN_START), new Field(FieldType.WALL) } };
		ghostGame = new PacManGame(new Board(ghostField),
				new ControllerFactory() {
			//		@Override
					public ActorController getGhostController(GameInfo info,
							GhostInfo ghost) {
						return simpleAutoplayer;
					}

			//		@Override
					public ActorController getPacManController(GameInfo info) {
						return simpleAutoplayer;
					}
				});

		observer = new MyObserver();
		pmg.addObserver(observer);
		ghostGame.addObserver(observer);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void addNullObserver() {
		try {
			pmg.addObserver(null);
			fail("Expected an exception for adding null as an observer.");
		} catch (IllegalArgumentException e) {
		}

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testStepDone() {
		for (int i = 0; i < 10; i++) {
			simpleAutoplayer.setDirection(UP);
			makeStep(pmg);
			simpleAutoplayer.setDirection(DOWN);
			makeStep(pmg);

		}
		assertEquals("StepDone wasn't called the right number of times.", 20,
				observer.stepDone);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testRemoveObserver() {
		pmg.removeObserver(observer);
		for (int i = 0; i < 10; i++) {
			simpleAutoplayer.setDirection(UP);
			makeStep(pmg);
			simpleAutoplayer.setDirection(DOWN);
			makeStep(pmg);

		}
		assertEquals("Observer was not removed.", 0, observer.stepDone);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testExtraItemPlaced() {
		Field[][] field = TestUtil.getField(new FieldType[][] { { FREE, FREE,
				FREE, FREE, WALL, PACMAN_START } });
		PacManGame pmg = new PacManGame(new Board(field), TestUtil
				.getSimpleControllerFactory(LEFT));
		pmg.addObserver(observer);
		makeStep(pmg);
		pmg.setNewExtraItemTime(10);
		pmg.step(11);
		assertEquals("ExtraItem should have been placed.", 1,
				observer.extraItemPlaced);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testActorNotMoved() {
		Board board = TestUtil.getBoard(new FieldType[][] {
				{ GHOST_START, WALL, PACMAN_START, WALL },
				{ FREE, FREE, FREE, FREE } });
		PacManGame pacManGame = new PacManGame(board, new ControllerFactory() {
		//	@Override
			public ActorController getGhostController(GameInfo info,
					GhostInfo ghost) {
				return simpleAutoplayer;
			}

//			@Override
			public ActorController getPacManController(GameInfo info) {
				return simpleAutoplayer;
			}
		});
		pacManGame.addObserver(observer);
		simpleAutoplayer.setDirection(RIGHT);
		makeStep(pacManGame);
		assertEquals("ActorRemoved wasn't called the right number of times.",
				0, observer.actorRemoved);
		assertEquals("ActorSet wasn't called the right number of times.", 0,
				observer.actorSet);
	}

}
