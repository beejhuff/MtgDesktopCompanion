package org.magic.gui.game.actions.library;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import org.magic.gui.game.GamePanelGUI;
import org.magic.gui.game.SearchLibraryFrame;

public class SearchActions extends AbstractAction {


	public SearchActions() {
		putValue(NAME,"Search in library");
		putValue(SHORT_DESCRIPTION,"");
		 putValue(MNEMONIC_KEY, KeyEvent.VK_S);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		GamePanelGUI.getInstance().getPlayer().logAction("search in library");
		SearchLibraryFrame f = new SearchLibraryFrame(GamePanelGUI.getInstance().getPlayer());
		f.setVisible(true);
		
	}
	
}