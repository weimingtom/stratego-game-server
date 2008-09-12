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
			StrategoPieceUI pieceUI = addPiece(i, j, strategoPiece);
			addMouseHandling(pieceUI);
		}
	}
	
	public StrategoPieceUI getSelectedPieceUI() {
		return selectedPieceUI;
	}

	private void addMouseHandling(final StrategoPieceUI pieceUI){
//		pieceUI.addMouseListener(new MouseAdapter(){
//
//			@Override
//			public void mouseReleased(MouseEvent e) {
//				setGraphicLocation(pieceUI);
//			}
//
//			@Override
//			public void mousePressed(MouseEvent e) {
//				//super.mousePressed(e);
//				//boardUI.add
//			}
//			
//		});
//		pieceUI.addMouseMotionListener(new MouseMotionListener(){
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				selectedPieceUI = pieceUI;
//				Point p = SwingUtilities.convertPoint(pieceUI, e.getPoint(),PiecesTray.this);
//				Dimension2D ps = getPieceSize();
//				int x = p.x - (int)(ps.getWidth()/2);
//				int y = p.y - (int)(ps.getHeight()/2);
//				pieceUI.setBounds(x, y, (int)ps.getWidth(), (int)ps.getHeight());
//			}
//
//			@Override
//			public void mouseMoved(MouseEvent e) {
//				
//			}
//			
//		});
	}
}
