package prog2.project5.game;

import java.awt.Point;

import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;

/**
 * FieldInfo provides information about a {@link Field} but does not allow to
 * manipulate it.
 * 
 */
public interface FieldInfo {

	/**
	 * Indicates whether or not there is a pac-dot on the field.
	 * 
	 * @return true, if the field has a pac-dot.
	 */
	boolean hasPacDot();

	/**
	 * Indicates whether or not there is a power pellet on the field.
	 * 
	 * @return true, if the field has a power pellet.
	 */
	boolean hasPowerPellet();

	/**
	 * Returns the type of the actor. If there is no actor on the field the
	 * method returns null.
	 * 
	 * @return the type of the actor or null.
	 */
	ActorType getActorType();
	 Direction getActorDirection(); //added
	/**
	 * Returns the extra item of this field. If there is no extra item on the
	 * field the method returns null, otherwise
	 * 
	 * @return the extraItem of this field or null.
	 */
	ExtraItem getExtraItem();

	/**
	 * Returns the type of this field.
	 * 
	 * @return the type of this field.
	 */
	FieldType getType();

	/**
	 * Returns a {@link GhostInfo} object for the ghost on this field, or null
	 * if there is no ghost on the field.
	 * 
	 * @return a {@link GhostInfo} object for the ghost on this field, or null.
	 */
	GhostInfo getGhostInfo();
	
	Point getOldPosition();
}