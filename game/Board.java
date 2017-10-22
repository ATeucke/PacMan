package prog2.project5.game;

import static prog2.project5.enums.FieldType.*;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import prog2.project5.enums.FieldType;

/**
 * The board class represents the board where Pac-Man can be played on.
 */
public class Board {

    /**
	 * The board that represents the map.
	 * 
	 * The position (0,0) represents the upper left corner.
	 */
    private Field[][] board;

    /**
	 * A list of fields where extra items can be placed (all fields of type
	 * FREE, POWER_PELLET, and PACMAN_START).
	 */
    private List<Point> extraItemFields = new ArrayList<Point>();

    /**
	 * A list of the start positions of the ghosts.
	 */
    private List<Point> ghostsStart = new ArrayList<Point>();

    /**
	 * The amount of pac-dots that are on the board when a new level begins.
	 */
    private int pacDotsOnStart;

    /**
	 * The position where Pac-Man starts.
	 */
    private Point pacManStart;

    /**
	 * Creates a new board with given rows and columns from the Field array. It
	 * counts the amount of free fields (where pac-dots can be placed), saves
	 * the start position of Pac-Man and the start position of the ghosts.
	 * 
	 * @param board
	 *            the board that specifies the map.
	 * @throws IllegalArgumentException
	 *             if the given board is null, has no FREE fields, not exactly
	 *             one Pac-Man start, a dimension is zero, the rows have
	 *             different length, or the given board contains a null.
	 */
    public Board(Field[][] board) {
    	if (board==null) throw new IllegalArgumentException("given board is null");
    	this.board = board;
    	int pacStarts = 0;
    	pacDotsOnStart =0;
    	if (board.length==0) throw new IllegalArgumentException("given board has dimension 0");
    	int boardLength = board[0].length;
    	if (boardLength==0) throw new IllegalArgumentException("given board has dimension 0");
		for(int a =0 ; a < board.length ; a++) 
    	{
			if (board[a].length!=boardLength) throw new IllegalArgumentException("given board's rows have diffent lenghts");
    		for(int b =0 ; b < boardLength ; b++) 
        	{
    			if (board[a][b]==null) throw new IllegalArgumentException("given board has null-field");
    			switch (board[a][b].getType())
    			{
    			case FREE: this.extraItemFields.add(new Point(a,b)); pacDotsOnStart++; break;
    			case WALL : break;
    			case PACMAN_START :this.extraItemFields.add(new Point(a,b)); this.pacManStart = new Point(a,b) ;pacStarts++; break;
    			case GHOST_START: this.ghostsStart.add(new Point(a,b)); break;
    			case POWER_PELLET : this.extraItemFields.add(new Point(a,b)); break;
    			}
    		}
    	}
		if (pacDotsOnStart==0) throw new IllegalArgumentException("given board has no free Fields");
    	if (pacStarts != 1) throw new IllegalArgumentException("given board has"+ pacStarts +" PACMAM_SATRT Fields");
    }

    /**
	 * Should be called, if a new game or a new stage starts. It initializes the
	 * fields, i.e. places pac-dots, resets the power pellets (indicated by the
	 * original board), removes all actors and extra items.
	 * 
	 */
    public void initBoard() {
    	for(int a =0 ; a < this.getNumberOfRows() ; a++) 
    	{
        	for(int b =0 ; b < this.getNumberOfColumns() ; b++) 
        	{
        		board[a][b].removeActor();
        		board[a][b].setExtraItem(null);
        		if (board[a][b].getType() == FREE) board[a][b].setPacDot(true);
        		else if(board[a][b].getType() == POWER_PELLET) board[a][b].setPowerPellet(true);
        	}
    	}
        return ;
    }

    /**
	 * Returns the underlying field array.
	 * 
	 * @return the underlying field array.
	 */
    public Field[][] getBoard() {
        return board;
    }

    /**
	 * 
	 * Returns a field specified by the given coordinates.
	 * 
	 * @param x
	 *            the x coordinate.
	 * @param y
	 *            the y coordinate.
	 * 
	 * @return the field at the given position.
	 * 
	 * @throws IllegalArgumentException
	 *             if there is no field at the coordinates.
	 * 
	 */
    public Field getField(int x, int y) {
       try { return board[x][y]; } catch (ArrayIndexOutOfBoundsException e) 
       {throw new IllegalArgumentException("given coordinates define no Field");}
    }

    /**
	 * Returns the position of the start fields of the ghosts.
	 * 
	 * @return the position of the start fields of the ghosts.
	 */
    public List<Point> getGhostsStart() {
        return this.ghostsStart;
    }

    /**
	 * Returns the amount of pac-dots at the start of the game.
	 * 
	 * @return the amount of pac-dots at the start of the game.
	 */
    public int getPacDotsOnStart() {
        return this.pacDotsOnStart;
    }

    /**
	 * Returns the position of the Pac-Man start field.
	 * 
	 * @return the position of the Pac-Man start field.
	 */
    public Point getPacManStart() {
        return this.pacManStart;
    }

    /**
	 * Returns the number of columns of the board.
	 * 
	 * @return the number of columns of the board.
	 */
    public int getNumberOfColumns() {
        return board[0].length;
    }

    /**
	 * Returns the number of rows of the board.
	 * 
	 * @return the number of rows of the board.
	 */
    public int getNumberOfRows() {
        return board.length;
    }

    /**
	 * Returns a board for given board description.
	 * 
	 * The Following characters are allowed and represent following field types. <br/>
	 * '#': WALL <br/>
	 * '-': FREE <br/>
	 * 'X': POWER_PELLET <br/>
	 * 'P': PACMAN_START <br/>
	 * 'G': GHOST_START<br/>
	 * 
	 * @param boardDescription
	 *            a description of the board, each entry represents one row.
	 * 
	 * @return a board for the given description.
	 * 
	 * @throws IllegalArgumentException
	 *             if the description contains a character that is not allowed.
	 */
    public static Board parse(String[] boardDescription) {
        Field[][] newBoard = new Field[boardDescription.length][];
        for(int a =0 ; a < newBoard.length ; a++) 
    	{
        	newBoard[a] = new Field[boardDescription[a].length()];
        	
        	for(int b =0 ; b < newBoard[a].length ; b++) 
        	{
    			switch (boardDescription[a].charAt(b))
    			{
    			case '#': newBoard[a][b]= new Field(WALL); break;
    			case '-' :newBoard[a][b]= new Field(FREE); break;
    			case 'X' :newBoard[a][b]= new Field(POWER_PELLET); break;
    			case 'P': newBoard[a][b]= new Field(PACMAN_START); break;
    			case 'G' : newBoard[a][b]= new Field(GHOST_START); break;
    			default : throw new IllegalArgumentException("the description contains a character that is not allowed");
    			}
    		}
    	}
        return new Board(newBoard);
    }

    /**
	 * Returns a list of all Fields where an extra item can be placed.
	 * 
	 * @return a list of all Fields where an extra item can be placed.
	 */
    public List<Point> getExtraItemFields() {
        return this.extraItemFields;
    }

    /**
	 * Returns a {@link BoardInfo} object for this Board.
	 * 
	 * @return a {@link BoardInfo} object for this Board.
	 */
    public BoardInfo getBoardInfo(){
    	
        return new MyBoardInfo(this);
    }
}