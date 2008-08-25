package dnl.games.stratego.server;


public enum PlayerType {
	RED, BLUE;
	
	public boolean isBlue(){
		return BLUE.equals(this);
	}

	public boolean isRed(){
		return RED.equals(this);
	}
}
