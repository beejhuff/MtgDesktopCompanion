package org.magic.game.actions.cards;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;

import org.apache.log4j.Logger;
import org.magic.api.beans.MTGNotification;
import org.magic.api.beans.MTGNotification.MESSAGE_TYPE;
import org.magic.api.beans.MagicCard;
import org.magic.game.gui.components.DisplayableCard;
import org.magic.game.gui.components.GamePanelGUI;
import org.magic.services.MTGControler;
import org.magic.services.MTGLogger;

public class MeldActions extends AbstractAction {

	private DisplayableCard card;
	private String meldWith = "";
	private transient Logger logger = MTGLogger.getLogger(this.getClass());
	private static final String PARSEKEY = "(Melds with ";

	public MeldActions(DisplayableCard card) {
		super("Meld into " + card.getMagicCard().getRotatedCardName());
		putValue(SHORT_DESCRIPTION, "Meld the cards with bigger one !");
		putValue(MNEMONIC_KEY, KeyEvent.VK_M);
		this.card = card;
		parse(card.getMagicCard().getText());
	}

	public void parse(String test) {
		if (test.contains(PARSEKEY)) {
			meldWith = test.substring(test.indexOf(PARSEKEY) + PARSEKEY.length(), test.indexOf(".)")).trim();
		} else if (test.contains("and a creature named")) {

			meldWith = test.substring(test.indexOf("a creature named ") + "a creature named ".length(),
					test.indexOf(", exile them")).trim();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		DisplayableCard card2;
		try {
			card2 = GamePanelGUI.getInstance().getPanelBattleField().lookupCardBy("name", meldWith).get(0);
		} catch (Exception ex) {
			MTGControler.getInstance().notify(new MTGNotification(MTGControler.getInstance().getLangService().getError(), "Could not meld the card, " + meldWith + " is not on the battlefield", MESSAGE_TYPE.ERROR));
			return;
		}

		GamePanelGUI.getInstance().getPlayer().logAction(
				"Meld " + card.getMagicCard() + " and " + meldWith + " to " + card.getMagicCard().getRotatedCardName());

		card.removeAllCounters();
		GamePanelGUI.getInstance().getPlayer().exileCardFromBattleField(card.getMagicCard());
		GamePanelGUI.getInstance().getPanelBattleField().remove(card);

		card2.removeAllCounters();
		GamePanelGUI.getInstance().getPlayer().exileCardFromBattleField(card2.getMagicCard());
		GamePanelGUI.getInstance().getPanelBattleField().remove(card2);

		MagicCard mc;
		try {
			mc = MTGControler
					.getInstance().getEnabledCardsProviders().searchCardByName(
							card.getMagicCard().getRotatedCardName(), card.getMagicCard().getCurrentSet(), true)
					.get(0);

			Dimension d = new Dimension((int) (MTGControler.getInstance().getCardsDimension().getWidth() * 1.5),
					(int) (MTGControler.getInstance().getCardsDimension().getHeight() * 1.5));
			DisplayableCard c = new DisplayableCard(mc, d, true);
			c.initActions();
			GamePanelGUI.getInstance().getPanelBattleField().addComponent(c);
		} catch (Exception e1) {
			logger.error(e1);
		}

		GamePanelGUI.getInstance().getPanelBattleField().revalidate();
		GamePanelGUI.getInstance().getPanelBattleField().repaint();

	}

}
