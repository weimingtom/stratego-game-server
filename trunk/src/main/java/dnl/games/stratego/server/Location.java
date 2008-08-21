package dnl.games.stratego.server;

public class Location {

	private int row;
	private int column;

	public Location(int row, int column) {
		this.column = column;
		this.row = row;
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName()+"("+row+", "+column+")";
	}

}
