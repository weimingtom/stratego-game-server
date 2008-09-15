package dnl.games.stragego.ui.actions;

import java.awt.event.ActionEvent;

import javax.swing.JFrame;

import dnl.games.stragego.ui.LoginDialog;
import dnl.games.stratego.client.StrategoClient;
import dnl.util.ui.WindowUtils;

public class LoginAction extends StrategoUiAction {

	public LoginAction(JFrame mainFrame, StrategoClient strategoClient) {
		super("Login", mainFrame, strategoClient);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		LoginDialog loginDialog = new LoginDialog(mainFrame, true);
		WindowUtils.centerWindowRelative(loginDialog, mainFrame);
		loginDialog.setVisible(true);
		String playerName = loginDialog.getPlayerName();
		String host = loginDialog.getHost();
		String port = loginDialog.getPort();
		strategoClient.login(host, port, playerName);
	}
}
