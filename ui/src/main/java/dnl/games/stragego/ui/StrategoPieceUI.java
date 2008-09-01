package dnl.games.stragego.ui;

import javax.swing.JLabel;

import dnl.games.stratego.Location;
import dnl.games.stratego.StrategoPiece;

public class StrategoPieceUI extends JLabel {

	Location location;
	StrategoPiece strategoPiece;

	public StrategoPieceUI(StrategoPiece strategoPiece) {
		super(strategoPiece.getAbbreviatedName() + "");
		this.strategoPiece = strategoPiece;
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
}
