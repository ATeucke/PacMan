package prog2.project5.tests;

import static prog2.project5.enums.Direction.*;
import static prog2.project5.testutil.TestUtil.*;
import static org.junit.Assert.*;
import prog2.project5.game.PacMan;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

public class PacManPublicTest {

	private PacMan p;

	@Before
	public void setUp() {
		p = new PacMan(new Point(0, 0));
	}

	
	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPacManConstructor() {
		Point position = new Point(0, 0);
		PacMan p = new PacMan(position);
		assertEquals("You should save the position.", position, p.getPosition());
		assertEquals("A new Pac-Man looks to the left.", LEFT, p.getDirection());

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testToString() {
		PacMan g = new PacMan(new Point(22, 11));
		g.setDirection(DOWN);
		assertEquals("The string representation of the actor is wrong.",
				"[PACMAN, position: (22,11), direction: DOWN]", g.toString());

	}

}
