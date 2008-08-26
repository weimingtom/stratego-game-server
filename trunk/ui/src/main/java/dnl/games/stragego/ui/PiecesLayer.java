package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * 
 */
class PiecesLayer extends JPanel {

	Image image;

	PiecesLayer(ImageLoader imageLoader) {
		image = imageLoader.getShieldImage();
		this.setOpaque(false);
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, this);
	}

	/**
	 * Get the preferred size of the board.
	 * 
	 * @return The preferred size of the board.
	 */
	public final Dimension getPreferredSize() {
		Dimension d = new Dimension(image.getWidth(this), image.getHeight(this));
		System.out.println("--"+d);
		return d;
	}

	public final Dimension getMinimumSize() {
		return getPreferredSize();
	}

}
