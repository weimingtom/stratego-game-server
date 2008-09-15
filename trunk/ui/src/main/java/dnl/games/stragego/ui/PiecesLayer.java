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
					addPiece(i, j, piece);
				}
			}
		}
		drawGrid();
	}

}
