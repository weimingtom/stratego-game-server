package dnl.games.stragego.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import dnl.games.stragego.ui.PlayersDialog;
import dnl.games.stratego.client.StrategoClient;

public class GetPlayersListAction extends StrategoUiAction {

	public GetPlayersListAction(JFrame mainFrame, StrategoClient strategoClient) {
		super("Current Players", mainFrame, strategoClient);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String[] playersList = strategoClient.getPlayersList();
		PlayersDialog playersDialog = new PlayersDialog(mainFrame, true);
		playersDialog.setPlayerNames(playersList);
		playersDialog.setVisible(true);
		if (playersDialog.getRival() != null) {
			strategoClient.challenge(playersDialog.getRival());
		}
	}
}
