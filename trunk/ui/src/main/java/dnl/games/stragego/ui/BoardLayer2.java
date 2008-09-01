package dnl.games.stragego.ui;

import java.awt.Color;
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
	Dimension imageOriginalSize;
	
	BoardLayer2(ImageLoader imageLoader) {
		image = imageLoader.getBoardImage();
		imageOriginalSize = new Dimension(image.getWidth(this), image.getHeight(this));
	}

	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
		double w = getWidth();
		double h = getHeight();
		g2.setColor(Color.red.darker());
		
		for (int i = 0; i < 10; i++) {
			int y = (int)((h/10)*i);
			g2.drawLine(0, y, (int)w, y);
		}

		for (int i = 0; i < 10; i++) {
			int x = (int)((w/10)*i);
			g2.drawLine(x, 0, x, (int)h);
		}
	}

	/**
	 * Get the preferred size of the board.
	 * 
	 * @return The preferred size of the board.
	 */
	public final Dimension getPreferredSize() {
		return imageOriginalSize;
	}

	public final Dimension getMinimumSize() {
		return imageOriginalSize;
	}

}
