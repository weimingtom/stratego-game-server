package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.SwingUtilities;

import dnl.games.stratego.Board;
import dnl.games.stratego.Location;
import dnl.games.stratego.StrategoPiece;

/**
 * 
 */
class PiecesLayer extends PiecesGrid {

	public PiecesLayer(ImageLoader imageLoader) {
		super(new Dimension(60, 60), 10, 10);
		this.setOpaque(false);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mouseEntered(MouseEvent e) {
				System.err.println("entered");
			}

			@Override
			public void mouseExited(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}
			
		});
	}

	public void drawBoard(Board board) {
		gridPieces.clear();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				StrategoPiece piece = board.getPieceAt(i, j);
				if (piece != null) {
					StrategoPieceUI pieceUI = addPiece(i, j, piece);
					addMouseHandling(pieceUI);
				}
			}
		}
		drawGrid();
	}

	private void addMouseHandling(final StrategoPieceUI pieceUI) {
		pieceUI.addMouseMotionListener(new MouseMotionListener(){

			@Override
			public void mouseDragged(MouseEvent e) {
				// make sure that the moving piece is painted on top of everyone.
				PiecesLayer.this.setComponentZOrder(pieceUI, 0);
				Point point = SwingUtilities.convertPoint(pieceUI, e.getPoint(),PiecesLayer.this);
				Location location = getMatchingLocation(point);
				if(gridPieces.get(location) == null){
					
				}
				//setGraphicLocation(pieceLabel, column, row);
			}

			@Override
			public void mouseMoved(MouseEvent e) {
			}
		});
		pieceUI.addMouseListener(new MouseAdapter(){
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(pieceUI.getStrategoPiece()+" - "+pieceUI.getLocationOnBoard());
			}
		});
	}
	
//	private void setGraphicLocation(StrategoPieceUI pieceUI, int row, int column) {
//		double x = (column * cellWidth)+cellWidth*.1;
//		double y = (row * cellHeight)+cellWidth*.1;
//		pieceUI.setBounds((int)x, (int)y, (int) (cellWidth*.8), (int) (cellHeight*.8));
//	}
	
//	private void calculateCellDimensions(){
//		cellWidth = getWidth();
//		cellWidth = cellWidth / 10;
//		cellHeight = getHeight();
//		cellHeight = cellHeight / 10;
//	}
//	
//	@Override
//	public void setSize(int width, int height) {
//		super.setSize(width, height);
//		calculateCellDimensions();
//	}
//
//	@Override
//	public void setBounds(int x, int y, int width, int height) {
//		super.setBounds(x, y, width, height);
//		calculateCellDimensions();
//	}
//
//	@Override
//	public void setBounds(Rectangle r) {
//		super.setBounds(r);
//		calculateCellDimensions();
//	}
//
//	@Override
//	public void setSize(Dimension d) {
//		super.setSize(d);
//		calculateCellDimensions();
//	}
	
}
