package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import dnl.games.stragego.ui.actions.GetPlayersListAction;
import dnl.games.stragego.ui.actions.LoadBoardAction;
import dnl.games.stragego.ui.actions.LoginAction;
import dnl.games.stragego.ui.actions.SaveBoardAction;
import dnl.games.stratego.PlayerType;
import dnl.games.stratego.client.StrategoClient;

/**
 * The frame that holds the UI and uses the client.
 * 
 * @author Daniel Ore
 * 
 */
public class MainFrame extends JFrame {

	private static final String TITLE_PREFIX = "Stragego Client";
	private JMenuBar menuBar = new JMenuBar();
	private BoardUI boardUi = new BoardUI();
	private JLabel statusBar = new JLabel(" ");

	JMenu playerMenu = new JMenu("Player");
	JMenu boardMenu = new JMenu("Board");
	private StrategoClient strategoClient = new StrategoClient();

	
	public MainFrame() {
		super(TITLE_PREFIX);
		initIcon();
		this.setJMenuBar(menuBar);
		initMenuBar();
		boardUi.setEnabled(false);
		getContentPane().add(boardUi, BorderLayout.CENTER);

		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		getContentPane().add(statusBar, BorderLayout.SOUTH);

		strategoClient.setClientListener(new ClientListener(this, strategoClient, boardUi));
	}

	@Override
	public void setTitle(String title) {
		String sep = "";
		if(title != null && !title.isEmpty()){
			sep = ": ";
		}
		super.setTitle(TITLE_PREFIX+sep+title);
	}

	public void setStatusMessage(String s){
		statusBar.setText(s);
	}
	
	private void initIcon() {
		URL url = getClass().getResource("admiral.jpg");
		setIconImage(Toolkit.getDefaultToolkit().createImage(url));
	}

	private void initMenuBar() {
		Action loginAction = new LoginAction(this, strategoClient);
		Action currentPlayersAction = new GetPlayersListAction(this, strategoClient); 
		Action loadBoardAction = new LoadBoardAction(this, boardUi);
		Action saveBoardAction = new SaveBoardAction(this, boardUi);
		Action editBoardAction = new AbstractAction("Edit") {
			@Override
			public void actionPerformed(ActionEvent e) {
				boardUi.setUiStatus(UiStatus.EDITING);
				boardUi.setEnabled(true);
			}
		};

		boardMenu.add(editBoardAction);
		boardMenu.add(loadBoardAction);
		boardMenu.add(saveBoardAction);

		playerMenu.add(loginAction);
		playerMenu.add(currentPlayersAction);
		menuBar.add(playerMenu);
		menuBar.add(boardMenu);
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
		frame.boardUi.setPlayerType(PlayerType.RED);
	}

}
