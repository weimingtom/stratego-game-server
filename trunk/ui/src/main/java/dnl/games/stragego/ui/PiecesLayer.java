package dnl.games.stragego.ui;

import java.awt.Dimension;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * 
 */
class PiecesLayer extends JPanel {

	Image image;
	JLabel piece;
	
	PiecesLayer(ImageLoader imageLoader) {
		super(null);
		image = imageLoader.getShieldImage();
		piece = new JLabel(new ImageIcon(image));
		this.setOpaque(false);
		this.add(piece);
		piece.setBounds(0,0,100,100);
		
	}

	/**
	 * Get the preferred size of the board.
	 * 
	 * @return The preferred size of the board.
	 */
	public final Dimension getPreferredSize() {
		Dimension d = new Dimension(image.getWidth(this), image.getHeight(this));
		return d;
	}

	public final Dimension getMinimumSize() {
		return getPreferredSize();
	}

}
