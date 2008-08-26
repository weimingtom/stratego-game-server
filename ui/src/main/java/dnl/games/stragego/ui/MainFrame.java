package dnl.games.stragego.ui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class MainFrame extends JFrame {
	AllLayers layers = new AllLayers();

	public MainFrame() {
		getContentPane().add(layers, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		MainFrame frame = new MainFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.setSize(frame.getPreferredSize());
		frame.pack();
		frame.setVisible(true);
	}
}
