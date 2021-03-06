package org.magic.gui.models;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.DefaultTableModel;

import org.magic.api.beans.MagicCard;
import org.magic.api.beans.MagicCardNames;
import org.magic.services.MTGControler;

public class MagicCardTableModel extends DefaultTableModel {

	private List<MagicCard> cards;
	private String[] columns = new String[] { MTGControler.getInstance().getLangService().getCapitalize("NAME"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_LANGUAGE"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_MANA"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_TYPES"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_POWER"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_RARITY"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_EDITIONS"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_NUMBER"),
			MTGControler.getInstance().getLangService().getCapitalize("CARD_COLOR"),
			"RL"};

	public MagicCardTableModel() {
		cards = new ArrayList<>();

	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	@Override
	public int getRowCount() {
		if (cards == null)
			return 0;

		return cards.size();
	}

	@Override
	public String getColumnName(int column) {
		return columns[column];
	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return false;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		
		if(columnIndex==0)
			return MagicCard.class;
		else if(columnIndex==6)
			return List.class;
		else if (columnIndex==9)
			return Boolean.class;
		
		return String.class;
	}
	
	@Override
	public Object getValueAt(int row, int column) {
		try {
			MagicCard mc = cards.get(row);

			switch (column) {
			case 0:
				return mc;
			case 1:
				return getName(mc.getForeignNames());
			case 2:
				return mc.getCost();
			case 3:
				return mc.getFullType();
			case 4:
				return powerorloyalty(mc);
			case 5:
				return (mc.getCurrentSet() != null) ? mc.getCurrentSet().getRarity() : "";
			case 6:
				return mc.getEditions();
			case 7:
				return (mc.getCurrentSet() != null) ? mc.getCurrentSet().getNumber() : "";
			case 8:
				return mc.getColors();
			case 9:
				return mc.isReserved();
			default:
				return mc;
			}
		} catch (Exception e) {
			return null;
		}

	}

	private String powerorloyalty(MagicCard mc) {
		
		if(contains(mc.getTypes(), "creature"))
			return mc.getPower() + "/" + mc.getToughness();
		else if(contains(mc.getTypes(), "planeswalker"))
			return String.valueOf(mc.getLoyalty());
		
		return "";
	}

	private String getName(List<MagicCardNames> foreignNames) {
		for (MagicCardNames name : foreignNames) {
			if (name.getLanguage().equals(MTGControler.getInstance().get("langage")))
				return name.getName();
		}
		return "";
	}

	private boolean contains(List<String> types, String string) {
		for (String s : types)
			if (s.equalsIgnoreCase(string))
				return true;

		return false;

	}

	public void init(List<MagicCard> cards2) {
		this.cards = cards2;
		fireTableDataChanged();
	}

	public List<MagicCard> getListCards() {
		return cards;
	}

	public void addCard(MagicCard arg) {
		cards.add(arg);
		fireTableDataChanged();

	}

	public void clear() {
		cards.clear();
		fireTableDataChanged();

	}
}
