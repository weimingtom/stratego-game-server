package dnl.games.stragego.ui.actions;

import javax.swing.AbstractAction;
import javax.swing.JFrame;

import dnl.games.stragego.ui.BoardUI;
import dnl.games.stratego.client.StrategoClient;

public abstract class StrategoUiAction extends AbstractAction {

	protected JFrame mainFrame;
	protected StrategoClient strategoClient;
	protected BoardUI boardUI;
	
	public StrategoUiAction(String name, JFrame mainFrame, StrategoClient strategoClient) {
		super(name);
		this.mainFrame = mainFrame;
		this.strategoClient = strategoClient;
	}

	public StrategoUiAction(String name, JFrame mainFrame, BoardUI boardUI) {
		super(name);
		this.mainFrame = mainFrame;
		this.boardUI = boardUI;
	}

}
