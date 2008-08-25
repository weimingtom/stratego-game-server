package dnl.games.stratego.server;

import java.io.Serializable;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppListener;
import com.sun.sgs.app.ClientSession;
import com.sun.sgs.app.ClientSessionListener;
import com.sun.sgs.app.ManagedReference;

public class StrategoGameManager implements AppListener, Serializable {
	
	private static final Logger logger = Logger.getLogger(StrategoGameManager.class.getSimpleName());
	private static final long serialVersionUID = 1L;
	
    /** A reference to the one-and-only {@linkplain SwordWorldRoom room}. */
    private ManagedReference<StrategoGame> roomRef = null;

	@Override
	public void initialize(Properties properties) {
		logger.info("init");
	}

	@Override
	public ClientSessionListener loggedIn(ClientSession session) {
        logger.log(Level.INFO,
                "SwordWorld Client login: {0}", session.getName());

            // Delegate to a factory method on SwordWorldPlayer,
            // since player management really belongs in that class.
            StrategoPlayer player = StrategoPlayer.loggedIn(session);

            // Put player in room
            //player.enter(getRoom());

            // return player object as listener to this client session
            return player;
	}

	
	
}