package prog2.project5.tests;

import static prog2.project5.enums.GhostCharacter.*;
import static prog2.project5.testutil.TestUtil.*;
import static org.junit.Assert.*;

import java.awt.Point;

import prog2.project5.game.Ghost;

import org.junit.Test;

public class GhostPublicTest {

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructorException1() {
		try {
			new Ghost(null, null);
			fail("Expected an exception, if null is given.");
		} catch (IllegalArgumentException e) {

		}

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testToString() {
		Ghost g = new Ghost(new Point(22, 11), OTOBOKE);
		assertEquals("The string representation of the actor is wrong.",
				"[GHOST, character: " + OTOBOKE
						+ ", position: (22,11), color: " + OTOBOKE.getColor()
						+ "]", g.toString());

	}
}
