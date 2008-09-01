package dnl.games.stragego.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class PaddingPanel extends JPanel {
	JComponent component;
	int paddingSize;
	
	public PaddingPanel(JComponent component, int paddingSize){
		super(null);
		this.component = component;
		this.paddingSize = paddingSize;
		this.add(component);
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}

	private void resolveComponentLocation(){
		Dimension size = getSize();
		component.setBounds(paddingSize, paddingSize, size.width-2*paddingSize, size.height - 2*paddingSize);
	}
	
	public final Dimension getPreferredSize() {
		Dimension size = component.getPreferredSize();
		Dimension d = new Dimension(size.width+paddingSize*2, size.height+paddingSize*2);
		return d;
	}

	@Override
	public void setPreferredSize(Dimension preferredSize) {
		super.setPreferredSize(preferredSize);
		component.setPreferredSize(new Dimension(preferredSize.width-paddingSize, preferredSize.height-paddingSize));
		resolveComponentLocation();
	}

	@Override
	public void setSize(Dimension d) {
		super.setSize(d);
		resolveComponentLocation();
	}

	@Override
	public void setSize(int width, int height) {
		super.setSize(width, height);
		resolveComponentLocation();
	}

	@Override
	public void setBounds(int x, int y, int width, int height) {
		super.setBounds(x, y, width, height);
		resolveComponentLocation();
	}

	@Override
	public void setBounds(Rectangle r) {
		super.setBounds(r);
		resolveComponentLocation();
	}
	
}