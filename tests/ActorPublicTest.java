package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.Direction.*;
import static prog2.project5.enums.GhostCharacter.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;

import org.junit.Test;

import prog2.project5.autoplay.ActorController;
import prog2.project5.enums.Direction;
import prog2.project5.game.Actor;
import prog2.project5.game.Ghost;

public class ActorPublicTest {

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testSetPosition() {
		Actor g = new Ghost(new Point(0, 0), KIMAGURE);
		g.setPosition(20, 20);
		Point position = g.getPosition();
		assertEquals("Expeted different position", 20, position.x);
		assertEquals("Expeted different position", 20, position.y);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testAutoplayer() {
		Actor actor = new Ghost(new Point(0, 0), KIMAGURE);
		actor.initController(new ActorController() {

			//@Override
			public Direction getMove() {

				return LEFT;
			}

		});
		assertEquals("Expected other move from actor", LEFT, actor.getMove());
		assertEquals("Expected other move from actor", LEFT, actor.getMove());
	}

}
