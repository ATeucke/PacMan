package prog2.project5.game;

import java.awt.Point;
import java.util.List;

import prog2.project5.enums.Direction;

public class MyGameInfo implements GameInfo {

	private PacManGame game ;
	
	public MyGameInfo(PacManGame game) {
		this.game = game;
	}
	//@Override
	public void addObserver(GameObserver observer) {
		game.addObserver(observer);
	}

//	@Override
	public BoardInfo getBoardInfo() {
		return game.getBoardInfo();
	}

//	@Override
	public List<Point> getGhostPositions() {
		return game.getGhostPositions();
	}

//	@Override
	public int getLives() {
		return game.getLives();
	}
	
//	@Override
	public Direction getPacManDirection() {
		return game.getPacManDirection();
	}

//	@Override
	public Point getPacManPosition() {
		return game.getPacManPosition();
	}

//	@Override
	public long getScore() {
		return game.getScore();
	}

//	@Override
	public boolean isGameOver() {
		return game.isGameOver();
	}

//	@Override
	public boolean isPowerPelletMode() {
		return game.isPowerPelletMode();
	}

//	@Override
	public void removeObserver(GameObserver observer) {
		game.removeObserver(observer);
	}
	
    
    /**
	 * A getter for pacManMoveDuration.
	 * 
	 * @return  pacManMoveDuration.
	 */
    public long getPacManMoveDuration() {
        return  game.getPacManMoveDuration();
    }
    /**
	 * A getter for ghostMoveDuration.
	 * 
	 * @return  ghostMoveDuration.
	 */
    public long getGhostMoveDuration() {
        return  game.getGhostMoveDuration();
    }
	//@Override
	public long getGhostMoveTime() {
		
		return game.getGhostMoveTime();
	}
	//@Override
	public long getPacManMoveTime() {
		
		return game.getPacManMoveTime();
	}
	//@Override
	public long getpowerduration() {
		
		return game.getpowerduration();
	}
	//@Override
	public Point getdeadManPosition() {
		// TODO Auto-generated method stub
		return game.getDeathpos();
	}
}
