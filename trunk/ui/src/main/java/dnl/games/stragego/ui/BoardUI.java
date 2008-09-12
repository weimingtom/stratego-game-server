package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dnl.games.stratego.Board;
import dnl.games.stratego.Location;
import dnl.games.stratego.PlayerType;

public class BoardUI extends JPanel {

	// private Board board = new Board();
	private JPanel mainContainer = new JPanel(new BorderLayout());
	private JLayeredPane layeredPane = new JLayeredPane();
	private ImageLoader imageLoader = new ImageLoader();
	private PiecesLayer piecesLayer = new PiecesLayer(imageLoader);
	private PiecesTray bluePiecesTray = new PiecesTray(PlayerType.BLUE, new Dimension(60, 60), 10, 4);
	private PiecesTray redPiecesTray = new PiecesTray(PlayerType.RED, new Dimension(60, 60), 10, 4);
	private JPanel glassPane = new JPanel(null);
	private PiecesGrid sourceGrid;
	private StrategoPieceUI selectedPiece;
	private MouseHandler mouseHandler = new MouseHandler();
	private MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
	private PlayerType playerType = PlayerType.BLUE;

	public BoardUI() {
		super(new BorderLayout());
		mainContainer.add(new PaddingPanel(piecesLayer, 20), BorderLayout.CENTER);
		mainContainer.add(new PaddingPanel(bluePiecesTray, 20), BorderLayout.WEST);
		mainContainer.add(new PaddingPanel(redPiecesTray, 20), BorderLayout.EAST);
		this.add(layeredPane, BorderLayout.CENTER);
		bluePiecesTray.setBorder(BorderFactory.createLoweredBevelBorder());
		redPiecesTray.setBorder(BorderFactory.createLoweredBevelBorder());
		mainContainer.setSize(mainContainer.getPreferredSize());
		glassPane.setSize(mainContainer.getPreferredSize());

		layeredPane.add(mainContainer, 1);
		glassPane.setBackground(new Color(200, 200, 200, 150));
		glassPane.setOpaque(false);
		layeredPane.add(glassPane, 2);

		layeredPane.setPreferredSize(mainContainer.getPreferredSize());
		layeredPane.moveToFront(glassPane);

	}

	public PlayerType getPlayerType() {
		return playerType;
	}

	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	public Board getBoard(){
		Board board = piecesLayer.getBoard();
		if(playerType.isRed()){
			board.flip();
		}
		return board;
	}
	
	public void setBoard(Board board) {
		if(playerType.isRed()){
			board.flip();
		}
		piecesLayer.drawBoard(board);
	}
	
	private StrategoPieceUI getPieceAt(Point glassPanePoint) {
		Point p = SwingUtilities.convertPoint(glassPane, glassPanePoint, bluePiecesTray);
		StrategoPieceUI pieceUI = bluePiecesTray.getPieceAt(p);
		if (pieceUI != null) {
			sourceGrid = bluePiecesTray;
		} else {
			p = SwingUtilities.convertPoint(glassPane, glassPanePoint, redPiecesTray);
			pieceUI = redPiecesTray.getPieceAt(p);
		}
		if (pieceUI == null) {
			p = SwingUtilities.convertPoint(glassPane, glassPanePoint, piecesLayer);
			pieceUI = piecesLayer.getPieceAt(p);
		}
		return pieceUI;
	}

	private PiecesGrid getMatchingGrid(Point glassPanePoint) {
		Point p = SwingUtilities.convertPoint(glassPane, glassPanePoint, bluePiecesTray);
		if (bluePiecesTray.getBounds().contains(p)) {
			return bluePiecesTray;
		}
		p = SwingUtilities.convertPoint(glassPane, glassPanePoint, redPiecesTray);
		if (redPiecesTray.getBounds().contains(p)) {
			return redPiecesTray;
		}
		p = SwingUtilities.convertPoint(glassPane, glassPanePoint, piecesLayer);
		if (piecesLayer.getBounds().contains(p)) {
			return piecesLayer;
		}
		return null;
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		if (enabled) {
			glassPane.addMouseListener(mouseHandler);
			glassPane.addMouseMotionListener(mouseMotionHandler);
		} else {
			glassPane.removeMouseListener(mouseHandler);
			glassPane.removeMouseMotionListener(mouseMotionHandler);
		}
		glassPane.setOpaque(!enabled);
	}

	private class MouseMotionHandler implements MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			if(selectedPiece == null){
				return;
			}
			int w = selectedPiece.getWidth();
			int h = selectedPiece.getHeight();
			selectedPiece.setBounds(e.getX() - w / 2, e.getY() - h / 2, w, h);
		}

		@Override
		public void mouseMoved(MouseEvent e) {

		}
	}

	private class MouseHandler extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mousePressed(MouseEvent e) {
			StrategoPieceUI pieceAt = getPieceAt(e.getPoint());
			if (pieceAt != null && pieceAt.getStrategoPiece().getPlayer().equals(playerType)) {
				selectedPiece = pieceAt;
				sourceGrid = getMatchingGrid(e.getPoint());
				Rectangle rectangle = SwingUtilities.convertRectangle(pieceAt, pieceAt.getBounds(),
						pieceAt);
				glassPane.add(pieceAt);
				pieceAt.setBounds(rectangle);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if(selectedPiece == null){
				return;
			}
			PiecesGrid grid = getMatchingGrid(e.getPoint());
			if (grid == piecesLayer) {
				Point pointOnBoard = SwingUtilities.convertPoint(glassPane, e.getPoint(),
						piecesLayer);
				Location location = piecesLayer.getMatchingLocation(pointOnBoard);
				piecesLayer.addPieceUI(location, selectedPiece);
			} else {
				sourceGrid.addPieceUI(selectedPiece.getLocationOnBoard(), selectedPiece);
			}
			selectedPiece = null;
			// for some reason the glass pane stays dirty after something moves
			// through it.
			// the repaint() clears it
			glassPane.repaint();
		}
	}

	public StrategoPieceUI getSelectedPieceUI() {
		if (bluePiecesTray.getSelectedPieceUI() != null) {
			return bluePiecesTray.getSelectedPieceUI();
		}
		return redPiecesTray.getSelectedPieceUI();
	}

}
