package dnl.games.stragego.ui;

import dnl.games.stratego.Location;

public interface MovesListener {
	
	void playerMoved(Location from, Location to);
}
