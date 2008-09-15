package dnl.games.stratego.server;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;

public class StrategoGameManager implements AppListener, Serializable {
	
	private static final Logger logger = Logger.getLogger(StrategoGameManager.class.getSimpleName());
	private static final long serialVersionUID = 1L;
	
    /** A reference to the one-and-only {@linkplain StrategoRoom room}. */
    private ManagedReference<StrategoRoom> roomRef = null;

	@Override
	public void initialize(Properties properties) {
		logger.info("init");
        StrategoRoom room =
            new StrategoRoom("Plain Room", "a nondescript room");
        setRoom(room);
	}

	@Override
	public ClientSessionListener loggedIn(ClientSession session) {
        logger.log(Level.INFO,
                ">> Stratego Client login: {0}", session.getName());

            // Delegate to a factory method on StrategoPlayer,
            // since player management really belongs in that class.
            StrategoPlayer player = StrategoPlayer.loggedIn(session);

            player.enter(getRoom());

            // return player object as listener to this client session
            return player;
	}

    /**
     * Gets the Stratego's One True Room.
     * <p>
     * @return the room for this {@code Stratego}
     */
    public StrategoRoom getRoom() {
        if (roomRef == null)
            return null;

        return roomRef.get();
    }

    /**
     * Sets the Stratego One True Room to the given room.
     * <p>
     * @param room the room to set
     */
    public void setRoom(StrategoRoom room) {
        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        if (room == null) {
            roomRef = null;
            return;
        }

        roomRef = dataManager.createReference(room);
    }
	
}