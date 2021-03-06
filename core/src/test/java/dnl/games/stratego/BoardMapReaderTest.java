package dnl.games.stratego;

import junit.framework.Assert;

import org.junit.Test;


public class BoardMapReaderTest {

	private static final StrategoPiece BLUE_MARSHALL = new StrategoPiece(PlayerType.BLUE, StrategoPieceType.MARSHAL);
	private static final StrategoPiece BLUE_GENERAL = new StrategoPiece(PlayerType.BLUE, StrategoPieceType.GENERAL);

	private static final StrategoPiece RED_MARSHALL = new StrategoPiece(PlayerType.RED, StrategoPieceType.MARSHAL);
	private static final StrategoPiece RED_SCOUT = new StrategoPiece(PlayerType.RED, StrategoPieceType.SCOUT);
	
	@Test
	public void testReadBlueSimpleFile() throws Exception {
		Board board = BoardMapReader.readResource("test.blue.initialpos1.stratego.map");
		Assert.assertEquals(BLUE_MARSHALL, board.getPieceAt(0, 0));
		Assert.assertEquals(BLUE_GENERAL, board.getPieceAt(0, 1));
	}

	@Test
	public void testReadRedSimpleFile() throws Exception {
		Board board = BoardMapReader.readResource("test.red.initialpos1.stratego.map");
		Assert.assertEquals(RED_MARSHALL, board.getPieceAt(6, 0));
		Assert.assertEquals(RED_SCOUT, board.getPieceAt(9, 0));
	}

	@Test
	public void testImpossibleMoves() throws Exception {
		Board board = BoardMapReader.readResource("test1.stratego.map");
		MoveResult moveResult = board.movePiece(new Location(0,0), new Location(3,0));
		Assert.assertEquals(MoveResult.CANNOT_MOVE, moveResult);
	}

	@Test
	public void testScoutScouting() throws Exception {
		Board board = BoardMapReader.readResource("test1.stratego.map");
		System.err.println(board.toString());
		MoveResult moveResult = board.movePiece(new Location(6,0), new Location(3,0));
		Assert.assertEquals(MoveResult.ANIHILATION, moveResult);
	}

	@Test
	public void testScoutScoutingABomb() throws Exception {
		Board board = BoardMapReader.readResource("test1.stratego.map");
		MoveResult moveResult = board.movePiece(new Location(6,8), new Location(3,8));
		Assert.assertEquals(MoveResult.BLOWN, moveResult);
	}

	@Test
	public void testUnknownEnemy() throws Exception {
		Board board = BoardMapReader.readResource("test2.stratego.map");
		StrategoPiece piece = board.getPieceAt(0, 0);
		Assert.assertEquals(StrategoPieceType.UNKNOWN, piece.getType());
	}
	
}
