package prog2.project5.testutil;

import prog2.project5.autoplay.ActorController;
import prog2.project5.autoplay.ControllerFactory;
import prog2.project5.enums.Direction;
import prog2.project5.enums.FieldType;
import prog2.project5.game.Board;
import prog2.project5.game.Field;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GhostInfo;
import prog2.project5.game.PacManGame;

public class TestUtil {

	public static final class TestControllerFactory implements
			ControllerFactory {

		private static final class Controller implements ActorController {

			Direction nextMove;

			//@Override
			public Direction getMove() {
				Direction result = nextMove;
				nextMove = null; //TODO welchen Sinn hat diese Zeile?
				return result;
			}

		}

		Controller ghostController = new Controller();

		Controller pacManController = new Controller();

		//@Override
		public ActorController getGhostController(GameInfo gameInfo,
				GhostInfo ghostInfo) {
			return ghostController;
		}

		//@Override
		public ActorController getPacManController(GameInfo gameInfo) {
			return pacManController;
		}

		public void setNextPacManMove(Direction dir) {
			pacManController.nextMove = dir;
		}

		public void setGhostMove(Direction dir) {
			ghostController.nextMove = dir;
		}

	}

	public static final class SimpleAutoPlayer implements ActorController {
		private final Direction direction;

		public SimpleAutoPlayer(Direction direction) {
			this.direction = direction;
		}

		//@Override
		public Direction getMove() {
			return direction;
		}
	}

	public static final int SECOND = 1000;

	/**
	 * Default timeout for the JUnit Tests.
	 */
	public static final long DEFAULT_TIMEOUT = 5 * SECOND;

	public static ControllerFactory getSimpleControllerFactory(
			final Direction direction) {
		final ActorController simpleAutoplayer = new SimpleAutoPlayer(direction);
		return new ControllerFactory() {
			//@Override
			public ActorController getGhostController(GameInfo info,
					GhostInfo ghost) {
				return simpleAutoplayer;
			}

			//@Override
			public ActorController getPacManController(GameInfo info) {
				return simpleAutoplayer;
			}
		};
	}

	public static void makeStep(PacManGame pmg) {
		pmg.setGhostMoveTime(1);
		pmg.setPacManMoveTime(1);
		pmg.step(2);
	}

	public static Field[][] getField(FieldType[][] fieldTypes) {
		Field[][] fields = new Field[fieldTypes.length][fieldTypes[0].length];

		for (int i = 0; i < fields.length; i++) {
			for (int j = 0; j < fields[i].length; j++) {
				fields[i][j] = new Field(fieldTypes[i][j]);
			}
		}
		return fields;
	}

	public static Board getBoard(FieldType[][] fieldTypes) {
		return new Board(getField(fieldTypes));
	}

}
