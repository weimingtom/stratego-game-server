package dnl.games.stratego.comm;

public enum Command {
	GET_PLAYERS, CHALLENGE, CHALLENGED_BY, CHALLENGE_ACCEPTED, STARTING_GAME, ORDERING_DONE, ERR;
	
	public boolean matchesCommand(String command){
		return command.startsWith(this.name()+":");
	}
}
