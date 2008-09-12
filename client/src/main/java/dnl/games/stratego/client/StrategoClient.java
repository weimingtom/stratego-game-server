package dnl.games.stratego.client;

import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.logging.Logger;

import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;

public class StrategoClient implements SimpleClientListener {

	private static final Logger logger = Logger.getLogger(StrategoClient.class.getSimpleName());

	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;

	/** The name of the host property. */
	public static final String HOST_PROPERTY = "stratego.host";

	/** The default hostname. */
	public static final String DEFAULT_HOST = "localhost";

	/** The name of the port property. */
	public static final String PORT_PROPERTY = "stratego.port";

	/** The default port. */
	public static final String DEFAULT_PORT = "1139";

	protected final SimpleClient simpleClient = new SimpleClient(this);

	private String playerName;
	
	private ClientStatusListener clientStatusListener;
	
	public void setClientStatusListener(ClientStatusListener clientStatusListener) {
		this.clientStatusListener = clientStatusListener;
	}

	public void login(String host, String port, String playerName) {
		this.playerName = playerName;
		//String host = System.getProperty(HOST_PROPERTY, DEFAULT_HOST);
		//String port = System.getProperty(PORT_PROPERTY, DEFAULT_PORT);

		try {
			Properties connectProps = new Properties();
			connectProps.put("host", host);
			connectProps.put("port", port);
			logger.info("Attempting to login player: " + playerName);
			simpleClient.login(connectProps);
		} catch (Exception e) {
			disconnected(false, e.getMessage());
		}

	}

	/**
	 * Displays the given string in this client's status bar.
	 * 
	 * @param status
	 *            the status message to set
	 */
	protected void setStatus(ClientStatus status, String statusMessage) {
		if(clientStatusListener == null)
			return;
		clientStatusListener.statusChanged(status, statusMessage);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Disables input and updates the status message on disconnect.
	 */
	public void disconnected(boolean graceful, String reason) {
		 setStatus(ClientStatus.DISCONNECTED, "Disconnected: " + reason);
	}

	@Override
	/*
	 * {@inheritDoc} <p> Returns dummy credentials where user is
	 * "guest-&lt;random&gt;" and the password is "guest." Real-world clients
	 * are likely to pop up a login dialog to get these fields from the player.
	 */
	public PasswordAuthentication getPasswordAuthentication() {
		setStatus(ClientStatus.LOGGING_IN, "Logging in as " + playerName);
		String password = "guest";
		return new PasswordAuthentication(playerName, password.toCharArray());
	}

	@Override
	public void loggedIn() {
		String msg = "Login succeeded for player: "+playerName;
		logger.info(msg);
		setStatus(ClientStatus.LOGGED_IN, msg);
	}

	@Override
	public void loginFailed(String arg0) {
		String msg = "Login failed for player: "+playerName;
		logger.info(msg);
		setStatus(ClientStatus.LOGIN_FAILED, msg);
	}

	@Override
	public ClientChannelListener joinedChannel(ClientChannel arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void receivedMessage(ByteBuffer arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnected() {
		// TODO Auto-generated method stub

	}

	@Override
	public void reconnecting() {
		// TODO Auto-generated method stub

	}
}