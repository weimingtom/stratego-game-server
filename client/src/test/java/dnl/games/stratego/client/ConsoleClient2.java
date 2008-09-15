package dnl.games.stratego.client;

import dnl.games.stratego.PlayerType;

public class ConsoleClient2 {
	private StrategoClient strategoClient = new StrategoClient();
	
	
	
	public static void main(String[] args) throws Exception {
		System.err.println("....");
		final ConsoleClient2 tc1 = new ConsoleClient2();
		tc1.strategoClient.setClientListener(new StrategoClientListener(){

			@Override
			public void challengedBy(String playerName) {
				System.out.println("challenged by "+playerName);
				tc1.strategoClient.acceptChallenge(playerName);
			}

			@Override
			public void startGame(String gameName, PlayerType playerType) {
				System.out.println("starting game "+gameName+". you are "+playerType);
				
			}

			@Override
			public void statusChanged(ClientStatus clientStatus, String statusMessage) {
				
			}
			
		});
		tc1.strategoClient.login("localhost", "1139", "TC2");
		Thread.sleep(200000);
	}
}
