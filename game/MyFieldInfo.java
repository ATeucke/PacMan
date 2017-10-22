package prog2.project5.game;

import java.awt.Point;

import prog2.project5.enums.ActorType;
import prog2.project5.enums.Direction;
import prog2.project5.enums.ExtraItem;
import prog2.project5.enums.FieldType;

public class MyFieldInfo implements FieldInfo {

	private Field field;
	
	public MyFieldInfo(Field field) {
		this.field= field;
	}

//	@Override
	public ActorType getActorType() {
		if(field.getActor()==null) return null;
		return field.getActor().getType();
	}
//	@Override
	public Direction getActorDirection() {
		if(field.getActor()== null) return null; 
		if(field.getActor().getType() == ActorType.PACMAN ) 
		return ((PacMan)field.getActor()).getDirection();
		if(field.getActor().getType() == ActorType.GHOST ) 
			return ((Ghost)field.getActor()).getDirection();
		 return null;
	}
	//@Override
	public ExtraItem getExtraItem() {
		return field.getExtraItem();
	}

	//@Override
	public GhostInfo getGhostInfo() {
		Actor ghost = field.getActor();
		if (ghost instanceof Ghost )return new MyGhostInfo((Ghost)ghost);
		return null;
	}

//	@Override
	public FieldType getType() {
		return field.getType();
	}

//	@Override
	public boolean hasPacDot() {
		return field.hasPacDot();
	}

//	@Override
	public boolean hasPowerPellet() {
		return field.hasPowerPellet();
	}
	//@Override
    public Point getOldPosition() {
    	if(field.getActor()==null) throw new IllegalArgumentException("no actor on field ->no old position");
        if(field.getActor().getOldPosition()==null) return field.getActor().getPosition();
    	return  field.getActor().getOldPosition();
    }
}
