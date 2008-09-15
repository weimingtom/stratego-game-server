/*
 * Copyright 2007-2008 Sun Microsystems, Inc.
 *
 * This file is part of Project Darkstar Server.
 *
 * Project Darkstar Server is free software: you can redistribute it
 * and/or modify it under the terms of the GNU General Public License
 * version 2 as published by the Free Software Foundation and
 * distributed hereunder to you.
 *
 * Project Darkstar Server is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package dnl.games.stratego.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.sun.sgs.app.AppContext;
import com.sun.sgs.app.DataManager;
import com.sun.sgs.app.ManagedReference;

import dnl.games.stratego.comm.Command;

/**
 * Represents a room in the {@link Stratego} example MUD.
 */
public class StrategoRoom extends StrategoObject
{
    /** The version of the serialized form of this class. */
    private static final long serialVersionUID = 1L;

    /** The {@link Logger} for this class. */
    private static final Logger logger =
        Logger.getLogger(StrategoRoom.class.getName());

    /** The set of players in this room. */
    private final Map<String, ManagedReference<StrategoPlayer>> players =
        new HashMap<String, ManagedReference<StrategoPlayer>>();

    private final Map<String, ManagedReference<StrategoGame>> games =
    	new HashMap<String, ManagedReference<StrategoGame>>();

    /**
     * Creates a new room with the given name and description, initially
     * empty of items and players.
     *
     * @param name the name of this room
     * @param description a description of this room
     */
    public StrategoRoom(String name, String description) {
        super(name, description);
    }

    public StrategoPlayer getPlayer(String name){
    	String s = name;
    	if(!s.startsWith("Player.")){
    		s = "Player."+s;
    	}
    	ManagedReference<StrategoPlayer> reference = players.get(s);
    	if(reference == null){
    		return null;
    	}
    	return reference.get();
    }
    
    public StrategoGame getGame(String name){
    	ManagedReference<StrategoGame> reference = games.get(name);
    	if(reference == null){
    		return null;
    	}
    	return reference.get();
    }

    /**
     * Adds a player to this room.
     *
     * @param player the player to add
     * @return {@code true} if the player was added to the room
     */
    public void addPlayer(StrategoPlayer player) {
        logger.log(Level.INFO, "{0} enters {1}",
            new Object[] { player, this });

        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        players.put(player.getName(), dataManager.createReference(player));
    }

    public void startGame(StrategoPlayer blue, StrategoPlayer red) {
    	StrategoGame game = new StrategoGame(blue, red);
    	
    	logger.log(Level.INFO, "Starting game {0}",
    			new Object[] { game.getName()});
    	
    	DataManager dataManager = AppContext.getDataManager();
    	dataManager.markForUpdate(this);
    	
    	games.put(game.getName(), dataManager.createReference(game));
    	blue.getSession().send(CDC.encodeString(Command.STARTING_GAME+":"+game.getName()));
    	red.getSession().send(CDC.encodeString(Command.STARTING_GAME+":"+game.getName()));
    }

    /**
     * Removes a player from this room.
     *
     * @param player the player to remove
     * @return {@code true} if the player was in the room
     */
    public void removePlayer(StrategoPlayer player) {
        logger.log(Level.INFO, "{0} leaves {1}",
            new Object[] { player, this });

        DataManager dataManager = AppContext.getDataManager();
        dataManager.markForUpdate(this);

        players.remove(player.getName());
    }

    /**
     * Returns a description of what the given player sees in this room.
     *
     * @param looker the player looking in this room
     * @return a description of what the given player sees in this room
     */
    public String describesPlayers(StrategoPlayer looker) {
        logger.log(Level.INFO, "{0} looks at {1}",
            new Object[] { looker, this });

        StringBuilder output = new StringBuilder();
        //output.append("You are in ").append(getDescription()).append(".\n");

        List<StrategoPlayer> otherPlayers =
            getPlayersExcluding(looker);

        if (! otherPlayers.isEmpty()) {
            //output.append("Also in here are ");
            appendPrettyList(output, otherPlayers);
            //output.append(".\n");
        }

        return output.toString();
    }

    /**
     * Appends the names of the {@code StrategoObject}s in the list
     * to the builder, separated by commas, with an "and" before the final
     * item.
     *
     * @param builder the {@code StringBuilder} to append to
     * @param list the list of items to format
     */
    private void appendPrettyList(StringBuilder builder,
        List<? extends StrategoPlayer> list)
    {
        if (list.isEmpty())
            return;

        int lastIndex = list.size() - 1;
        StrategoPlayer last = list.get(lastIndex);

        Iterator<? extends StrategoPlayer> it =
            list.subList(0, lastIndex).iterator();
        if (it.hasNext()) {
        	StrategoPlayer other = it.next();
            builder.append(other.getName());
            while (it.hasNext()) {
                other = it.next();
                builder.append(" ,");
                builder.append(other.getName());
            }
            builder.append(" and ");
        }
        builder.append(last.getName());
    }

    /**
     * Returns a list of players in this room excluding the given
     * player.
     *
     * @param player the player to exclude
     * @return the list of players
     */
    private List<StrategoPlayer>
            getPlayersExcluding(StrategoPlayer player)
    {
        if (players.isEmpty())
            return Collections.emptyList();

        ArrayList<StrategoPlayer> otherPlayers =
            new ArrayList<StrategoPlayer>(players.size());

        Collection<ManagedReference<StrategoPlayer>> values = players.values();
        for (ManagedReference<StrategoPlayer> playerRef : values) {
        	StrategoPlayer other = playerRef.get();
            if (! player.equals(other))
                otherPlayers.add(other);
        }

        return Collections.unmodifiableList(otherPlayers);
    }
}
