package prog2.project5.autoplay;

import static prog2.project5.enums.Direction.*;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Set;


import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.GhostCharacter;
import prog2.project5.game.BoardInfo;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.GameInfo;
import prog2.project5.game.GhostInfo;

/**
 * An GhostAutoPlayer is able to move a ghost.
 * 
 */
public class GhostAutoPlayer implements ActorController {

	private final GameInfo info;
	private BoardInfo boardInfo;
	private int rows;
	private int columns;
	private GhostCharacter character;
	private Direction last;
	private List<Direction> directions = Arrays.asList(Direction.values());
	private Random r = new Random(24639876l);

	/**
	 * Creates a new GhostAutoPlayer for the given game and ghost.
	 * 
	 * @param info
	 *            information about the game.
	 * @param ghost
	 *            the ghost this AutoPlayer is created for.
	 * 
	 */
	public GhostAutoPlayer(GameInfo info, GhostInfo ghost) {
		this.info = info;
		boardInfo = info.getBoardInfo();
		rows = boardInfo.getNumberOfRows();
		columns = boardInfo.getNumberOfColumns();
		this.character = ghost.getCharacter();
	}

	/**
	 * Provides a move for a ghost. A ghost always moves towards Pac-Man, unless
	 * Pac-Man is in power pellet mode. Then the ghosts move randomly.
	 * 
	 * @see prog2.project5.autoplay.ActorController#getMove()
	 */
	//@Override
	public Direction getMove() {
		Point start = getGhostPosition();
		Direction result = null;
		if (info.isPowerPelletMode()) {
			result = getPowerPelletMove(start);
		} else {
			result = getMove(start).direction;
		}
		last = result;
		return result;
	}

	private Direction getPowerPelletMove(Point start) {
		if (last != null) {
			Point point = getPoint(start, last);
			if (canMove(point)) {
				return last;
			}
		}
		Collections.shuffle(directions, r);
		for (Direction direction : directions) {
			Point p = getPoint(start, direction);
			if (canMove(p)) {
				return direction;
			}
		}
		return null;

	}

	private Point getPoint(Point start, Direction direction) {
		Point result = null;
		switch (direction) {
		case LEFT:
			result = getPoint(start.x, start.y - 1);
			break;
		case RIGHT:
			result = getPoint(start.x, start.y + 1);
			break;
		case UP:
			result = getPoint(start.x - 1, start.y);
			break;
		case DOWN:
			result = getPoint(start.x + 1, start.y);
			break;
		}
		return result;

	}

	private boolean canMove(Point left) {
		FieldInfo fi = boardInfo.getFieldInfo(left);
		switch (fi.getType()) {
		case WALL:
			return false;
		case GHOST_START:
		case PACMAN_START:
		case FREE:
		case POWER_PELLET:
			break;
		}
		if (fi.getActorType() != null) {
			return false;
		}
		return true;
	}

	private Point getGhostPosition() {
		List<Point> ghostPositions = info.getGhostPositions();
		Point start = null;
		for (Point point : ghostPositions) {
			FieldInfo fieldInfo = boardInfo.getFieldInfo(point);
			GhostInfo ghostInfo = fieldInfo.getGhostInfo();
			if (character == ghostInfo.getCharacter()) {
				start = point;
			}
		}
		return start;
	}

	private static class Move {
		Point p;
		int dist;
		private final Direction direction;

		private Move(Point p, int dist, Direction d) {
			super();
			this.p = p;
			this.dist = dist;
			this.direction = d;
		}
	}

	private Move getMove(Point start) {
		LinkedList<Move> points = new LinkedList<Move>();
		Set<Point> checkedPoints = new HashSet<Point>();
		points.add(new Move(start, 0, null));
		while (points.size() > 0) {
			Move m = points.poll();


			FieldInfo fi = boardInfo.getFieldInfo(m.p);
			Direction dir = m.direction;
			if (fi.getActorType() == ActorType.PACMAN) {
				return m;
			}
			Point left = getPoint(m.p.x, m.p.y - 1);
			addHelper(points, checkedPoints, left, m.dist, dir != null ? dir
					: LEFT);
			Point right = getPoint(m.p.x, m.p.y + 1);
			addHelper(points, checkedPoints, right, m.dist, dir != null ? dir
					: RIGHT);
			Point up = getPoint(m.p.x - 1, m.p.y);
			addHelper(points, checkedPoints, up, m.dist, dir != null ? dir : UP);
			Point down = getPoint(m.p.x + 1, m.p.y);
			addHelper(points, checkedPoints, down, m.dist, dir != null ? dir
					: DOWN);
		}
		throw new RuntimeException("Seems that there is no Pac-Man reachable");
	}

	private void addHelper(LinkedList<Move> pInfos, Set<Point> checkedPoints,
			Point p, int dist, Direction dir) {
		FieldInfo fi = boardInfo.getFieldInfo(p);
		switch (fi.getType()) {
		case WALL:
			break;
		case GHOST_START:
		case PACMAN_START:
		case FREE:
		case POWER_PELLET:
			if (!checkedPoints.contains(p)) {
				pInfos.addLast(new Move(p, dist + 1, dir));
				checkedPoints.add(p);
			}
			break;
		}
	}

	private Point getPoint(int x, int y) {
		return new Point((x + rows) % rows, (y + columns) % columns);
	}

}