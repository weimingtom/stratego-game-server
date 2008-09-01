package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JFrame;

import dnl.games.stratego.Board;
import dnl.games.stratego.BoardMapReader;

public class MainFrame extends JFrame {
	BoardUI boardUi = new BoardUI();

	public MainFrame() {
		getContentPane().add(boardUi, BorderLayout.CENTER);
//		try {
//			Board board = BoardMapReader.readSystemResource("test.blue.initialpos1.stratego.map");
//			boardUi.showBoard(board);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setVisible(true);
		frame.setResizable(false);
	}
}
