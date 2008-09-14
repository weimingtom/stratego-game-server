package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.util.List;

import dnl.games.stratego.PlayerType;
import dnl.games.stratego.StrategoPiece;

public class PiecesTray extends PiecesGrid {

	private PlayerType playerType;
	private StrategoPieceUI selectedPieceUI;
	//private BoardUI boardUI;
	
	public PiecesTray(PlayerType playerType, Dimension pieceSize, int numberOfRows, int numberOfColumns) {
		super(pieceSize, numberOfRows, numberOfColumns);
		//this.boardUI = boardUI;
		this.setOpaque(true);
		this.playerType = playerType;
		vanilla();
		drawGrid();
	}
	
	private void vanilla() {
		gridPieces.clear();
		List<StrategoPiece> pieces = StrategoPiece.enumerateInitialPieces(playerType);
		
		int k = 0;
		for (StrategoPiece strategoPiece : pieces) {
			int i = k / 4;
			int j = k % 4;
			k++;
			addPiece(i, j, strategoPiece);
		}
	}
	
	public StrategoPieceUI getSelectedPieceUI() {
		return selectedPieceUI;
	}

}
