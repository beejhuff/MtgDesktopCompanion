package org.magic.game.actions.cards;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import org.magic.api.beans.MagicCard;
import org.magic.game.gui.components.DisplayableCard;
import org.magic.game.gui.components.GamePanelGUI;
import org.magic.game.model.AbilitySpell;
import org.magic.services.MTGControler;

public class AftermathActions extends AbilitySpell {

	private String cost;
	private MagicCard mc;

	public AftermathActions(DisplayableCard card) {
		super("Aftermath", "Aftermath " + card.getMagicCard().getRotatedCardName(), card);
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);

		try {
			mc = MTGControler
					.getInstance().getEnabledCardsProviders().searchCardByName(
							card.getMagicCard().getRotatedCardName(), card.getMagicCard().getCurrentSet(), true)
					.get(0);
			cost = mc.getCost();
		} catch (Exception e) {
			logger.error(e);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			super.actionPerformed(e);
			DisplayableCard card2 = new DisplayableCard(mc, MTGControler.getInstance().getCardsDimension(), true);

			GamePanelGUI.getInstance().getPlayer().logAction("Aftermath " + card2 + " for " + cost);

			GamePanelGUI.getInstance().getPlayer().getGraveyard().remove(card.getMagicCard());
			GamePanelGUI.getInstance().getPlayer().getGraveyard().add(mc);
			GamePanelGUI.getInstance().getPlayer().playCardFromGraveyard(mc);

			GamePanelGUI.getInstance().getPanelGrave().remove(card);
			GamePanelGUI.getInstance().getPanelGrave().postTreatment(card);

			GamePanelGUI.getInstance().getPanelBattleField().addComponent(card2);
			GamePanelGUI.getInstance().getPanelBattleField().updatePanel();

		} catch (Exception e1) {
			logger.error(e1);
		}

	}

	@Override
	public String getCost() {
		return cost;
	}

	@Override
	public boolean isStackable() {
		return true;
	}

}
