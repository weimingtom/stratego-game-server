package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import dnl.games.stratego.Board;
import dnl.games.stratego.PlayerType;

public class BoardUI extends JPanel{

	private Board board = new Board();
	
	private JLayeredPane layeredPane = new JLayeredPane();
	private ImageLoader imageLoader = new ImageLoader();
	private BoardLayer2 boardLayer = new BoardLayer2(imageLoader);
	private PiecesLayer piecesLayer = new PiecesLayer(imageLoader);
	PiecesTray bluePiecesTray = new PiecesTray(PlayerType.BLUE, new Dimension(60, 60), 10, 4);
	PiecesTray redPiecesTray = new PiecesTray(PlayerType.RED, new Dimension(60, 60), 10, 4);
	private MovesListener movesListener;
	
	public BoardUI(){
		super(new BorderLayout());
		this.add(new PaddingPanel(layeredPane, 20), BorderLayout.CENTER);
		this.add(new PaddingPanel(bluePiecesTray, 20), BorderLayout.WEST);
		this.add(new PaddingPanel(redPiecesTray, 20), BorderLayout.EAST);
		bluePiecesTray.setBorder(BorderFactory.createLoweredBevelBorder());
		redPiecesTray.setBorder(BorderFactory.createLoweredBevelBorder());
		piecesLayer.setSize(boardLayer.getPreferredSize());
		boardLayer.setSize(boardLayer.getPreferredSize());
		layeredPane.add(boardLayer, 2);
		layeredPane.add(piecesLayer, 1);
		layeredPane.moveToFront(piecesLayer);
		layeredPane.setPreferredSize(boardLayer.getPreferredSize());
		layeredPane.setMinimumSize(boardLayer.getPreferredSize());
		layeredPane.setBackground(Color.green);
	}
	
	public void addMovesListener(MovesListener movesListener){
		this.movesListener = movesListener;
	}
	
	public void showBoard(Board board){
		piecesLayer.drawBoard(board);
	}
}
