package dnl.games.stratego;


public enum PlayerType {
	RED, BLUE;
	
	public boolean isBlue(){
		return BLUE.equals(this);
	}

	public boolean isRed(){
		return RED.equals(this);
	}
}
