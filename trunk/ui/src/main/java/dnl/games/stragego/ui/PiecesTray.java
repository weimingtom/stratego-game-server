package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Dimension2D;
import java.util.List;

import javax.swing.SwingUtilities;

import dnl.games.stratego.PlayerType;
import dnl.games.stratego.StrategoPiece;

public class PiecesTray extends PiecesGrid {

	private PlayerType playerType;

	public PiecesTray(PlayerType playerType, Dimension pieceSize, int numberOfRows, int numberOfColumns) {
		super(pieceSize, numberOfRows, numberOfColumns);
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
	
	private void addMouseHandling(final StrategoPieceUI pieceUI){
		pieceUI.addMouseMotionListener(new MouseMotionListener(){
			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = SwingUtilities.convertPoint(pieceUI, e.getPoint(),PiecesTray.this);
				Dimension2D ps = getPieceSize();
				int x = p.x - (int)(ps.getWidth()/2);
				int y = p.y - (int)(ps.getHeight()/2);
				pieceUI.setBounds(x, y, (int)ps.getWidth(), (int)ps.getHeight());
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				
			}
			
		});
	}
}
