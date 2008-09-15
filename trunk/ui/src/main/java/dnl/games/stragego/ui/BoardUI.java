package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dnl.games.stratego.Board;
import dnl.games.stratego.Location;
import dnl.games.stratego.PlayerType;
import dnl.util.ui.Panels;

/**
 * The main UI class that displays a Stratego board and two trays, one for each
 * player.
 * 
 * @author Daniel Ore
 */
public class BoardUI extends JPanel {

	private final Color DISABLED_BACKGROUND = new Color(200, 200, 200, 150);

	private JPanel mainContainer = new JPanel(new BorderLayout());
	private JLayeredPane layeredPane = new JLayeredPane();
	private ImageLoader imageLoader = new ImageLoader();
	private PiecesLayer piecesLayer = new PiecesLayer(imageLoader);
	private JPanel glassPane = new JPanel(null);
	private StrategoPieceUI selectedPiece;
	private MouseHandler mouseHandler = new MouseHandler();
	private MouseMotionHandler mouseMotionHandler = new MouseMotionHandler();
	private PiecesTray bluePiecesTray = new PiecesTray(PlayerType.BLUE, new Dimension(60, 60), 10,
			4);
	private PiecesTray redPiecesTray = new PiecesTray(PlayerType.RED, new Dimension(60, 60), 10, 4);

	// by default the player is blue. when a game starts the
	// actuall type is assigned
	private PlayerType playerType = PlayerType.BLUE;

	// the ui can be in editing mode or playing mode.
	private UiStatus uiStatus;

	public BoardUI() {
		super(new BorderLayout());
		initUI();
	}

	/**
	 * Sets the status. This affects what the user can and cannot do with the
	 * board.
	 * 
	 * @param uiStatus
	 */
	public void setUiStatus(UiStatus uiStatus) {
		this.uiStatus = uiStatus;
	}

	/**
	 * Is this player red or blue?
	 * 
	 * @return
	 */
	public PlayerType getPlayerType() {
		return playerType;
	}

	/**
	 * Sets the player's type. If red, the board need to be rotated.
	 * 
	 * @param playerType
	 */
	public void setPlayerType(PlayerType playerType) {
		this.playerType = playerType;
	}

	/**
	 * Retrieves the board as it is seen by the player that is using it.
	 * 
	 * @return
	 */
	public Board getBoard() {
		Board board = piecesLayer.getBoard();
		if (playerType.isRed()) {
			board.flip();
		}
		return board;
	}

	/**
	 * Sets the board that the player sees.
	 * 
	 * @param board
	 */
	public void setBoard(Board board) {
		glassPane.removeAll();
		if (playerType.isRed()) {
			board.flip();
		}
		piecesLayer.drawBoard(board);
		repaint();
	}

	// ---------- UI ----------------------------------------------------------

	private void initUI() {
		mainContainer.add(new PaddingPanel(piecesLayer, 20), BorderLayout.CENTER);
		mainContainer.add(new PaddingPanel(bluePiecesTray, 20), BorderLayout.WEST);
		mainContainer.add(new PaddingPanel(redPiecesTray, 20), BorderLayout.EAST);
		this.add(layeredPane, BorderLayout.CENTER);

		// borders add a more polished look
		bluePiecesTray.setBorder(BorderFactory.createLoweredBevelBorder());
		redPiecesTray.setBorder(BorderFactory.createLoweredBevelBorder());

		// since the containers are within a JLayeredPane they must be given a
		// size.
		mainContainer.setSize(mainContainer.getPreferredSize());
		glassPane.setSize(mainContainer.getPreferredSize());

		layeredPane.add(mainContainer, 1);
		glassPane.setBackground(DISABLED_BACKGROUND);
		glassPane.setOpaque(false);
		layeredPane.add(glassPane, 2);
		layeredPane.setPreferredSize(mainContainer.getPreferredSize());
		layeredPane.moveToFront(glassPane);
	}

