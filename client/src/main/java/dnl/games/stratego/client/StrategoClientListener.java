package dnl.games.stratego.client;

import dnl.games.stratego.PlayerType;

public interface StrategoClientListener {

	public void statusChanged(ClientStatus clientStatus, String statusMessage);
	
	public void challengedBy(String playerName);

	public void startGame(String gameName, PlayerType playerType);
}
