package dnl.games.stratego.client;

import dnl.games.stratego.PlayerType;

public class ConsoleClient1 {
	private StrategoClient strategoClient = new StrategoClient();
	
	
	
	public static void main(String[] args) throws Exception {
		System.err.println("....");
		final ConsoleClient1 tc = new ConsoleClient1();
		tc.strategoClient.setClientListener(new StrategoClientListener(){

			@Override
			public void challengedBy(String playerName) {
				System.out.println("challenged by "+playerName);
				tc.strategoClient.acceptChallenge(playerName);
			}

			@Override
			public void startGame(String gameName, PlayerType playerType) {
				System.out.println("starting game "+gameName+". you are "+playerType);
				
			}

			@Override
			public void statusChanged(ClientStatus clientStatus, String statusMessage) {
				
			}
			
		});
		tc.strategoClient.login("localhost", "1139", "TC1");
		Thread.sleep(2000);
		tc.strategoClient.challenge("TC2");
		Thread.sleep(210000);
	}
}
