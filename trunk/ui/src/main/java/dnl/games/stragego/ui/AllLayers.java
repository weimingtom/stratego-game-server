package dnl.games.stragego.ui;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class AllLayers extends JPanel{

	JLayeredPane layeredPane = new JLayeredPane();
	ImageLoader imageLoader = new ImageLoader();
	BoardLayer2 boardLayer = new BoardLayer2(imageLoader);
//	PiecesLayer piecesLayer = new PiecesLayer(imageLoader);
	
	public AllLayers(){
		super(new BorderLayout());
		this.add(layeredPane, BorderLayout.CENTER);
//		piecesLayer.setSize(boardLayer.getPreferredSize());
		//layeredPane.add(boardLayer, 1);
		layeredPane.add(new JButton("555555555"), 0);
		//layeredPane.add(piecesLayer, 2);
		//layeredPane.moveToFront(boardLayer);
		layeredPane.setPreferredSize(boardLayer.getPreferredSize());
	}
}
