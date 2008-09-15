package dnl.games.stratego.client;

import java.io.IOException;
import java.net.PasswordAuthentication;
import java.nio.ByteBuffer;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang.StringUtils;

import com.sun.sgs.client.ClientChannel;
import com.sun.sgs.client.ClientChannelListener;
import com.sun.sgs.client.simple.SimpleClient;
import com.sun.sgs.client.simple.SimpleClientListener;

import dnl.games.stratego.PlayerType;
import dnl.games.stratego.comm.Command;

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

	private StrategoClientListener clientListener;

	// private
	public void setClientListener(StrategoClientListener clientListener) {
		this.clientListener = clientListener;
	}

	private String message;

	public void login(String host, String port, String playerName) {
		this.playerName = playerName;

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

	public void challenge(String player) {
		sendCommand(Command.CHALLENGE, player);
	}

	public void doneOrderingPieces(String game) {
		sendCommand(Command.ORDERING_DONE, game);
	}

	public void acceptChallenge(String player) {
		sendCommand(Command.CHALLENGE_ACCEPTED, player);
	}

	private void sendCommand(Command command, String content) {
		ByteBuffer bb = CDC.encodeString(command.name() + ":" + content);
		try {
			simpleClient.send(bb);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String[] getPlayersList() {
		message = null;
		sendCommand(Command.GET_PLAYERS, "");
		waitForMessage();
		if (message == null) {
			// too bad
			logger.log(Level.WARNING, "Did not receive data from server in a timely manner.");
		}
		return StringUtils.split(message, ";,");
	}

	private void waitForMessage() {
		for (int i = 0; message == null && i < 5; i++) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Displays the given string in this client's status bar.
	 * 
	 * @param status
	 *            the status message to set
	 */
	protected void setStatus(ClientStatus status, String statusMessage) {
		if (clientListener == null)
			return;
		clientListener.statusChanged(status, statusMessage);
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
		String msg = "Login succeeded for player: " + playerName;
		logger.info(msg);
		setStatus(ClientStatus.LOGGED_IN, msg);
	}

	@Override
	public void loginFailed(String arg0) {
		String msg = "Login failed for player: " + playerName;
		logger.info(msg);
		setStatus(ClientStatus.LOGIN_FAILED, msg);
	}

	@Override
	public ClientChannelListener joinedChannel(ClientChannel arg0) {
		System.err.println("joinedChannel!");
		return null;
	}

	@Override
	public void receivedMessage(ByteBuffer bb) {
		message = CDC.decodeString(bb);
		logger.info("receivedMessage(): " + message);
		if (Command.CHALLENGED_BY.matchesCommand(message)) {
			clientListener.challengedBy(extractCommandContent(message));
		}
		if (Command.STARTING_GAME.matchesCommand(message)) {
			String gameName = extractCommandContent(message);
			PlayerType playerType = PlayerType.BLUE;
			if(gameName.endsWith(playerName)){
				playerType = PlayerType.RED;
			}
			clientListener.startGame(gameName, playerType);
		}
	}

	private String extractCommandContent(String command) {
		int ind = command.indexOf(':');
		return command.substring(ind + 1);
	}

	@Override
	public void reconnected() {
		logger.info("reconnected()");

	}

	@Override
	public void reconnecting() {
		logger.info("reconnecting()");

	}
}
