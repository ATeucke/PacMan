package prog2.project5.game;

import static prog2.project5.enums.ActorType.*;
import static prog2.project5.enums.Event.*;
import static prog2.project5.enums.FieldType.*;
import prog2.project5.enums.ActorType;
import prog2.project5.enums.Event;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;

/**
 * Represents a field of the board.
 * 
 */
public class Field {

    /**
	 * Indicates whether or not a pac-dot is on the field.
	 */
    private boolean pacDot;

    /**
	 * Indicates whether or not a power pellet is on the field.
	 */
    private boolean powerPellet;

    /**
	 * If no extra item is on the field, it is null. Otherwise it holds the
	 * extra item that is on the field.
	 */
    private ExtraItem extraItem;

    /**
	 * If no actor is on the field, it is null. Otherwise it holds the actor
	 * that is on the field.
	 */
    private Actor actor;

    /**
	 * The type of the field.
	 */
    private FieldType type;

    /**
	 * Creates a new field of given type.
	 * 
	 * @param type
	 *            The type of the field.
	 * @throws IllegalArgumentException
	 *             if the given type was null.
	 */
    public Field(FieldType type) {
    	if (type==null) throw new IllegalArgumentException("given type is null");
    	this.type= type;
    	pacDot=false;
    	powerPellet=false;
    	extraItem=null;
    }

    /**
	 * Method places the actor on this field. It is called whenever an actor
	 * enters the field. If Pac-Man enters the field, he eats all items that are
	 * on this field (power pellet, pac-dot, or extra item).
	 * 
	 * @param actor
	 *            The actor to enter the field.
	 * 
	 * @throws IllegalArgumentExecption
	 *             if a null is given.
	 * 
	 * @throws IllegalStateException
	 *             if the actor cannot move on this field, e.g checkActor would
	 *             return MOVE_IMPOSSIBLE.
	 */
    public void placeActor(Actor actor) {
    	if (actor==null) throw new IllegalArgumentException("given actor is null");
    	if (checkActor(actor) == Event.MOVE_IMPOSSIBLE) throw new IllegalStateException("MOVE_IMPOSSIBLE");
    	this.actor = actor;
    	if(actor instanceof PacMan) 
    	{
    		pacDot = false;
    		powerPellet = false;
    		extraItem = null;
    	}
    	return ;
    }

    /**
	 * Removes the actor from the field.
	 */
    public void removeActor() {
        actor= null;
        return ;
    }

    /**
	 * This method checks what happens if the given actor enters this field and
	 * returns the corresponding event. <br/>
	 * The events are as follows:<br/>
	 * 
	 * If the field is of type WALL no actor can enter the field
	 * (MOVE_IMPOSSIBLE is returned in this case).<br/>
	 * 
	 * If the field is of type GHOST_START only ghosts can enter the field
	 * (MOVE_IMPOSSIBLE is returned for Pac-Man).<br/>
	 * 
	 * Two ghosts cannot be on the same field, if the incoming actor is a ghost
	 * and there is already a ghost on this field MOVE_IMPOSSIBLE is returned.<br/>
	 * 
	 * If the incoming actor is Pac-Man and the actor on the field is a ghost or
	 * vice versa they collide, and PACMAN_GHOST_COLLISION is returned.<br/>
	 * 
	 * In all other cases MOVE_POSSIBLE is returned.
	 * 
	 * @param incomingActor
	 *            The actor that wants to enter the field.
	 * 
	 * @return The event that occurs if this actor would enter the field.
	 * 
	 * @throws IllegalArgumentException
	 *             if null is passed as incoming actor.
	 */
    public Event checkActor(Actor incomingActor) {
    	if (incomingActor==null) throw new IllegalArgumentException("null is passed as incoming actor");
    	if (type.equals(WALL)) return MOVE_IMPOSSIBLE ;
    	if (incomingActor instanceof PacMan) {
    		if (type == GHOST_START) return MOVE_IMPOSSIBLE;
    		if (actor == null) return  MOVE_POSSIBLE;
    		if (actor instanceof Ghost) return  PACMAN_GHOST_COLLISION;
    	}
    	else {
    		if (actor == null) return  MOVE_POSSIBLE;
    		if (actor instanceof Ghost) return MOVE_IMPOSSIBLE;
    		if (actor instanceof PacMan) return  PACMAN_GHOST_COLLISION;
    	}
    	return MOVE_POSSIBLE;
    }

