package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 
 */
class BoardLayer2 extends JPanel {

	Image image;

	BoardLayer2(ImageLoader imageLoader) {
		image = imageLoader.getBoardImage();
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, this);
		System.err.println("-----");
	}

	/**
	 * Get the preferred size of the board.
	 * 
	 * @return The preferred size of the board.
	 */
	public final Dimension getPreferredSize() {
		Dimension d = new Dimension(image.getWidth(this), image.getHeight(this));
		System.err.println(d);
		return d;
	}

	public final Dimension getMinimumSize() {
		return getPreferredSize();
	}

}
