package prog2.project5.tests;

import static org.junit.Assert.*;
import static prog2.project5.enums.ExtraItem.*;
import static prog2.project5.enums.FieldType.*;
import static prog2.project5.testutil.TestUtil.*;

import java.awt.Point;

import org.junit.Before;
import org.junit.Test;

import prog2.project5.enums.GhostCharacter;
import prog2.project5.game.Field;
import prog2.project5.game.FieldInfo;
import prog2.project5.game.Ghost;
import prog2.project5.game.GhostInfo;

public class FieldInfoPublicTest {

	private FieldInfo fieldInfo;
	private Field f;

	@Before
	public void setUp() {
		f = new Field(FREE);
		fieldInfo = f.getFieldInfo();
	}

	@Test(timeout = DEFAULT_TIMEOUT)
	public void testGetExtraItem() {
		f.setExtraItem(BANANA);
		assertEquals("Expected extra item on field", fieldInfo.getExtraItem(),
				BANANA);
	}

}
