package dnl.games.stragego.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class AllLayers extends JPanel{

	JLayeredPane layeredPane = new JLayeredPane();
	ImageLoader imageLoader = new ImageLoader();
	BoardLayer2 boardLayer = new BoardLayer2(imageLoader);
	PiecesLayer piecesLayer = new PiecesLayer(imageLoader);
	
	public AllLayers(){
		super(new BorderLayout());
		this.add(layeredPane, BorderLayout.CENTER);
		piecesLayer.setSize(boardLayer.getPreferredSize());
		boardLayer.setSize(boardLayer.getPreferredSize());
		layeredPane.add(boardLayer, 2);
		layeredPane.add(piecesLayer, 1);
		layeredPane.moveToFront(piecesLayer);
		layeredPane.setPreferredSize(boardLayer.getPreferredSize());
		layeredPane.setBackground(Color.green);
	}
}
