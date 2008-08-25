package dnl.games.stratego.server;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;

public class StrategoPlayer extends StrategoObject implements ClientSessionListener {

	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;

	/** The {@link Logger} for this class. */
	private static final Logger logger = Logger.getLogger(StrategoPlayer.class.getName());

	/** The message encoding. */
	public static final String MESSAGE_CHARSET = "UTF-8";

	/** The prefix for player bindings in the {@code DataManager}. */
	protected static final String PLAYER_BIND_PREFIX = "Player.";

	/** The {@code ClientSession} for this player, or null if logged out. */
	private ManagedReference<ClientSession> currentSessionRef = null;

	/** The {@link SwordWorldRoom} this player is in, or null if none. */
	private ManagedReference<StrategoGame> currentRoomRef = null;

	public StrategoPlayer(String name) {
		super(name, "A player");
	}

	/**
	 * Find or create the player object for the given session, and mark the
	 * player as logged in on that session.
	 * 
	 * @param session
	 *            which session to find or create a player for
	 * @return a player for the given session
	 */
	public static StrategoPlayer loggedIn(ClientSession session) {
		String playerBinding = PLAYER_BIND_PREFIX + session.getName();

		// try to find player object, if non existent then create
		DataManager dataMgr = AppContext.getDataManager();
		StrategoPlayer player;

		try {
			player = (StrategoPlayer) dataMgr.getBinding(playerBinding);
		} catch (NameNotBoundException ex) {
			// this is a new player
			player = new StrategoPlayer(playerBinding);
			logger.log(Level.INFO, "New player created: {0}", player);
			dataMgr.setBinding(playerBinding, player);
		}
		player.setSession(session);
		return player;
	}

	/**
	 * Returns the session for this listener.
	 * 
	 * @return the session for this listener
	 */
	protected ClientSession getSession() {
		if (currentSessionRef == null)
			return null;

		return currentSessionRef.get();
	}

	/**
	 * Mark this player as logged in on the given session.
	 * 
	 * @param session
	 *            the session this player is logged in on
	 */
	protected void setSession(ClientSession session) {
		DataManager dataMgr = AppContext.getDataManager();
		dataMgr.markForUpdate(this);

		currentSessionRef = dataMgr.createReference(session);

		logger.log(Level.INFO, "Set session for {0} to {1}", new Object[] { this, session });
	}

	/**
	 * Sets the room this player is currently in. If the room given is null,
	 * marks the player as not in any room.
	 * <p>
	 * 
	 * @param room
	 *            the room this player should be in, or {@code null}
	 */
	protected void setRoom(StrategoGame room) {
		DataManager dataManager = AppContext.getDataManager();
		dataManager.markForUpdate(this);

		if (room == null) {
			currentRoomRef = null;
			return;
		}

		currentRoomRef = dataManager.createReference(room);
	}

	/**
	 * Handles a player entering a room.
	 * 
	 * @param room
	 *            the room for this player to enter
	 */
	public void enter(StrategoGame room) {
		logger.log(Level.INFO, "{0} enters {1}", new Object[] { this, room });
		room.addPlayer(this);
		setRoom(room);
	}

	@Override
	public void disconnected(boolean arg0) {
		setSession(null);
		logger.log(Level.INFO, "Disconnected: {0}", this);
		// getRoom().removePlayer(this);
		setRoom(null);
	}

	@Override
	public void receivedMessage(ByteBuffer message) {
        String command = decodeString(message);

        logger.log(Level.INFO,
            "{0} received command: {1}",
            new Object[] { this, command }
        );

        if (command.equalsIgnoreCase("look")) {
//            String reply = getRoom().look(this);
//            getSession().send(encodeString(reply));
        } else {
            logger.log(Level.WARNING,
                "{0} unknown command: {1}",
                new Object[] { this, command }
            );
            // We could disconnect the rogue player at this point.
            //currentSession.disconnect();
        }
    }

	/**
	 * Encodes a {@code String} into a {@link ByteBuffer}.
	 * 
	 * @param s
	 *            the string to encode
	 * @return the {@code ByteBuffer} which encodes the given string
	 */
	protected static ByteBuffer encodeString(String s) {
		try {
			return ByteBuffer.wrap(s.getBytes(MESSAGE_CHARSET));
		} catch (UnsupportedEncodingException e) {
			throw new Error("Required character set " + MESSAGE_CHARSET + " not found", e);
		}
	}

	/**
	 * Decodes a message into a {@code String}.
	 * 
	 * @param message
	 *            the message to decode
	 * @return the decoded string
	 */
	protected static String decodeString(ByteBuffer message) {
		try {
			byte[] bytes = new byte[message.remaining()];
			message.get(bytes);
			return new String(bytes, MESSAGE_CHARSET);
		} catch (UnsupportedEncodingException e) {
			throw new Error("Required character set " + MESSAGE_CHARSET + " not found", e);
		}
	}
}
