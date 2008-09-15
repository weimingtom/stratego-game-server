package dnl.games.stratego.server;

import java.nio.ByteBuffer;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;
import com.sun.sgs.app.NameNotBoundException;

import dnl.games.stratego.comm.Command;

public class StrategoPlayer extends StrategoObject implements ClientSessionListener {

	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;

	/** The {@link Logger} for this class. */
	private static final Logger logger = Logger.getLogger(StrategoPlayer.class.getName());

	/** The prefix for player bindings in the {@code DataManager}. */
	protected static final String PLAYER_BIND_PREFIX = "Player.";

	/** The {@code ClientSession} for this player, or null if logged out. */
	private ManagedReference<ClientSession> currentSessionRef = null;

	/** The {@link StrategoRoom} this player is in, or null if none. */
	private ManagedReference<StrategoRoom> currentRoomRef = null;

	public StrategoPlayer(String name) {
		super(name, "A player");
	}

	public String getSimpleName(){
		String s = getName();
		int ind = s.indexOf('.');
		return s.substring(ind+1);
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
		if(session == null){
			currentSessionRef = null;
		}
		else {
			currentSessionRef = dataMgr.createReference(session);
		}

		logger.log(Level.INFO, "Set session for {0} to {1}", new Object[] { this, session });
	}

    /**
     * Returns the room this player is currently in, or {@code null} if
     * this player is not in a room.
     * <p>
     * @return the room this player is currently in, or {@code null}
     */
    protected StrategoRoom getRoom() {
        if (currentRoomRef == null)
            return null;

        return currentRoomRef.get();
    }
	
	/**
	 * Sets the room this player is currently in. If the room given is null,
	 * marks the player as not in any room.
	 * <p>
	 * 
	 * @param room
	 *            the room this player should be in, or {@code null}
	 */
	protected void setRoom(StrategoRoom room) {
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
	public void enter(StrategoRoom room) {
		logger.log(Level.INFO, "{0} enters {1}", new Object[] { this, room });
		room.addPlayer(this);
		setRoom(room);
	}

	@Override
	public void disconnected(boolean arg0) {
        setSession(null);
        logger.log(Level.INFO, "Disconnected: {0}", this);
        getRoom().removePlayer(this);
        setRoom(null);
	}

	@Override
	public void receivedMessage(ByteBuffer message) {
        String command = CDC.decodeString(message);

        logger.log(Level.INFO,
            "{0} received command: {1}",
            new Object[] { this, command }
        );

        if (Command.GET_PLAYERS.matchesCommand(command)) {
        	String reply = getRoom().describesPlayers(this);
            getSession().send(CDC.encodeString(reply));
        }
        else if(Command.CHALLENGE.matchesCommand(command)){
        	String challengedPlayerName = getCommandContents(command);
        	challengeAnotherPlayer(challengedPlayerName);
        }
        else if(Command.CHALLENGE_ACCEPTED.matchesCommand(command)){
        	String challengedPlayerName = getCommandContents(command);
        	StrategoPlayer red = getRoom().getPlayer(challengedPlayerName); 
        	getRoom().startGame(red, this);
        }
        else {
            logger.log(Level.WARNING,
                "{0} unknown command: {1}",
                new Object[] { this, command }
            );
            // We could disconnect the rogue player at this point.
            //currentSession.disconnect();
        }
    }

	private String getCommandContents(String command) {
		int ind = command.indexOf(':');
		return command.substring(ind+1);
	}

	private void challengeAnotherPlayer(String challengedPlayerName) {
		logger.info("Challenging "+challengedPlayerName);
		StrategoPlayer challengedPlayer = getRoom().getPlayer(challengedPlayerName);
		if(challengedPlayer != null){
			challengedPlayer.getSession().send(CDC.encodeString(Command.CHALLENGED_BY+":"+getName()));
		}
		else {
			getSession().send(CDC.encodeString(Command.ERR+": Player '"+challengedPlayerName+"' not connected anymore."));
		}
	}


}
