package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.Event.*;
import static prog2.project5.enums.ExtraItem.*;
import static prog2.project5.enums.GhostCharacter.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import prog2.project5.enums.ActorType;
import prog2.project5.enums.Event;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;
import prog2.project5.enums.GhostCharacter;
import static prog2.project5.enums.FieldType.*;
import prog2.project5.game.Field;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.Ghost;
import prog2.project5.game.PacMan;

public class FieldPublicTest {

	private Field freeField;
	private Field ghostStart;
	private Field pacManStart;
	private Field powerPellet;
	private Field wall;

	private Ghost ghost1;
	private Ghost ghost2;
	private PacMan pacMan;

	@Before
	public void setUp() throws Exception {
		freeField = new Field(FieldType.FREE);
		ghostStart = new Field(FieldType.GHOST_START);
		pacManStart = new Field(FieldType.PACMAN_START);
		powerPellet = new Field(FieldType.POWER_PELLET);
		wall = new Field(FieldType.WALL);
		freeField.setPacDot(true);
		ghost1 = new Ghost(new Point(0, 0), KIMAGURE);
		ghost2 = new Ghost(new Point(0, 0), OTOBOKE);
		pacMan = new PacMan(new Point(1, 1));

	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testConstructorException() {
		try {
			new Field(null);
			fail("Expected an exception if no field type is given.");
		} catch (IllegalArgumentException e) {

		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaceActor3() {
		Field a = new Field(FieldType.FREE);
		a.setExtraItem(ExtraItem.BANANA);
		a.setPacDot(true);

		a.placeActor(pacMan);
		assertNotNull("The actor wasn't set properly.", a.getActor());
		assertFalse("The pac-dot wasn't removed.", a.hasPacDot());
		assertEquals("The fieldype shouldn't be changed.", FieldType.FREE, a
				.getType());
		assertNull("The extra item wasn't removed", a.getExtraItem());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaceActorException1() {
		try {
			freeField.placeActor(null);
			fail("Expected an exception.");
		} catch (IllegalArgumentException e) {
		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testPlaceActorException4() {
		try {
			ghostStart.placeActor(pacMan);
			fail("Expected an exception.");
		} catch (IllegalStateException e) {
		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckActor1() {
		Event e = freeField.checkActor(pacMan);
		assertEquals("Expected other event", MOVE_POSSIBLE, e);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckActor2() {
		Event e = wall.checkActor(pacMan);
		assertEquals("Expected other event", MOVE_IMPOSSIBLE, e);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckActor5() {
		freeField.placeActor(ghost1);
		Event e = freeField.checkActor(pacMan);
		assertEquals("Expected other event", PACMAN_GHOST_COLLISION, e);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckActor6() {
		Event e = powerPellet.checkActor(ghost1);
		assertEquals("Expected other event", MOVE_POSSIBLE, e);
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testCheckActorException() {
		try {
			Event e = powerPellet.checkActor(null);
			fail("Expected Exception");
		} catch (IllegalArgumentException e) {
		}
	}

	

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testSetPowerPellet() {
		powerPellet.setPowerPellet(true);
		assertTrue("Expected power pellet on field", powerPellet
				.hasPowerPellet());
		powerPellet.setPowerPellet(false);
		assertFalse("Expected no power pellet on field", powerPellet
				.hasPowerPellet());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testSetExtraItem() {
		freeField.setExtraItem(BANANA);
		assertEquals("Expected extra item on field", BANANA, freeField
				.getExtraItem());

		freeField.setExtraItem(null);
		assertNull("Expected no extra item on field", freeField.getExtraItem());
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testSetExtraItemException1() {
		try {
			wall.setExtraItem(BANANA);
			fail("Expected Exception");
		} catch (IllegalStateException e) {
		}
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testToString1() {
		Field a = new Field(FieldType.FREE);
		assertEquals("The string representation of the field is wrong.",
				"[FREE, false, false]", a.toString());
	}
	@Test(timeout = DEFAULT_TIMEOUT)
	public void testToString2() {
		Field a = new Field(FieldType.FREE);
		a.setPacDot(true);
		a.placeActor(ghost1);
		assertEquals("The string representation of the field is wrong.",
				"[FREE, true, false, GHOST]", a.toString());
	}

}