	/**
	 * Displays a user option on top of the board.
	 * 
	 * @param message
	 * @param action
	 */
	public void displayUserOption(String message, Action action) {
		JButton jb = new JButton(action);
		JLabel jl = new JLabel(message);
		final JPanel jp = new JPanel(new BorderLayout());
		jp.setBackground(DISABLED_BACKGROUND);
		JPanel np1 = Panels.newPanel(jb);
		JPanel np2 = Panels.newPanel(jl);
		np1.setBackground(DISABLED_BACKGROUND);
		np2.setBackground(DISABLED_BACKGROUND);
		jp.add(np2, BorderLayout.CENTER);
		jp.add(np1, BorderLayout.SOUTH);
		glassPane.add(jp);
		Rectangle r = piecesLayer.getBounds();
		r = SwingUtilities.convertRectangle(piecesLayer, r, glassPane);
		jp.setBounds((int) (r.x * 1.3), (int) (r.y * 1.4), (int) (r.width * .6),
				(int) (r.height * .2));
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				glassPane.remove(jp);
			}
		});
	}

	/**
	 * A utility method for the UI logic that gets the piece at the given point.
	 * 
	 * @param glassPanePoint
	 *            a point at glassPane coordinates.
	 * @return
	 */
	private StrategoPieceUI getPieceAt(Point glassPanePoint) {
		Point p = SwingUtilities.convertPoint(glassPane, glassPanePoint, bluePiecesTray);
		StrategoPieceUI pieceUI = bluePiecesTray.getPieceAt(p);
		if (pieceUI == null) {
			p = SwingUtilities.convertPoint(glassPane, glassPanePoint, redPiecesTray);
			pieceUI = redPiecesTray.getPieceAt(p);
		}
		if (pieceUI == null) {
			p = SwingUtilities.convertPoint(glassPane, glassPanePoint, piecesLayer);
			pieceUI = piecesLayer.getPieceAt(p);
		}
		return pieceUI;
	}

	/**
	 * Gets the grid that matches the given glassPane location.
	 * 
	 * @param glassPanePoint
	 * @return
	 */
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
			if (selectedPiece == null) {
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
			if (SwingUtilities.isRightMouseButton(e)) {
				PiecesGrid grid = getMatchingGrid(e.getPoint());
				if (grid == piecesLayer) {
					// Point pointOnBoard =
					// SwingUtilities.convertPoint(glassPane, e.getPoint(),
					// grid);
					selectedPiece = getPieceAt(e.getPoint());
					if (selectedPiece == null)
						return;
					restorePieceToTray();
					glassPane.repaint();
				}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			StrategoPieceUI pieceAt = getPieceAt(e.getPoint());
			if (pieceAt != null && pieceAt.getStrategoPiece().getPlayer().equals(playerType)) {
				selectedPiece = pieceAt;
				// sourceGrid = getMatchingGrid(e.getPoint());
				Rectangle rectangle = SwingUtilities.convertRectangle(pieceAt, pieceAt.getBounds(),
						pieceAt);
				glassPane.add(pieceAt);
				pieceAt.setBounds(rectangle);
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			if (selectedPiece == null) {
				return;
			}
			PiecesGrid grid = getMatchingGrid(e.getPoint());
			boolean moveFailed = false;
			Location location = null;

			if (grid == null) {
				moveFailed = true;
			} else {

				Point pointOnBoard = SwingUtilities.convertPoint(glassPane, e.getPoint(), grid);
				location = selectedPiece.getSourceGrid().getMatchingLocation(pointOnBoard);
				StrategoPieceUI pieceAt = grid.getPieceAt(location);
				if (pieceAt == selectedPiece) {
					selectedPiece = null;
					return;
				} else if (grid == piecesLayer) {
					if (UiStatus.EDITING.equals(uiStatus)) {
						if (location.getRow() < 6 || pieceAt != null) {
							moveFailed = true;
						}
					}
				} else if (grid == selectedPiece.getSourceGrid()) {
					moveFailed = true;
				} else {
					return;
				}

			}
			if (moveFailed) {
				restorePieceToTray();
			} else {
				selectedPiece.getSourceGrid().removePieceUI(selectedPiece);
				piecesLayer.addPieceUI(location, selectedPiece);
			}
			selectedPiece = null;
			// for some reason the glass pane stays dirty after something moves
			// through it.
			// the repaint() clears it
			glassPane.repaint();
		}
	}

	private void restorePieceToTray() {
		PiecesGrid sourceGrid = selectedPiece.getSourceGrid();
		sourceGrid.addPieceUI(selectedPiece.getSourceGridLocation(), selectedPiece);
	}

	public StrategoPieceUI getSelectedPieceUI() {
		if (bluePiecesTray.getSelectedPieceUI() != null) {
			return bluePiecesTray.getSelectedPieceUI();
		}
		return redPiecesTray.getSelectedPieceUI();
	}

}
