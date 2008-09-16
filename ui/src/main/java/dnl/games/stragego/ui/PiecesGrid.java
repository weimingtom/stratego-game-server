package dnl.games.stragego.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Dimension2D;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import dnl.games.stratego.Board;
import dnl.games.stratego.Location;
import dnl.games.stratego.StrategoPiece;

public class PiecesGrid extends JPanel {

	private Dimension2D squareSize;
	private int numberOfRows; 
	private int numberOfColumns;
	protected Map<Location, StrategoPieceUI> gridPieces = new HashMap<Location, StrategoPieceUI>();

	public PiecesGrid(Dimension pieceSize, int numberOfRows, int numberOfColumns) {
		super(null);
		this.setOpaque(true);
		this.squareSize = pieceSize;
		this.numberOfRows = numberOfRows;
		this.numberOfColumns = numberOfColumns;
		double width = pieceSize.width*numberOfColumns;
		double height = pieceSize.height*numberOfRows;
		setPreferredSize(new Dimension((int)width,(int)height));
		setBackground(Color.lightGray);
		drawGrid();
	}
	
	public Dimension2D getSquareSize() {
		return squareSize;
	}

	public Dimension2D getPieceSize() {
		return new Dimension((int)(squareSize.getWidth()*.8), (int)(squareSize.getHeight()*.8));
	}
	
	public double getSquareWidth(){
		return squareSize.getWidth();
	}

	public double getSquareHeight(){
		return squareSize.getHeight();
	}

	public Board getBoard(){
		Board board = new Board();
		Set<Entry<Location, StrategoPieceUI>> entrySet = gridPieces.entrySet();
		for (Entry<Location, StrategoPieceUI> me : entrySet) {
			Location location = me.getKey();
			StrategoPiece strategoPiece = me.getValue().getStrategoPiece();
			board.setPieceAt(location, strategoPiece);
		}
		return board;
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		double w = getWidth();
		double h = getHeight();
		
		g2.setColor(Color.red.darker());
		
		for (int i = 0; i < numberOfRows; i++) {
			int y = (int)((h/numberOfRows)*i);
			g2.drawLine(0, y, (int)w, y);
		}

		for (int i = 0; i < numberOfColumns; i++) {
			int x = (int)((w/numberOfColumns)*i);
			g2.drawLine(x, 0, x, (int)h);
		}
	}
	
	public void drawGrid() {
		Set<Entry<Location, StrategoPieceUI>> entrySet = gridPieces.entrySet();
		for (Entry<Location, StrategoPieceUI> entry : entrySet) {
			//Location location = entry.getKey();
			StrategoPieceUI pieceUI = entry.getValue();
			if(pieceUI.getStrategoPiece().getPlayer().isBlue()){
				pieceUI.setBackground(Color.blue);
				pieceUI.setForeground(Color.white);
				pieceUI.setHorizontalTextPosition(SwingConstants.CENTER);
			}
			else {
				pieceUI.setBackground(Color.red);
			}
			pieceUI.setBorder(BorderFactory.createLineBorder(Color.black));
			setGraphicLocation(pieceUI);
			pieceUI.setOpaque(true);
		}
	}
	
	public void restorePiece(Location location){
		StrategoPieceUI pieceUI = gridPieces.get(location);
		if(pieceUI != null){
			addPieceUI(location, pieceUI);
		}
	}
	
	public StrategoPieceUI getPieceAt(Location location){
		return gridPieces.get(location);
	}
	
	public StrategoPieceUI getPieceAt(Point p){
		Location location = getMatchingLocation(p);
		return gridPieces.get(location);
	}
	
	protected Location getMatchingLocation(Point p){
		int column = (int)(p.x/getSquareWidth());
		int row = (int)(p.y/getSquareHeight());
		return new Location(row, column);
	}
	
	protected void setGraphicLocation(StrategoPieceUI pieceUI) {
		int column = pieceUI.getLocationOnBoard().getColumn();
		int row = pieceUI.getLocationOnBoard().getRow();
		double cellWidth = squareSize.getWidth();
		double cellHeight = squareSize.getHeight();
		double x = (column * cellWidth)+cellWidth*.1;
		double y = (row * cellHeight)+cellWidth*.1;
		pieceUI.setBounds((int)x, (int)y, (int) (cellWidth*.8), (int) (cellHeight*.8));
	}
	
	protected Location getFirstAvailableFreeLocation(){
		for (int i = 0; i < numberOfRows; i++) {
			for (int j = 0; j < numberOfColumns; j++) {
				Location loc = new Location(i, j);
				if(gridPieces.get(loc) == null){
					return loc;
				}
			}
		}
		return null;
	}
	
	protected void removePieceUI(StrategoPieceUI pieceUI){
		gridPieces.remove(pieceUI.getSourceGridLocation());
		this.remove(pieceUI);
	}
	
	protected void addPieceUI(Location location, StrategoPieceUI pieceUI){
		if(location == null){
			throw new IllegalStateException("location cannot be null.");
		}
		pieceUI.setLocationOnBoard(location.getRow(), location.getColumn());
		gridPieces.put(pieceUI.getLocationOnBoard(), pieceUI);
		this.add(pieceUI);
		setGraphicLocation(pieceUI);
	}

	protected StrategoPieceUI addPiece(int row, int column, StrategoPiece piece){
		Location initialLocation = new Location(row, column);
		StrategoPieceUI pieceUI = new StrategoPieceUI(this, initialLocation, piece);
		addPieceUI(initialLocation, pieceUI);
		return pieceUI;
	}

}
