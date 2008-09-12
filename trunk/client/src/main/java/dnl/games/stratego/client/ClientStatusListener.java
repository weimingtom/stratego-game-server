package dnl.games.stratego.client;

public interface ClientStatusListener {

	public void statusChanged(ClientStatus clientStatus, String statusMessage);
}
