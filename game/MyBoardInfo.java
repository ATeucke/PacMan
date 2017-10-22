package prog2.project5.game;

import java.awt.Point;
import java.util.List;

public class MyBoardInfo implements BoardInfo {

	 private Board board;
	
	public MyBoardInfo(Board board) {
		this.board=board;
	}

	//@Override
	public List<Point> getExtraItemFields() {
		return board.getExtraItemFields();
	}

//	@Override
	public FieldInfo getFieldInfo(Point p) {
		if (p==null) throw new IllegalArgumentException("given point is null");
    	return board.getField(p.x,p.y).getFieldInfo();
	}

//	@Override
	public FieldInfo getFieldInfo(int x, int y) {
		return board.getField(x,y).getFieldInfo();
	}

	//@Override
	public List<Point> getGhostsStart() {
		return board.getGhostsStart();
	}

	//@Override
	public int getNumberOfColumns() {
		return board.getNumberOfColumns();
	}

	//@Override
	public int getNumberOfRows() {
		return board.getNumberOfRows();
	}

//	@Override
	public int getPacDotsOnStart() {
		return board.getPacDotsOnStart();
	}

//	@Override
	public Point getPacManStart() {
		return board.getPacManStart();
	}

}
