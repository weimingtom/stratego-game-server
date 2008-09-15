package dnl.games.stragego.ui;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JOptionPane;

import dnl.games.stratego.PlayerType;
import dnl.games.stratego.client.ClientStatus;
import dnl.games.stratego.client.StrategoClient;
import dnl.games.stratego.client.StrategoClientListener;

public class ClientListener implements StrategoClientListener {
	private MainFrame mainFrame;
	private StrategoClient strategoClient;
	private BoardUI boardUI;

	
	public ClientListener(MainFrame mainFrame, StrategoClient strategoClient,BoardUI boardUI) {
		this.boardUI = boardUI;
		this.mainFrame = mainFrame;
		this.strategoClient = strategoClient;
	}

	@Override
	public void challengedBy(String playerName) {
		int userInput = JOptionPane.showConfirmDialog(mainFrame, playerName
				+ " is challenging you to a game.\nDo you accept?");
		if (userInput == JOptionPane.YES_OPTION) {
			strategoClient.acceptChallenge(playerName);
		}
	}

	@Override
	public void statusChanged(ClientStatus clientStatus, String statusMessage) {
		mainFrame.setStatusMessage(statusMessage);
	}

	@Override
	public void startGame(String gameName, PlayerType playerType) {
		mainFrame.setTitle(gameName);
		boardUI.setPlayerType(playerType);
		boardUI.setUiStatus(UiStatus.EDITING);
		boardUI.setEnabled(true);
		Action done = new AbstractAction("Done") {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		};
		boardUI.displayUserOption("Edit your opening position and press 'Done'", done);
	}

}
