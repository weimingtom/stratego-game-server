package dnl.games.stragego.ui.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;

import dnl.games.stragego.ui.BoardUI;
import dnl.games.stratego.Board;
import dnl.games.stratego.BoardMapReader;
import dnl.ui.FileChooserUtils;

public class LoadBoardAction extends StrategoUiAction {

	public LoadBoardAction(JFrame mainFrame, BoardUI boardUI) {
		super("Load from File", mainFrame, boardUI);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		File file = FileChooserUtils.selectFile(mainFrame);
		if(file == null){
			return;
		}
		try {
			Board board = BoardMapReader.readFile(file);
			boardUI.setBoard(board);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
