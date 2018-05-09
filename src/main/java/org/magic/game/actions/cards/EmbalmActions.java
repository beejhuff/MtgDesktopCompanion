package org.magic.game.actions.cards;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;
import org.magic.api.beans.MagicCard;
import org.magic.game.gui.components.DisplayableCard;
import org.magic.game.gui.components.GamePanelGUI;
import org.magic.game.model.counters.ItemCounter;
import org.magic.services.MTGControler;
import org.magic.services.MTGLogger;

public class EmbalmActions extends AbstractAction {

	private transient Logger logger = MTGLogger.getLogger(this.getClass());
	private DisplayableCard card;

	public EmbalmActions(DisplayableCard card) {
		super("Embalm");
		putValue(SHORT_DESCRIPTION, "Create a embalmed copy");
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		this.card = card;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			MagicCard tok = MTGControler.getInstance().getEnabledTokensProvider().generateTokenFor(card.getMagicCard());
			DisplayableCard dc = new DisplayableCard(tok, MTGControler.getInstance().getCardsDimension(), true);
			dc.addCounter(new ItemCounter("Embalm", Color.YELLOW));

			dc.setMagicCard(tok);
			GamePanelGUI.getInstance().getPlayer().exileCardFromGraveyard(card.getMagicCard());
			GamePanelGUI.getInstance().getPanelGrave().remove(card);
			GamePanelGUI.getInstance().getPanelBattleField().addComponent(dc);
			GamePanelGUI.getInstance().getPanelBattleField().revalidate();
			GamePanelGUI.getInstance().getPanelBattleField().repaint();
			GamePanelGUI.getInstance().getPanelGrave().postTreatment(card);
			GamePanelGUI.getInstance().getPlayer().playToken(tok);
			GamePanelGUI.getInstance().getPlayer().logAction("Embalm " + card);
		} catch (Exception ex) {
			logger.error(ex);
		}

	}
}
