package prog2.project5.game;

import java.awt.Color;
import java.awt.Point;

import prog2.project5.enums.GhostCharacter;

public class MyGhostInfo implements GhostInfo {

	private Ghost ghost ;
	
	public MyGhostInfo(Ghost ghost) {
		this.ghost=ghost;
	}

//	@Override
	public GhostCharacter getCharacter() {
		return ghost.getCharacter();
	}

//	@Override
	public Color getColor() {
		return ghost.getColor();
	}

	//@Override
	public Point getPosition() {
		return ghost.getPosition();
	}
	
}
