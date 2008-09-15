package dnl.games.stratego.server;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;

import dnl.games.stratego.PlayerType;

public class StrategoGame extends StrategoObject {

	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;

	/** The {@link Logger} for this class. */
	private static final Logger logger = Logger.getLogger(StrategoGame.class.getName());

	private ManagedReference<StrategoPlayer> bluePlayer;
	private ManagedReference<StrategoPlayer> redPlayer;

	public StrategoGame(StrategoPlayer blue, StrategoPlayer red) {
		super(blue.getSimpleName()+" against "+red.getSimpleName(), "");
		setPlayer(blue, PlayerType.BLUE);
		setPlayer(red, PlayerType.RED);
	}

	public StrategoPlayer getBluePlayer(){
		return bluePlayer.get();
	}

	public StrategoPlayer getRedPlayer(){
		return redPlayer.get();
	}
	
    /**
     * Adds a player to this room.
     *
     * @param player the player to add
     * @return {@code true} if the player was added to the room
     */
    public void setPlayer(StrategoPlayer player, PlayerType playerType) {
        logger.log(Level.INFO, "{0} enters {1}",
            new Object[] { player, this });

        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        if(playerType.isBlue()){
        	bluePlayer = dataManager.createReference(player);
        }
        else {
        	redPlayer = dataManager.createReference(player);
        }
    }


}