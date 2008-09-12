package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import dnl.games.stratego.Board;
import dnl.games.stratego.StrategoPiece;

/**
 * 
 */
class PiecesLayer extends PiecesGrid {

	private Image image;
	
	public PiecesLayer(ImageLoader imageLoader) {
		super(new Dimension(60, 60), 10, 10);
		this.setOpaque(false);
		image = imageLoader.getBoardImage();
		//imageOriginalSize = new Dimension(image.getWidth(this), image.getHeight(this));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
	}

	public void drawBoard(Board board) {
		gridPieces.clear();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				StrategoPiece piece = board.getPieceAt(i, j);
				if (piece != null) {
					//StrategoPieceUI pieceUI = 
						addPiece(i, j, piece);
					//addMouseHandling(pieceUI);
				}
			}
		}
		drawGrid();
	}

//	private void addMouseHandling(final StrategoPieceUI pieceUI) {
//		pieceUI.addMouseMotionListener(new MouseMotionListener(){
//
//			@Override
//			public void mouseDragged(MouseEvent e) {
//				// make sure that the moving piece is painted on top of everyone.
//				PiecesLayer.this.setComponentZOrder(pieceUI, 0);
//				Point point = SwingUtilities.convertPoint(pieceUI, e.getPoint(),PiecesLayer.this);
//				Location location = getMatchingLocation(point);
//				if(gridPieces.get(location) == null){
//					
//				}
//				//setGraphicLocation(pieceLabel, column, row);
//			}
//
//			@Override
//			public void mouseMoved(MouseEvent e) {
//			}
//		});
//		pieceUI.addMouseListener(new MouseAdapter(){
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				System.out.println(pieceUI.getStrategoPiece()+" - "+pieceUI.getLocationOnBoard());
//			}
//		});
//	}
	
}
