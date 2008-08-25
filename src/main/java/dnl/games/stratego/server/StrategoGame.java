package dnl.games.stratego.server;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;

public class StrategoGame extends StrategoObject {

	/** The version of the serialized form of this class. */
	private static final long serialVersionUID = 1L;

	/** The {@link Logger} for this class. */
	private static final Logger logger = Logger.getLogger(StrategoGame.class.getName());

	/** The set of items in this room. */
	private final Set<ManagedReference<StrategoObject>> items = new HashSet<ManagedReference<StrategoObject>>();

	/** The set of players in this room. */
	private final Set<ManagedReference<StrategoPlayer>> players = new HashSet<ManagedReference<StrategoPlayer>>();

	public StrategoGame(String name) {
		super(name, "");
	}

    /**
     * Adds an item to this room.
     * 
     * @param item the item to add to this room.
     * @return {@code true} if the item was added to the room
     */
    public boolean addItem(StrategoObject item) {
        logger.log(Level.INFO, "{0} placed in {1}",
            new Object[] { item, this });

        // NOTE: we can't directly save the item in the list, or
        // we'll end up with a local copy of the item. Instead, we
        // must save a ManagedReference to the item.

        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        return items.add(dataManager.createReference(item));
    }
	
    /**
     * Adds a player to this room.
     *
     * @param player the player to add
     * @return {@code true} if the player was added to the room
     */
    public boolean addPlayer(StrategoPlayer player) {
        logger.log(Level.INFO, "{0} enters {1}",
            new Object[] { player, this });

        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        return players.add(dataManager.createReference(player));
    }

    /**
     * Removes a player from this room.
     *
     * @param player the player to remove
     * @return {@code true} if the player was in the room
     */
    public boolean removePlayer(StrategoPlayer player) {
        logger.log(Level.INFO, "{0} leaves {1}",
            new Object[] { player, this });

        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        return players.remove(dataManager.createReference(player));
    }
}