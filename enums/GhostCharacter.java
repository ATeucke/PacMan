package prog2.project5.enums;

import static java.awt.Color.*;

import java.awt.Color;


/**
 * Represents the characters of the ghosts.
 * 
 */
public enum GhostCharacter {

	OIKAKE(RED), MACHIBUSE(PINK), KIMAGURE(CYAN), OTOBOKE(ORANGE);

	private final Color color;

	/**
	 * Returns the color of the ghost.
	 * 
	 * @return the color of the ghost.
	 */
	public Color getColor() {
		return color;
	}

	private GhostCharacter(Color color) {
		this.color = color;
	}
    public static GhostCharacter getCharacter(int e){
    	switch(e)
    	{
    	case 0: return OIKAKE; 
    	case 1:return MACHIBUSE; 
    	case 2:return KIMAGURE; 
    	case 3:return OTOBOKE; 
    	default :return null;
    	}
    }
}