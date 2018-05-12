package org.magic.gui.renderer;

import java.awt.Component;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import org.magic.api.beans.MagicEdition;
import org.magic.gui.renderer.MagicEditionListRenderer.SIZE;

public class MagicEditionRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,int row, int column) {

		List<MagicEdition> e = (List) value;
		JComboBox<MagicEdition> cbo = new JComboBox<>(e.toArray(new MagicEdition[e.size()]));
		cbo.setRenderer(new MagicEditionListRenderer(SIZE.SMALL));
		return cbo;
	}
}