    /**
	 * A getter for the actor on the field.
	 * 
	 * @return the actor.
	 */
    public Actor getActor() {
        return actor;
    }

    /**
	 * Indicates whether or not a pac-dot is on the field.
	 * 
	 * @return has a pac-dot?
	 */
    public boolean hasPacDot() {
        return pacDot;
    }

    /**
	 * Indicates whether or not a power pellet is on the field.
	 * 
	 * @return has a power pellet?.
	 */
    public boolean hasPowerPellet() {
        return powerPellet;
    }

    /**
	 * Indicates whether or not an extra item is on the field.
	 * 
	 * @return the extraItem.
	 */
    public ExtraItem getExtraItem() {
        return extraItem;
    }

    /**
	 * A getter for the field type.
	 * 
	 * @return the type of the field.
	 */
    public FieldType getType() {
        return type;
    }

    /**
	 * A setter for the pac-dot on the field. It is only allowed to place a
	 * pac-dot on fields of type FREE.
	 * 
	 * @param pacDot
	 *            true, if a pac-dot should be place on this field.
	 * 
	 * @throws IllegalStateException
	 *             if the field is not of type FREE.
	 */
    public void setPacDot(boolean pacDot) {
    	if (type !=  FREE) throw new IllegalStateException("he field is not of type FREE");
    	this.pacDot = pacDot; 
    	return ;
    }

    /**
	 * A setter for the power pellet on the field. It is only allowed to place a
	 * power pellet on a field of type POWER_PELLET.
	 * 
	 * @param pacDot
	 *            the pacDot to set
	 * 
	 * @throws IllegalStateException
	 *             if the field is not of type POWER_PELLET.
	 */
    public void setPowerPellet(boolean powerPellet) {
    	if (type !=  POWER_PELLET) throw new IllegalStateException("he field is not of type POWER_PELLET");
    	this.powerPellet= powerPellet;
    	return ;
    }

    /**
	 * A setter for the extra item on the field. It is only allowed to place an
	 * extra item on a field of type POWER_PELLET or FREE or PACMAN_START.
	 * 
	 * 
	 * @param extraItem
	 *            the extraItem to set.
	 * 
	 * @throws IllegalStateException
	 *             if the field is not of type POWER_PELLET, FREE, or
	 *             PACMAN_START
	 */
    public void setExtraItem(ExtraItem extraItem) {
    	if(extraItem == null) {
    		this.extraItem = extraItem;
    		return;
    	}
    	if (type ==  WALL || type == GHOST_START) throw new IllegalStateException("the field is not of type POWER_PELLET, FREE, or PACMAN_START");
    	this.extraItem = extraItem;
    	return ;
    }

    /**
	 * A toString-Method. The string should have the following format: <br>
	 * <br>
	 * [FieldType, PacDot?, PowerPellet?, ExtraItem, ActorType] <br>
	 * <br>
	 * The last two entries are optional. If they are null, they don't appear in
	 * the result string. <br>
	 * <br>
	 * Examples: <br>
	 * <br>
	 * [FREE, true, false, CHERRY, GHOST] <br>
	 * <br>
	 * [PACMANSTART, false, false]
	 * 
	 * 
	 * @return the string.
	 */
    public String toString() {
        String s = "[" + type +", "+ pacDot +", "+ powerPellet;
        if (extraItem != null) s =s + ", " + extraItem;
        if (actor != null) s =s + ", " + actor.getType();
        s = s + "]";
        return s;
    }

    /**
	 * Returns a {@link FieldInfo} for this field.
	 * 
	 * @return a {@link FieldInfo} for this field.
	 */
    public FieldInfo getFieldInfo() {
        //TODO Complete implementation
        return new MyFieldInfo(this);
    }
}