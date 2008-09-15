package dnl.games.stragego.ui;

import javax.swing.JLabel;

import dnl.games.stratego.Location;
import dnl.games.stratego.StrategoPiece;

public class StrategoPieceUI extends JLabel {

	private Location location;
	private Location sourceGridLocation;
	private StrategoPiece strategoPiece;
	private PiecesGrid sourceGrid;

	public StrategoPieceUI(PiecesGrid sourceGrid, Location sourceGridLocation, StrategoPiece strategoPiece) {
		super(strategoPiece.getType().getAbbreviatedName() + "");
		this.sourceGrid = sourceGrid;
		this.sourceGridLocation = sourceGridLocation;
		this.strategoPiece = strategoPiece;
	}

	public PiecesGrid getSourceGrid() {
		return sourceGrid;
	}

	public Location getSourceGridLocation() {
		return sourceGridLocation;
	}

	public StrategoPiece getStrategoPiece() {
		return strategoPiece;
	}

	public void setStrategoPiece(StrategoPiece strategoPiece) {
		this.strategoPiece = strategoPiece;
	}

	public Location getLocationOnBoard() {
		return location;
	}

	public void setLocationOnBoard(int row, int column) {
		this.location = new Location(row, column);
	}
	
	@Override
	public String toString() {
		return getClass().getSimpleName()+"(piece="+strategoPiece+", location="+location+")";
	}
}
