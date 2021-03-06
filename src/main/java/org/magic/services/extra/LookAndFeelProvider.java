package org.magic.services.extra;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.plaf.FontUIResource;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.log4j.Logger;
import org.magic.services.MTGControler;
import org.magic.services.MTGLogger;
import org.pushingpixels.substance.api.SubstanceLookAndFeel;
import org.reflections.Reflections;

public class LookAndFeelProvider {

	private Logger logger = MTGLogger.getLogger(this.getClass());
	private List<LookAndFeelInfo> list;

	public LookAndFeelProvider() {
		list = new ArrayList<>();
	}

	public void setComponentLookAndFeel(Component ui, LookAndFeelInfo lookAndFeel) {
		setLookAndFeel(ui, lookAndFeel.getClassName());
	}

	public void setLookAndFeel(Container container, LookAndFeelInfo lookAndFeel) {
		setLookAndFeel(container, lookAndFeel.getClassName());
	}

	public void cleanExtra() {
		list.clear();
	}

	public void setLookAndFeel(Component ui, String lookAndFeel) {
		try {
			UIManager.setLookAndFeel(lookAndFeel);
			MTGControler.getInstance().setProperty("lookAndFeel", lookAndFeel);
			SwingUtilities.updateComponentTreeUI(ui);

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public LookAndFeelInfo[] getStandardLookAndFeel() {
		return UIManager.getInstalledLookAndFeels();

	}

	public LookAndFeelInfo[] getAllLookAndFeel() {

		return ArrayUtils.addAll(UIManager.getInstalledLookAndFeels(), getExtraLookAndFeel());
	}

	public LookAndFeelInfo[] getExtraLookAndFeel() {

		if (!list.isEmpty())
			return list.toArray(new LookAndFeelInfo[list.size()]);

		Reflections classReflections = new Reflections("org.pushingpixels.substance.api.skin");
		list = new ArrayList<>();
		for (Class<? extends SubstanceLookAndFeel> c : classReflections.getSubTypesOf(SubstanceLookAndFeel.class)) {
			try {
				SubstanceLookAndFeel look = c.getConstructor(null).newInstance();
				list.add(new LookAndFeelInfo(look.getID(), c.getName()));
			} catch (Exception e) {
				logger.error("Loading " + c, e);
			}
		}
		return list.toArray(new LookAndFeelInfo[list.size()]);
	}
	
	public void setFont(FontUIResource myFont) {
	    UIManager.put("CheckBoxMenuItem.acceleratorFont", myFont);
	    UIManager.put("Button.font", myFont);
	    UIManager.put("ToggleButton.font", myFont);
	    UIManager.put("RadioButton.font", myFont);
	    UIManager.put("CheckBox.font", myFont);
	    UIManager.put("ColorChooser.font", myFont);
	    UIManager.put("ComboBox.font", myFont);
	    UIManager.put("Label.font", myFont);
	    UIManager.put("List.font", myFont);
	    UIManager.put("MenuBar.font", myFont);
	    UIManager.put("Menu.acceleratorFont", myFont);
	    UIManager.put("RadioButtonMenuItem.acceleratorFont", myFont);
	    UIManager.put("MenuItem.acceleratorFont", myFont);
	    UIManager.put("MenuItem.font", myFont);
	    UIManager.put("RadioButtonMenuItem.font", myFont);
	    UIManager.put("CheckBoxMenuItem.font", myFont);
	    UIManager.put("OptionPane.buttonFont", myFont);
	    UIManager.put("OptionPane.messageFont", myFont);
	    UIManager.put("Menu.font", myFont);
	    UIManager.put("PopupMenu.font", myFont);
	    UIManager.put("OptionPane.font", myFont);
	    UIManager.put("Panel.font", myFont);
	    UIManager.put("ProgressBar.font", myFont);
	    UIManager.put("ScrollPane.font", myFont);
	    UIManager.put("Viewport.font", myFont);
	    UIManager.put("TabbedPane.font", myFont);
	    UIManager.put("Slider.font", myFont);
	    UIManager.put("Table.font", myFont);
	    UIManager.put("TableHeader.font", myFont);
	    UIManager.put("TextField.font", myFont);
	    UIManager.put("Spinner.font", myFont);
	    UIManager.put("PasswordField.font", myFont);
	    UIManager.put("TextArea.font", myFont);
	    UIManager.put("TextPane.font", myFont);
	    UIManager.put("EditorPane.font", myFont);
	    UIManager.put("TabbedPane.smallFont", myFont);
	    UIManager.put("TitledBorder.font", myFont);
	    UIManager.put("ToolBar.font", myFont);
	    UIManager.put("ToolTip.font", myFont);
	    UIManager.put("Tree.font", myFont);
	    UIManager.put("FormattedTextField.font", myFont);
	    UIManager.put("IconButton.font", myFont);
	    UIManager.put("InternalFrame.optionDialogTitleFont", myFont);
	    UIManager.put("InternalFrame.paletteTitleFont", myFont);
	    UIManager.put("InternalFrame.titleFont", myFont);
	}

}
