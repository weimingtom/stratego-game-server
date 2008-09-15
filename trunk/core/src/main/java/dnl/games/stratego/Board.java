package dnl.games.stratego;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 
 * @author daniel
 *
 */
public class Board {
	private static final Logger logger = Logger.getLogger("StrategoBoard");
	private StrategoPiece[][] squares = new StrategoPiece[10][10];

	private static final Map<String, Integer> initialPieceQuantities = new HashMap<String, Integer>();
	static {
		initialPieceQuantities.put(StrategoPieceType.MARSHAL.getName(), 1);
		initialPieceQuantities.put(StrategoPieceType.GENERAL.getName(), 1);
		initialPieceQuantities.put(StrategoPieceType.COLONEL.getName(), 2);
		initialPieceQuantities.put(StrategoPieceType.MAJOR.getName(), 3);
		initialPieceQuantities.put(StrategoPieceType.CAPTAIN.getName(), 4);
		initialPieceQuantities.put(StrategoPieceType.LIEUTENANT.getName(), 4);
		initialPieceQuantities.put(StrategoPieceType.SERGEANT.getName(), 4);
		initialPieceQuantities.put(StrategoPieceType.MINER.getName(), 5);
		initialPieceQuantities.put(StrategoPieceType.SCOUT.getName(), 8);
		initialPieceQuantities.put(StrategoPieceType.SPY.getName(), 1);
		initialPieceQuantities.put(StrategoPieceType.BOMB.getName(), 6);
		initialPieceQuantities.put(StrategoPieceType.FLAG.getName(), 1);
	}

	public StrategoPiece[][] getSquares() {
		return this.squares;
	}

	public void setSquares(StrategoPiece[][] squares) {
		this.squares = squares;
	}

	public StrategoPiece getPieceAt(Location location) {
		return getPieceAt(location.getRow(), location.getColumn());
	}

	public StrategoPiece getPieceAt(int row, int column) {
		return squares[row][column];
	}

	public void setPieceAt(Location location, StrategoPiece piece) {
		if(location.getColumn() >= 10 || location.getRow() >= 10){
			throw new IllegalArgumentException("no such location: "+location);
		}
	
		clearLocation(location);
		squares[location.getRow()][location.getColumn()] = piece;
	}

	/**
	 * If you're on the red side you see it all upside down.
	 */
	public void flip(){
		StrategoPiece[][] flippedSquares = new StrategoPiece[10][10];
		for (int i = 0; i < squares.length; i++) {
			for (int j = 0; j < squares[i].length; j++) {
				int newi = 9 - i;
				int newj = 9 - j;
				flippedSquares[newi][newj] = squares[i][j];
			}
		}
		this.squares = flippedSquares;
	}
	
	private StrategoPiece clearLocation(Location location) {
		StrategoPiece piece = getPieceAt(location);
		squares[location.getRow()][location.getColumn()] = null;
		return piece;
	}

	public List<Location> possibleMoves(Location from){
		List<Location> result = new ArrayList<Location>();
		
		return result;
	}
	
	public MoveResult movePiece(Location from, Location to){
		MoveResult moveResult = resolveMoveResult(from, to);
		// execute move
		switch (moveResult) {
		case ANIHILATION:
			clearLocation(from);
			clearLocation(to);
			break;
		case BLOWN:
		case EATEN:
			clearLocation(from);
			break;
		case EATS:
		case MOVE:
			StrategoPiece movingPiece = clearLocation(from);
			setPieceAt(to, movingPiece);
			break;
		}
		return moveResult;
	}
	
	private MoveResult resolveMoveResult(Location from, Location to){
		StrategoPiece piece1 = getPieceAt(from);
		logger.info("Attempting to move "+piece1+" from "+from+" to "+to);
		if(piece1.isBomb() || piece1.isFlag() || piece1.isUnknown()){
			return MoveResult.CANNOT_MOVE;
		}
		// cannot move in diagonals
		if(from.getRow()!= to.getRow() && from.getColumn() != to.getColumn()){
			return MoveResult.CANNOT_MOVE;
		}
		// only scouts can move more than one.  
		if(!piece1.isScout()){
			if(from.getRow() - to.getRow() > 1 || from.getColumn() - to.getColumn() > 1){
				return MoveResult.CANNOT_MOVE;
			}
		}
		else {
			// make sure that all squares in the trajectory are not populated
			if(from.getRow()!= to.getRow()){// if the movement is row-wise
				for (int i = from.getRow()+1; i <= to.getRow(); i++) {
					if(getPieceAt(i, from.getColumn()) != null){
						return MoveResult.CANNOT_MOVE;
					}
				}
			}
			else {// if the movement is column-wise
				for (int i = from.getColumn()+1; i <= to.getColumn(); i++) {
					if(getPieceAt(from.getRow(), i) != null){
						return MoveResult.CANNOT_MOVE;
					}
				}
			}
		}
		StrategoPiece piece2 = getPieceAt(to);
		if(piece2 == null){
			// ok, we're moving to an empty square
			return MoveResult.MOVE;
		}
		else if(piece2.getPlayer().equals(piece1.getPlayer())){
			return MoveResult.CANNOT_MOVE;
		}
		else if(piece2.isBomb()){
			if(piece1.isMiner()){
				// a miner eats a bomb
				return MoveResult.EATS;
			}
			else {
				// oops ... KABOOM!
				return MoveResult.BLOWN;
			}
		}
		else if(piece1.getType().getRank() == piece2.getType().getRank()){
			return MoveResult.ANIHILATION;
		}
		else if(piece1.getType().getRank() > piece2.getType().getRank()){
			return MoveResult.EATS;
		}
		else {
			return MoveResult.EATEN;
		}
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder("Stratego Board:\n   |==========|\n");
		for (int i = 0; i < 10; i++) {
			sb.append(" ");
			sb.append(i);
			sb.append(" |");
			for (int j = 0; j < 10; j++) {
				if(i>3 && i<6 && (j==2||j==3||j==6||j==7)){
					sb.append('0');
				}
				else {
					StrategoPiece piece = squares[i][j];
					char c = piece==null ? ' ': piece.getAbbreviatedName();
					sb.append(c);
				}
			}
			sb.append("|\n");
		}
		sb.append("   |==========|\n");
		sb.append("    0123456789 \n");
		return sb.toString();
	}

	public void setInitialLocations(PlayerType player, char[][] piecesLocation) {
		Map<String, Integer> pieceQuantities = new HashMap<String, Integer>();
		int startRowIndex = 0;
		int endRowIndex = 3;
		if (PlayerType.BLUE.equals(player)) {
			startRowIndex = 6;
			endRowIndex = 9;
		}
		for (int i = 0; i < 10; i++) {
			for (int j = startRowIndex; j < endRowIndex; j++) {
				char c = piecesLocation[i][j];
				StrategoPieceType type = StrategoPieceType.getByAbbreviatedName(c);
				Integer val = pieceQuantities.get(type.getName());
				if (val == null) {
					pieceQuantities.put(type.getName(), 1);
				} else {
					pieceQuantities.put(type.getName(), val + 1);
				}
			}
		}
	}

}
