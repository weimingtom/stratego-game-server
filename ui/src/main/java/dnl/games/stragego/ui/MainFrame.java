package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

import dnl.games.stratego.Board;
import dnl.games.stratego.BoardMapReader;
import dnl.games.stratego.client.ClientStatus;
import dnl.games.stratego.client.ClientStatusListener;
import dnl.games.stratego.client.StrategoClient;
import dnl.ui.FileChooserUtils;
import dnl.util.ui.WindowUtils;

public class MainFrame extends JFrame implements ClientStatusListener{
	
	
	private JMenuBar menuBar = new JMenuBar();
	private BoardUI boardUi = new BoardUI();
	private JLabel statusBar = new JLabel(" ");

	JMenu playerMenu = new JMenu("Player");
	JMenu boardMenu = new JMenu("Board");
	private StrategoClient strategoClient = new StrategoClient();
	
	public MainFrame() {
		this.setJMenuBar(menuBar);
		initMenuBar();
		boardUi.setEnabled(false);
		System.out.println(boardUi.isEnabled());
		getContentPane().add(boardUi, BorderLayout.CENTER);

		statusBar.setBorder(BorderFactory.createLoweredBevelBorder());
		getContentPane().add(statusBar, BorderLayout.SOUTH);
//		try {
//			Board board = BoardMapReader.readSystemResource("test.blue.initialpos1.stratego.map");
//			boardUi.showBoard(board);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		strategoClient.setClientStatusListener(this);
	}

	private void initMenuBar() {
		Action loginAction = new AbstractAction("Login"){
			@Override
			public void actionPerformed(ActionEvent e) {
				LoginDialog loginDialog = new LoginDialog(MainFrame.this, true);
				WindowUtils.centerWindowRelative(loginDialog, MainFrame.this);
				loginDialog.setVisible(true);
				String playerName = loginDialog.getPlayerName();
				String host = loginDialog.getHost();
				String port = loginDialog.getPort();
				strategoClient.login(host, port, playerName);
			}
			
		};
		Action loadBoardAction = new AbstractAction("Load from File"){
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = FileChooserUtils.selectFile(MainFrame.this);
				try {
					Board board = BoardMapReader.readFile(file);
					boardUi.setBoard(board);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		};
		Action saveBoardAction = new AbstractAction("Save to File"){
			@Override
			public void actionPerformed(ActionEvent e) {
				File file = FileChooserUtils.selectFile(MainFrame.this);
			}
			
		};
		Action editBoardAction = new AbstractAction("Edit"){
			@Override
			public void actionPerformed(ActionEvent e) {
				boardUi.setEnabled(true);
			}
			
		};
		
		boardMenu.add(editBoardAction);
		boardMenu.add(loadBoardAction);
		boardMenu.add(saveBoardAction);
		
		playerMenu.add(loginAction);
		menuBar.add(playerMenu);
		menuBar.add(boardMenu);
	}

	@Override
	public void statusChanged(ClientStatus clientStatus, String statusMessage) {
		statusBar.setText(statusMessage);
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		//frame.setResizable(false);
	}

}
