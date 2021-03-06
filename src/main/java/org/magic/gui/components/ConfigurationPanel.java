package org.magic.gui.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.Currency;
import java.util.Locale;

import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.magic.api.beans.MTGNotification;
import org.magic.api.beans.MagicCard;
import org.magic.api.beans.MagicCollection;
import org.magic.api.beans.MagicEdition;
import org.magic.api.interfaces.MTGDao;
import org.magic.services.MTGControler;
import org.magic.services.MTGLogger;
import org.magic.services.ThreadManager;
import org.magic.services.extra.IconSetProvider;
import org.magic.tools.ImageUtils;
import org.magic.tools.InstallCert;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ConfigurationPanel extends JPanel {

	private JTextField textField;
	private JComboBox<MTGDao> cboTargetDAO;
	private JComboBox<MagicCollection> cboCollections;
	private JComboBox<Level> cboLogLevels;
	private JTextField txtdirWebsite;
	private JComboBox<MagicEdition> cboEditionLands;
	private JTextField txtMinPrice;
	private JComboBox<String> cbojsonView;
	private JTextField txtWebSiteCertificate;
	private JCheckBox chkToolTip;
	private JBuzyLabel lblLoading;
	private JTextField txtName;
	private JLabel lblIconAvatar;
	private JSpinner spinCardW;
	private JSpinner spinCardH;
	private JCheckBox chckbxIconset;
	private JCheckBox chckbxIconcards;

	private JCheckBox chckbxSearch;
	private JCheckBox chckbxCollection;
	private JCheckBox chckbxDashboard;
	private JCheckBox chckbxGame;
	private JCheckBox chckbxDeckBuilder;
	private JCheckBox chckbxShopper;
	private JCheckBox chckbxAlert;
	private JCheckBox chckbxRss;
	private JCheckBox chckbxCardBuilder;
	private JCheckBox chckbxStock;

	private transient Logger logger = MTGLogger.getLogger(this.getClass());

	public void loading(boolean show, String text) {
		lblLoading.buzy(show,text);
	}

	public ConfigurationPanel() {
		cboTargetDAO = new JComboBox<>();
		cboCollections = new JComboBox<>();
		cboEditionLands = new JComboBox<>();
		lblLoading = new JBuzyLabel();
		
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[] { 396, 212, 0 };
		gridBagLayout.rowHeights = new int[] { 179, 0, 0, 0, 0 };
		gridBagLayout.columnWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);

		for (MTGDao daos : MTGControler.getInstance().getDaoProviders())
			if (!daos.getName().equals(MTGControler.getInstance().getEnabledDAO().getName())) {

				cboTargetDAO.addItem(daos);
			}

		JPanel panelDAO = new JPanel();
		panelDAO.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				MTGControler.getInstance().getLangService().getCapitalize("DATABASES"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(64, 64, 64)));
		GridBagConstraints gbcpanelDAO = new GridBagConstraints();
		gbcpanelDAO.insets = new Insets(0, 0, 5, 5);
		gbcpanelDAO.fill = GridBagConstraints.BOTH;
		gbcpanelDAO.gridx = 0;
		gbcpanelDAO.gridy = 0;
		add(panelDAO, gbcpanelDAO);
		GridBagLayout gblpanelDAO = new GridBagLayout();
		gblpanelDAO.columnWidths = new int[] { 0, 0, 130, 0, 0 };
		gblpanelDAO.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gblpanelDAO.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gblpanelDAO.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelDAO.setLayout(gblpanelDAO);

		JLabel lblBackupDao = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("DAO_BACKUP") + " : ");
		GridBagConstraints gbclblBackupDao = new GridBagConstraints();
		gbclblBackupDao.insets = new Insets(0, 0, 5, 5);
		gbclblBackupDao.gridx = 0;
		gbclblBackupDao.gridy = 0;
		panelDAO.add(lblBackupDao, gbclblBackupDao);

		textField = new JTextField();
		GridBagConstraints gbctextField = new GridBagConstraints();
		gbctextField.fill = GridBagConstraints.HORIZONTAL;
		gbctextField.gridwidth = 2;
		gbctextField.insets = new Insets(0, 0, 5, 5);
		gbctextField.gridx = 1;
		gbctextField.gridy = 0;
		panelDAO.add(textField, gbctextField);
		textField.setColumns(10);

		JButton btnBackup = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnBackup = new GridBagConstraints();
		gbcbtnBackup.insets = new Insets(0, 0, 5, 0);
		gbcbtnBackup.gridx = 3;
		gbcbtnBackup.gridy = 0;
		panelDAO.add(btnBackup, gbcbtnBackup);

		JLabel lblDuplicateDb = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("DUPLICATE_TO",
				MTGControler.getInstance().getEnabledDAO()));
		GridBagConstraints gbclblDuplicateDb = new GridBagConstraints();
		gbclblDuplicateDb.insets = new Insets(0, 0, 5, 5);
		gbclblDuplicateDb.gridx = 0;
		gbclblDuplicateDb.gridy = 1;
		panelDAO.add(lblDuplicateDb, gbclblDuplicateDb);

		GridBagConstraints gbccboTargetDAO = new GridBagConstraints();
		gbccboTargetDAO.fill = GridBagConstraints.HORIZONTAL;
		gbccboTargetDAO.gridwidth = 2;
		gbccboTargetDAO.insets = new Insets(0, 0, 5, 5);
		gbccboTargetDAO.gridx = 1;
		gbccboTargetDAO.gridy = 1;
		panelDAO.add(cboTargetDAO, gbccboTargetDAO);

		JButton btnDuplicate = new JButton((MTGControler.getInstance().getLangService().getCapitalize("SAVE")));
		GridBagConstraints gbcbtnDuplicate = new GridBagConstraints();
		gbcbtnDuplicate.insets = new Insets(0, 0, 5, 0);
		gbcbtnDuplicate.gridx = 3;
		gbcbtnDuplicate.gridy = 1;
		panelDAO.add(btnDuplicate, gbcbtnDuplicate);

		JLabel lblLocation = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("LOCATION") + " : ");
		GridBagConstraints gbclblLocation = new GridBagConstraints();
		gbclblLocation.insets = new Insets(0, 0, 5, 5);
		gbclblLocation.gridx = 0;
		gbclblLocation.gridy = 3;
		panelDAO.add(lblLocation, gbclblLocation);

		JLabel lblLocationValue = new JLabel(MTGControler.getInstance().getEnabledDAO().getDBLocation());
		GridBagConstraints gbclblLocationValue = new GridBagConstraints();
		gbclblLocationValue.gridwidth = 2;
		gbclblLocationValue.insets = new Insets(0, 0, 5, 5);
		gbclblLocationValue.gridx = 1;
		gbclblLocationValue.gridy = 3;
		panelDAO.add(lblLocationValue, gbclblLocationValue);

		JLabel lblSize = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("SIZE") + " : ");
		GridBagConstraints gbclblSize = new GridBagConstraints();
		gbclblSize.insets = new Insets(0, 0, 0, 5);
		gbclblSize.gridx = 0;
		gbclblSize.gridy = 4;
		panelDAO.add(lblSize, gbclblSize);

		JLabel lblSizeValue = new JLabel(
				String.valueOf(MTGControler.getInstance().getEnabledDAO().getDBSize() / 1024 / 1024) + "MB");
		GridBagConstraints gbclblSizeValue = new GridBagConstraints();
		gbclblSizeValue.gridwidth = 2;
		gbclblSizeValue.insets = new Insets(0, 0, 0, 5);
		gbclblSizeValue.gridx = 1;
		gbclblSizeValue.gridy = 4;
		panelDAO.add(lblSizeValue, gbclblSizeValue);

		btnDuplicate.addActionListener(ae -> ThreadManager.getInstance().execute(() -> {
			try {
				MTGDao dao = (MTGDao) cboTargetDAO.getSelectedItem();
				loading(true, MTGControler.getInstance().getLangService().getCapitalize("DUPLICATE_TO",
						MTGControler.getInstance().getEnabledDAO()) + " " + dao);

				dao.init();
				for (MagicCollection col : MTGControler.getInstance().getEnabledDAO().getCollections())
					for (MagicCard mc : MTGControler.getInstance().getEnabledDAO().listCardsFromCollection(col)) {
						dao.saveCard(mc, col);
					}

				loading(false, "");
			} catch (Exception e) {
				loading(false, "");
				logger.error(e);
			}
		}, "duplicate " + MTGControler.getInstance().getEnabledDAO() + " to " + cboTargetDAO.getSelectedItem())

		);
		btnBackup.addActionListener(ae ->

		ThreadManager.getInstance().execute(() -> {
			try {
				loading(true, "backup db " + MTGControler.getInstance().getEnabledDAO() + " database");
				MTGControler.getInstance().getEnabledDAO().backup(new File(textField.getText()));
				loading(false,"");

			} catch (Exception e1) {
				logger.error(e1);
			}
		}, "backup " + MTGControler.getInstance().getEnabledDAO() + " database"));

		JPanel panelConfig = new JPanel();
		panelConfig.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				MTGControler.getInstance().getLangService().getCapitalize("CONFIGURATION"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(64, 64, 64)));
		GridBagConstraints gbcpanelConfig = new GridBagConstraints();
		gbcpanelConfig.gridheight = 2;
		gbcpanelConfig.insets = new Insets(0, 0, 5, 0);
		gbcpanelConfig.fill = GridBagConstraints.BOTH;
		gbcpanelConfig.gridx = 1;
		gbcpanelConfig.gridy = 0;
		add(panelConfig, gbcpanelConfig);
		GridBagLayout gblpanelConfig = new GridBagLayout();
		gblpanelConfig.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gblpanelConfig.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gblpanelConfig.columnWeights = new double[] { 1.0, 1.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gblpanelConfig.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		panelConfig.setLayout(gblpanelConfig);

		JLabel lblMainCol = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("MAIN_COLLECTION") + " :");
		GridBagConstraints gbclblMainCol = new GridBagConstraints();
		gbclblMainCol.insets = new Insets(0, 0, 5, 5);
		gbclblMainCol.gridx = 0;
		gbclblMainCol.gridy = 0;
		panelConfig.add(lblMainCol, gbclblMainCol);

		GridBagConstraints gbccboCollections = new GridBagConstraints();
		gbccboCollections.fill = GridBagConstraints.HORIZONTAL;
		gbccboCollections.gridwidth = 3;
		gbccboCollections.insets = new Insets(0, 0, 5, 5);
		gbccboCollections.gridx = 1;
		gbccboCollections.gridy = 0;
		panelConfig.add(cboCollections, gbccboCollections);

		JButton btnSaveDefaultLib = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnSave = new GridBagConstraints();
		gbcbtnSave.insets = new Insets(0, 0, 5, 5);
		gbcbtnSave.gridx = 4;
		gbcbtnSave.gridy = 0;
		panelConfig.add(btnSaveDefaultLib, gbcbtnSave);

		JLabel lblDefaultLandManuel = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("DEFAULT_LAND_IMPORT") + " :");
		GridBagConstraints gbclblDefaultLandManuel = new GridBagConstraints();
		gbclblDefaultLandManuel.insets = new Insets(0, 0, 5, 5);
		gbclblDefaultLandManuel.gridx = 0;
		gbclblDefaultLandManuel.gridy = 1;
		panelConfig.add(lblDefaultLandManuel, gbclblDefaultLandManuel);

		GridBagConstraints gbccboEditionLands = new GridBagConstraints();
		gbccboEditionLands.fill = GridBagConstraints.HORIZONTAL;
		gbccboEditionLands.gridwidth = 3;
		gbccboEditionLands.insets = new Insets(0, 0, 5, 5);
		gbccboEditionLands.gridx = 1;
		gbccboEditionLands.gridy = 1;
		panelConfig.add(cboEditionLands, gbccboEditionLands);

		JButton btnSaveDefaultLandDeck = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnSave1 = new GridBagConstraints();
		gbcbtnSave1.insets = new Insets(0, 0, 5, 5);
		gbcbtnSave1.gridx = 4;
		gbcbtnSave1.gridy = 1;
		panelConfig.add(btnSaveDefaultLandDeck, gbcbtnSave1);

		JLabel lblLogLevel = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("LOG_LEVEL") + " :");
		GridBagConstraints gbclblLogLevel = new GridBagConstraints();
		gbclblLogLevel.insets = new Insets(0, 0, 5, 5);
		gbclblLogLevel.gridx = 0;
		gbclblLogLevel.gridy = 2;
		panelConfig.add(lblLogLevel, gbclblLogLevel);

		cboLogLevels = new JComboBox<>(
				new DefaultComboBoxModel<>(new Level[] { Level.INFO, Level.ERROR, Level.DEBUG, Level.TRACE }));
		GridBagConstraints gbccboLogLevels = new GridBagConstraints();
		gbccboLogLevels.fill = GridBagConstraints.HORIZONTAL;
		gbccboLogLevels.gridwidth = 3;
		gbccboLogLevels.insets = new Insets(0, 0, 5, 5);
		gbccboLogLevels.gridx = 1;
		gbccboLogLevels.gridy = 2;
		panelConfig.add(cboLogLevels, gbccboLogLevels);

		JButton btnSaveLoglevel = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnSaveLoglevel = new GridBagConstraints();
		gbcbtnSaveLoglevel.insets = new Insets(0, 0, 5, 5);
		gbcbtnSaveLoglevel.gridx = 4;
		gbcbtnSaveLoglevel.gridy = 2;
		panelConfig.add(btnSaveLoglevel, gbcbtnSaveLoglevel);

		JLabel lblShowJsonPanel = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("SHOW_JSON_PANEL") + " :");
		GridBagConstraints gbclblShowJsonPanel = new GridBagConstraints();
		gbclblShowJsonPanel.insets = new Insets(0, 0, 5, 5);
		gbclblShowJsonPanel.gridx = 0;
		gbclblShowJsonPanel.gridy = 3;
		panelConfig.add(lblShowJsonPanel, gbclblShowJsonPanel);

		cbojsonView = new JComboBox<>();
		GridBagConstraints gbccbojsonView = new GridBagConstraints();
		gbccbojsonView.fill = GridBagConstraints.HORIZONTAL;
		gbccbojsonView.gridwidth = 3;
		gbccbojsonView.insets = new Insets(0, 0, 5, 5);
		gbccbojsonView.gridx = 1;
		gbccbojsonView.gridy = 3;
		panelConfig.add(cbojsonView, gbccbojsonView);
		cbojsonView.setModel(new DefaultComboBoxModel<String>(new String[] { "true", "false" }));
		cbojsonView.setSelectedItem(MTGControler.getInstance().get("debug-json-panel"));

		JButton btnSaveJson = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnSaveJson = new GridBagConstraints();
		gbcbtnSaveJson.insets = new Insets(0, 0, 5, 5);
		gbcbtnSaveJson.gridx = 4;
		gbcbtnSaveJson.gridy = 3;
		panelConfig.add(btnSaveJson, gbcbtnSaveJson);
		btnSaveJson.addActionListener(
				ae -> MTGControler.getInstance().setProperty("debug-json-panel", cbojsonView.getSelectedItem()));

		JLabel lblDontTakeAlert = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("SHOW_LOW_PRICES") + " :");
		GridBagConstraints gbclblDontTakeAlert = new GridBagConstraints();
		gbclblDontTakeAlert.insets = new Insets(0, 0, 5, 5);
		gbclblDontTakeAlert.gridx = 0;
		gbclblDontTakeAlert.gridy = 4;
		panelConfig.add(lblDontTakeAlert, gbclblDontTakeAlert);

		txtMinPrice = new JTextField(MTGControler.getInstance().get("min-price-alert"));
		GridBagConstraints gbctxtMinPrice = new GridBagConstraints();
		gbctxtMinPrice.gridwidth = 3;
		gbctxtMinPrice.insets = new Insets(0, 0, 5, 5);
		gbctxtMinPrice.gridx = 1;
		gbctxtMinPrice.gridy = 4;
		panelConfig.add(txtMinPrice, gbctxtMinPrice);
		txtMinPrice.setColumns(10);

		JButton btnSavePrice = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnSavePrice = new GridBagConstraints();
		gbcbtnSavePrice.insets = new Insets(0, 0, 5, 5);
		gbcbtnSavePrice.gridx = 4;
		gbcbtnSavePrice.gridy = 4;
		panelConfig.add(btnSavePrice, gbcbtnSavePrice);
		btnSavePrice.addActionListener(
				ae -> MTGControler.getInstance().setProperty("min-price-alert", txtMinPrice.getText()));

		JLabel lblShowTooltip = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("SHOW_TOOLTIP") + " :");
		GridBagConstraints gbclblShowTooltip = new GridBagConstraints();
		gbclblShowTooltip.insets = new Insets(0, 0, 5, 5);
		gbclblShowTooltip.gridx = 0;
		gbclblShowTooltip.gridy = 5;
		panelConfig.add(lblShowTooltip, gbclblShowTooltip);

		chkToolTip = new JCheckBox("");
		GridBagConstraints gbcchkToolTip = new GridBagConstraints();
		gbcchkToolTip.insets = new Insets(0, 0, 5, 5);
		gbcchkToolTip.gridx = 2;
		gbcchkToolTip.gridy = 5;
		panelConfig.add(chkToolTip, gbcchkToolTip);
		chkToolTip.setSelected(MTGControler.getInstance().get("tooltip").equals("true"));
		chkToolTip.addItemListener(ie -> MTGControler.getInstance().setProperty("tooltip", chkToolTip.isSelected()));

		JLabel lblCardsLanguage = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("CARDS_LANGUAGE") + " :");
		GridBagConstraints gbclblCardsLanguage = new GridBagConstraints();
		gbclblCardsLanguage.insets = new Insets(0, 0, 5, 5);
		gbclblCardsLanguage.gridx = 0;
		gbclblCardsLanguage.gridy = 6;
		panelConfig.add(lblCardsLanguage, gbclblCardsLanguage);

		final JComboBox<String> cboLanguages = new JComboBox<>();

		for (String s : MTGControler.getInstance().getEnabledCardsProviders().getLanguages()) {
			cboLanguages.addItem(s);
			if (MTGControler.getInstance().get("langage").equals(s))
				cboLanguages.setSelectedItem(s);
		}

		GridBagConstraints gbccboLanguages = new GridBagConstraints();
		gbccboLanguages.gridwidth = 3;
		gbccboLanguages.insets = new Insets(0, 0, 5, 5);
		gbccboLanguages.fill = GridBagConstraints.HORIZONTAL;
		gbccboLanguages.gridx = 1;
		gbccboLanguages.gridy = 6;
		panelConfig.add(cboLanguages, gbccboLanguages);

		JButton btnSavelang = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		btnSavelang.addActionListener(
				ae -> MTGControler.getInstance().setProperty("langage", cboLanguages.getSelectedItem().toString()));

		GridBagConstraints gbcbtnSavelang = new GridBagConstraints();
		gbcbtnSavelang.insets = new Insets(0, 0, 5, 5);
		gbcbtnSavelang.gridx = 4;
		gbcbtnSavelang.gridy = 6;
		panelConfig.add(btnSavelang, gbcbtnSavelang);

		JLabel lblGuiLocal = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("LOCALISATION") + " :");
		GridBagConstraints gbclblGuiLocal = new GridBagConstraints();
		gbclblGuiLocal.insets = new Insets(0, 0, 5, 5);
		gbclblGuiLocal.gridx = 0;
		gbclblGuiLocal.gridy = 7;
		panelConfig.add(lblGuiLocal, gbclblGuiLocal);

		JComboBox<Locale> cboLocales = new JComboBox<>(
				new DefaultComboBoxModel<Locale>(MTGControler.getInstance().getLangService().getAvailableLocale()));
		GridBagConstraints gbccboLocales = new GridBagConstraints();
		gbccboLocales.gridwidth = 3;
		gbccboLocales.insets = new Insets(0, 0, 5, 5);
		gbccboLocales.fill = GridBagConstraints.HORIZONTAL;
		gbccboLocales.gridx = 1;
		gbccboLocales.gridy = 7;
		panelConfig.add(cboLocales, gbccboLocales);

		cboLocales.setSelectedItem(MTGControler.getInstance().getLocale());
		JButton btnSave = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		btnSave.addActionListener(ae -> MTGControler.getInstance().setProperty("locale", cboLocales.getSelectedItem()));

		GridBagConstraints gbcbtnSave3 = new GridBagConstraints();
		gbcbtnSave3.insets = new Insets(0, 0, 5, 5);
		gbcbtnSave3.gridx = 4;
		gbcbtnSave3.gridy = 7;
		panelConfig.add(btnSave, gbcbtnSave3);

		JLabel lblCleancache = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("CLEAN_CACHE") + " :");
		GridBagConstraints gbclblCleancache = new GridBagConstraints();
		gbclblCleancache.insets = new Insets(0, 0, 5, 5);
		gbclblCleancache.gridx = 0;
		gbclblCleancache.gridy = 8;
		panelConfig.add(lblCleancache, gbclblCleancache);

		JButton btnClean = new JButton(MTGControler.getInstance().getLangService().getCapitalize("CLEAN"));
		btnClean.addActionListener(ae -> {

			try {
				loading(true, MTGControler.getInstance().getLangService().getCapitalize("CLEAN"));
				IconSetProvider.getInstance().clean();
				MTGControler.getInstance().getEnabledCache().clear();
				loading(false, "");
			} catch (Exception e) {
				logger.error(e);
				loading(false, "");
			}
		});

		JPanel panelCheckCache = new JPanel();
		GridBagConstraints gbcPanelCheckCache = new GridBagConstraints();
		gbcPanelCheckCache.anchor = GridBagConstraints.WEST;
		gbcPanelCheckCache.gridwidth = 3;
		gbcPanelCheckCache.insets = new Insets(0, 0, 5, 5);
		gbcPanelCheckCache.fill = GridBagConstraints.VERTICAL;
		gbcPanelCheckCache.gridx = 1;
		gbcPanelCheckCache.gridy = 8;
		panelConfig.add(panelCheckCache, gbcPanelCheckCache);

		chckbxIconset = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("IMG_SET"));
		panelCheckCache.add(chckbxIconset);
		chckbxIconset.setSelected(true);

		chckbxIconcards = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("IMG_CARD"));
		panelCheckCache.add(chckbxIconcards);
		chckbxIconcards.setSelected(true);
		GridBagConstraints gbcbtnClean = new GridBagConstraints();
		gbcbtnClean.fill = GridBagConstraints.HORIZONTAL;
		gbcbtnClean.insets = new Insets(0, 0, 5, 5);
		gbcbtnClean.gridx = 4;
		gbcbtnClean.gridy = 8;
		panelConfig.add(btnClean, gbcbtnClean);

		JLabel lblLook = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("LOOK") + " :");
		GridBagConstraints gbclblLook = new GridBagConstraints();
		gbclblLook.insets = new Insets(0, 0, 5, 5);
		gbclblLook.gridx = 0;
		gbclblLook.gridy = 9;
		panelConfig.add(lblLook, gbclblLook);

		JComboBox<LookAndFeelInfo> cboLook = new JComboBox<>(
				new DefaultComboBoxModel<>(MTGControler.getInstance().getLafService().getAllLookAndFeel()));
		cboLook.setRenderer(new DefaultListCellRenderer() {
			@Override
			public Component getListCellRendererComponent(javax.swing.JList<?> list, Object value, int index,
					boolean isSelected, boolean cellHasFocus) {
				return new JLabel(((LookAndFeelInfo) value).getName());
			}
		});

		GridBagConstraints gbccomboBox = new GridBagConstraints();
		gbccomboBox.gridwidth = 3;
		gbccomboBox.insets = new Insets(0, 0, 5, 5);
		gbccomboBox.fill = GridBagConstraints.HORIZONTAL;
		gbccomboBox.gridx = 1;
		gbccomboBox.gridy = 9;
		panelConfig.add(cboLook, gbccomboBox);
		
		JLabel lblCurrency = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("CURRENCY")+" :");
		GridBagConstraints gbclblCurrency = new GridBagConstraints();
		gbclblCurrency.insets = new Insets(0, 0, 0, 5);
		gbclblCurrency.gridx = 0;
		gbclblCurrency.gridy = 10;
		panelConfig.add(lblCurrency, gbclblCurrency);
		
		JComboBox<Currency> cboCurrency = new JComboBox<>(new DefaultComboBoxModel<>(Currency.getAvailableCurrencies().toArray(new Currency[Currency.getAvailableCurrencies().size()])));
		
		if(MTGControler.getInstance().get("currency").isEmpty())
			cboCurrency.setSelectedItem(Currency.getInstance(Locale.getDefault()));
		else
			cboCurrency.setSelectedItem(Currency.getInstance(MTGControler.getInstance().get("currency")));
		
		GridBagConstraints gbccboCurrency = new GridBagConstraints();
		gbccboCurrency.gridwidth = 3;
		gbccboCurrency.insets = new Insets(0, 0, 0, 5);
		gbccboCurrency.fill = GridBagConstraints.HORIZONTAL;
		gbccboCurrency.gridx = 1;
		gbccboCurrency.gridy = 10;
		panelConfig.add(cboCurrency, gbccboCurrency);
		
		JButton btnSavecurrency = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		btnSavecurrency.addActionListener(ae->MTGControler.getInstance().setProperty("currency", cboCurrency.getSelectedItem()));
		GridBagConstraints gbcbtnSavecurrency = new GridBagConstraints();
		gbcbtnSavecurrency.insets = new Insets(0, 0, 0, 5);
		gbcbtnSavecurrency.fill = GridBagConstraints.HORIZONTAL;
		gbcbtnSavecurrency.gridx = 4;
		gbcbtnSavecurrency.gridy = 10;
		panelConfig.add(btnSavecurrency, gbcbtnSavecurrency);
		cboLook.addActionListener(ae -> MTGControler.getInstance().getLafService().setLookAndFeel(
				SwingUtilities.getAncestorOfClass(JFrame.class, this), (LookAndFeelInfo) cboLook.getSelectedItem()));

		btnSaveLoglevel.addActionListener(ae -> {
			if (chckbxIconset.isSelected())
				MTGControler.getInstance().setProperty("loglevel", (Level) cboLogLevels.getSelectedItem());
			MTGLogger.changeLevel((Level) cboLogLevels.getSelectedItem());
		});

		cboLogLevels.addActionListener(ae -> MTGLogger.changeLevel((Level) cboLogLevels.getSelectedItem()));
		btnSaveDefaultLandDeck.addActionListener(ae -> MTGControler.getInstance().setProperty("default-land-deck",
				((MagicEdition) cboEditionLands.getSelectedItem()).getId()));
		btnSaveDefaultLib.addActionListener(ae -> {
			try {

				MTGControler.getInstance().setProperty("default-library",
						(MagicCollection) cboCollections.getSelectedItem());
			} catch (Exception e) {
				logger.error(e);
			}
		});

		try {
			for (MagicCollection col : MTGControler.getInstance().getEnabledDAO().getCollections()) {
				cboCollections.addItem(col);
				if (col.getName().equalsIgnoreCase(MTGControler.getInstance().get("default-library"))) {
					cboCollections.setSelectedItem(col);
				}
			}
		} catch (Exception e1) {
			logger.error(e1);
		}

		JPanel panelWebSite = new JPanel();
		panelWebSite.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				MTGControler.getInstance().getLangService().getCapitalize("WEBSITE"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(64, 64, 64)));
		GridBagConstraints gbcpanelWebSite = new GridBagConstraints();
		gbcpanelWebSite.insets = new Insets(0, 0, 5, 5);
		gbcpanelWebSite.fill = GridBagConstraints.BOTH;
		gbcpanelWebSite.gridx = 0;
		gbcpanelWebSite.gridy = 1;
		add(panelWebSite, gbcpanelWebSite);
		GridBagLayout gblpanelWebSite = new GridBagLayout();
		gblpanelWebSite.columnWidths = new int[] { 0, 0, 0, 103, 0, 0 };
		gblpanelWebSite.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gblpanelWebSite.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gblpanelWebSite.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelWebSite.setLayout(gblpanelWebSite);

		JLabel lblWebsiteDir = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("DIRECTORY") + " :");
		GridBagConstraints gbclblWebsiteDir = new GridBagConstraints();
		gbclblWebsiteDir.insets = new Insets(0, 0, 5, 5);
		gbclblWebsiteDir.gridx = 0;
		gbclblWebsiteDir.gridy = 0;
		panelWebSite.add(lblWebsiteDir, gbclblWebsiteDir);

		txtdirWebsite = new JTextField(MTGControler.getInstance().get("default-website-dir"));
		GridBagConstraints gbctxtdirWebsite = new GridBagConstraints();
		gbctxtdirWebsite.fill = GridBagConstraints.HORIZONTAL;
		gbctxtdirWebsite.gridwidth = 3;
		gbctxtdirWebsite.insets = new Insets(0, 0, 5, 5);
		gbctxtdirWebsite.gridx = 1;
		gbctxtdirWebsite.gridy = 0;
		panelWebSite.add(txtdirWebsite, gbctxtdirWebsite);
		txtdirWebsite.setColumns(10);

		JButton btnWebsiteSave = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnWebsiteSave = new GridBagConstraints();
		gbcbtnWebsiteSave.insets = new Insets(0, 0, 5, 0);
		gbcbtnWebsiteSave.gridx = 4;
		gbcbtnWebsiteSave.gridy = 0;
		panelWebSite.add(btnWebsiteSave, gbcbtnWebsiteSave);

		JLabel lblAddWebsiteCertificate = new JLabel(
				MTGControler.getInstance().getLangService().getCapitalize("ADD_CERTIFICATE") + " :");
		GridBagConstraints gbclblAddWebsiteCertificate = new GridBagConstraints();
		gbclblAddWebsiteCertificate.insets = new Insets(0, 0, 5, 5);
		gbclblAddWebsiteCertificate.gridx = 0;
		gbclblAddWebsiteCertificate.gridy = 1;
		panelWebSite.add(lblAddWebsiteCertificate, gbclblAddWebsiteCertificate);

		txtWebSiteCertificate = new JTextField();
		GridBagConstraints gbctxtWebSiteCertificate = new GridBagConstraints();
		gbctxtWebSiteCertificate.fill = GridBagConstraints.HORIZONTAL;
		gbctxtWebSiteCertificate.gridwidth = 3;
		gbctxtWebSiteCertificate.insets = new Insets(0, 0, 5, 5);
		gbctxtWebSiteCertificate.gridx = 1;
		gbctxtWebSiteCertificate.gridy = 1;
		panelWebSite.add(txtWebSiteCertificate, gbctxtWebSiteCertificate);
		txtWebSiteCertificate.setText("www.");
		txtWebSiteCertificate.setColumns(10);

		JButton btnAdd = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		GridBagConstraints gbcbtnAdd = new GridBagConstraints();
		gbcbtnAdd.insets = new Insets(0, 0, 5, 0);
		gbcbtnAdd.gridx = 4;
		gbcbtnAdd.gridy = 1;
		panelWebSite.add(btnAdd, gbcbtnAdd);

		JPanel panelProfil = new JPanel();
		panelProfil.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true),
				MTGControler.getInstance().getLangService().getCapitalize("GAME"), TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(64, 64, 64)));
		GridBagConstraints gbcpanelProfil = new GridBagConstraints();
		gbcpanelProfil.insets = new Insets(0, 0, 5, 5);
		gbcpanelProfil.fill = GridBagConstraints.BOTH;
		gbcpanelProfil.gridx = 0;
		gbcpanelProfil.gridy = 2;
		add(panelProfil, gbcpanelProfil);
		GridBagLayout gblpanelProfil = new GridBagLayout();
		gblpanelProfil.columnWidths = new int[] { 0, 71, 0, 0, 0, 0 };
		gblpanelProfil.rowHeights = new int[] { 0, 0, 0, 0, 0, 0 };
		gblpanelProfil.columnWeights = new double[] { 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gblpanelProfil.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelProfil.setLayout(gblpanelProfil);

		JLabel lblName = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("NAME") + " :");
		GridBagConstraints gbclblName = new GridBagConstraints();
		gbclblName.anchor = GridBagConstraints.EAST;
		gbclblName.insets = new Insets(0, 0, 5, 5);
		gbclblName.gridx = 0;
		gbclblName.gridy = 0;
		panelProfil.add(lblName, gbclblName);

		txtName = new JTextField(MTGControler.getInstance().get("/game/player-profil/name"));
		GridBagConstraints gbctxtName = new GridBagConstraints();
		gbctxtName.gridwidth = 3;
		gbctxtName.insets = new Insets(0, 0, 5, 5);
		gbctxtName.fill = GridBagConstraints.HORIZONTAL;
		gbctxtName.gridx = 1;
		gbctxtName.gridy = 0;
		panelProfil.add(txtName, gbctxtName);
		txtName.setColumns(10);

		JLabel lblAvatar = new JLabel("Avatar :");
		lblIconAvatar = new JLabel();
		GridBagConstraints gbclblAvatar = new GridBagConstraints();
		gbclblAvatar.insets = new Insets(0, 0, 5, 5);
		gbclblAvatar.gridx = 0;
		gbclblAvatar.gridy = 1;
		panelProfil.add(lblAvatar, gbclblAvatar);

		loadIcon();

		lblIconAvatar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent paramMouseEvent) {
				JFileChooser jf = new JFileChooser();
				jf.setFileFilter(new FileNameExtensionFilter("Images", "bmp", "gif", "jpg", "jpeg", "png"));
				int result = jf.showOpenDialog(null);
				if (result == JFileChooser.APPROVE_OPTION) {
					MTGControler.getInstance().setProperty("/game/player-profil/avatar",
							jf.getSelectedFile().getAbsolutePath());
					loadIcon();
				}
			}
		});

		lblIconAvatar.setBorder(new LineBorder(Color.RED, 1, true));
		GridBagConstraints gbclblIconAvatar = new GridBagConstraints();
		gbclblIconAvatar.fill = GridBagConstraints.BOTH;
		gbclblIconAvatar.gridwidth = 2;
		gbclblIconAvatar.gridheight = 4;
		gbclblIconAvatar.insets = new Insets(0, 0, 0, 5);
		gbclblIconAvatar.gridx = 1;
		gbclblIconAvatar.gridy = 1;
		panelProfil.add(lblIconAvatar, gbclblIconAvatar);

		JButton btnSave2 = new JButton(MTGControler.getInstance().getLangService().getCapitalize("SAVE"));
		btnSave2.addActionListener(ae -> {
			MTGControler.getInstance().setProperty("/game/player-profil/name", txtName.getText());
			MTGControler.getInstance().setProperty("/game/cards/card-width", spinCardW.getValue());
			MTGControler.getInstance().setProperty("/game/cards/card-heigth", spinCardH.getValue());
		});

		JPanel panelSubGame = new JPanel();
		GridBagConstraints gbcpanelSubGame = new GridBagConstraints();
		gbcpanelSubGame.gridheight = 3;
		gbcpanelSubGame.insets = new Insets(0, 0, 5, 5);
		gbcpanelSubGame.fill = GridBagConstraints.BOTH;
		gbcpanelSubGame.gridx = 3;
		gbcpanelSubGame.gridy = 1;
		panelProfil.add(panelSubGame, gbcpanelSubGame);
		panelSubGame.setLayout(new GridLayout(3, 2, 0, 0));

		JLabel lblCardW = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("CARD_WIDTH") + " :");
		panelSubGame.add(lblCardW);

		spinCardW = new JSpinner();
		spinCardW.setModel(new SpinnerNumberModel(154, 0, null, 1));
		spinCardW.setValue(Integer.parseInt(MTGControler.getInstance().get("/game/cards/card-width")));

		panelSubGame.add(spinCardW);

		JLabel lblCardH = new JLabel(MTGControler.getInstance().getLangService().getCapitalize("CARD_HEIGHT") + " :");
		panelSubGame.add(lblCardH);

		spinCardH = new JSpinner();
		spinCardH.setModel(new SpinnerNumberModel(215, 0, null, 1));
		spinCardH.setValue(Integer.parseInt(MTGControler.getInstance().get("/game/cards/card-height")));
		panelSubGame.add(spinCardH);

		GridBagConstraints gbcbtnSave2 = new GridBagConstraints();
		gbcbtnSave2.insets = new Insets(0, 0, 0, 5);
		gbcbtnSave2.gridx = 3;
		gbcbtnSave2.gridy = 4;
		panelProfil.add(btnSave2, gbcbtnSave2);

		JPanel panelModule = new JPanel();
		panelModule.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "Modules",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(64, 64, 64)));
		GridBagConstraints gbcpanelModule = new GridBagConstraints();
		gbcpanelModule.insets = new Insets(0, 0, 5, 0);
		gbcpanelModule.fill = GridBagConstraints.BOTH;
		gbcpanelModule.gridx = 1;
		gbcpanelModule.gridy = 2;
		add(panelModule, gbcpanelModule);
		GridBagLayout gblPanelModule = new GridBagLayout();
		gblPanelModule.columnWidths = new int[] { 0, 0, 0, 103, 0, 0 };
		gblPanelModule.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gblPanelModule.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		gblPanelModule.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		panelModule.setLayout(gblPanelModule);

		chckbxSearch = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("SEARCH_MODULE"));
		chckbxSearch.setSelected(MTGControler.getInstance().get("modules/search").equals("true"));
		chckbxSearch.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/search", chckbxSearch.isSelected()));
		GridBagConstraints gbcchckbxSearch = new GridBagConstraints();
		gbcchckbxSearch.anchor = GridBagConstraints.WEST;
		gbcchckbxSearch.insets = new Insets(0, 0, 5, 5);
		gbcchckbxSearch.gridx = 1;
		gbcchckbxSearch.gridy = 0;
		panelModule.add(chckbxSearch, gbcchckbxSearch);

		chckbxCollection = new JCheckBox("Collection");
		chckbxCollection.setSelected(MTGControler.getInstance().get("modules/collection").equals("true"));
		chckbxCollection.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/collection", chckbxCollection.isSelected()));

		GridBagConstraints gbcchckbxCollection = new GridBagConstraints();
		gbcchckbxCollection.anchor = GridBagConstraints.WEST;
		gbcchckbxCollection.insets = new Insets(0, 0, 5, 5);
		gbcchckbxCollection.gridx = 3;
		gbcchckbxCollection.gridy = 0;
		panelModule.add(chckbxCollection, gbcchckbxCollection);

		chckbxDashboard = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("DASHBOARD_MODULE"));
		chckbxDashboard.setSelected(MTGControler.getInstance().get("modules/dashboard").equals("true"));
		chckbxDashboard.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/dashboard", chckbxDashboard.isSelected()));
		GridBagConstraints gbcchckbxDashboard = new GridBagConstraints();
		gbcchckbxDashboard.anchor = GridBagConstraints.WEST;
		gbcchckbxDashboard.insets = new Insets(0, 0, 5, 5);
		gbcchckbxDashboard.gridx = 1;
		gbcchckbxDashboard.gridy = 1;
		panelModule.add(chckbxDashboard, gbcchckbxDashboard);

		chckbxGame = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("GAME_MODULE"));
		chckbxGame.setSelected(MTGControler.getInstance().get("modules/game").equals("true"));
		chckbxGame
				.addItemListener(ie -> MTGControler.getInstance().setProperty("modules/game", chckbxGame.isSelected()));
		GridBagConstraints gbcchckbxGame = new GridBagConstraints();
		gbcchckbxGame.anchor = GridBagConstraints.WEST;
		gbcchckbxGame.insets = new Insets(0, 0, 5, 5);
		gbcchckbxGame.gridx = 3;
		gbcchckbxGame.gridy = 1;
		panelModule.add(chckbxGame, gbcchckbxGame);

		chckbxDeckBuilder = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("DECK_MODULE"));
		chckbxDeckBuilder.setSelected(MTGControler.getInstance().get("modules/deckbuilder").equals("true"));
		chckbxDeckBuilder.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/deckbuilder", chckbxDeckBuilder.isSelected()));
		GridBagConstraints gbcchckbxDeckBuilder = new GridBagConstraints();
		gbcchckbxDeckBuilder.anchor = GridBagConstraints.WEST;
		gbcchckbxDeckBuilder.insets = new Insets(0, 0, 5, 5);
		gbcchckbxDeckBuilder.gridx = 1;
		gbcchckbxDeckBuilder.gridy = 2;
		panelModule.add(chckbxDeckBuilder, gbcchckbxDeckBuilder);

		chckbxShopper = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("SHOPPING_MODULE"));
		chckbxShopper.setSelected(MTGControler.getInstance().get("modules/shopper").equals("true"));
		chckbxShopper.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/shopper", chckbxShopper.isSelected()));
		GridBagConstraints gbcchckbxShopper = new GridBagConstraints();
		gbcchckbxShopper.anchor = GridBagConstraints.WEST;
		gbcchckbxShopper.insets = new Insets(0, 0, 5, 5);
		gbcchckbxShopper.gridx = 3;
		gbcchckbxShopper.gridy = 2;
		panelModule.add(chckbxShopper, gbcchckbxShopper);

		chckbxAlert = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("ALERT_MODULE"));
		chckbxAlert.setSelected(MTGControler.getInstance().get("modules/alarm").equals("true"));
		chckbxAlert.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/alarm", chckbxAlert.isSelected()));

		GridBagConstraints gbcchckbxAlert = new GridBagConstraints();
		gbcchckbxAlert.anchor = GridBagConstraints.WEST;
		gbcchckbxAlert.insets = new Insets(0, 0, 5, 5);
		gbcchckbxAlert.gridx = 1;
		gbcchckbxAlert.gridy = 3;
		panelModule.add(chckbxAlert, gbcchckbxAlert);

		chckbxRss = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("RSS_MODULE"));
		chckbxRss.setSelected(MTGControler.getInstance().get("modules/rss").equals("true"));
		chckbxRss.addItemListener(ie -> MTGControler.getInstance().setProperty("modules/rss", chckbxRss.isSelected()));

		GridBagConstraints gbcchckbxRss = new GridBagConstraints();
		gbcchckbxRss.anchor = GridBagConstraints.WEST;
		gbcchckbxRss.insets = new Insets(0, 0, 5, 5);
		gbcchckbxRss.gridx = 3;
		gbcchckbxRss.gridy = 3;
		panelModule.add(chckbxRss, gbcchckbxRss);

		chckbxCardBuilder = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("BUILDER_MODULE"));
		chckbxCardBuilder.setSelected(MTGControler.getInstance().get("modules/cardbuilder").equals("true"));
		chckbxCardBuilder.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/cardbuilder", chckbxCardBuilder.isSelected()));
		GridBagConstraints gbcchckbxCardBuilder = new GridBagConstraints();
		gbcchckbxCardBuilder.anchor = GridBagConstraints.WEST;
		gbcchckbxCardBuilder.insets = new Insets(0, 0, 5, 5);
		gbcchckbxCardBuilder.gridx = 1;
		gbcchckbxCardBuilder.gridy = 4;
		panelModule.add(chckbxCardBuilder, gbcchckbxCardBuilder);

		chckbxStock = new JCheckBox(MTGControler.getInstance().getLangService().getCapitalize("STOCK_MODULE"));
		chckbxStock.setSelected(MTGControler.getInstance().get("modules/stock").equals("true"));

		chckbxStock.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/stock", chckbxStock.isSelected()));
		GridBagConstraints gbcchckbxStock = new GridBagConstraints();
		gbcchckbxStock.insets = new Insets(0, 0, 5, 5);
		gbcchckbxStock.anchor = GridBagConstraints.WEST;
		gbcchckbxStock.gridx = 3;
		gbcchckbxStock.gridy = 4;
		panelModule.add(chckbxStock, gbcchckbxStock);

		JCheckBox chckbxHistory = new JCheckBox(
				MTGControler.getInstance().getLangService().getCapitalize("HISTORY_MODULE"));
		chckbxHistory.setSelected(MTGControler.getInstance().get("modules/history").equals("true"));
		chckbxHistory.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/history", chckbxHistory.isSelected()));
		GridBagConstraints gbcchckbxHistory = new GridBagConstraints();
		gbcchckbxHistory.anchor = GridBagConstraints.WEST;
		gbcchckbxHistory.insets = new Insets(0, 0, 0, 5);
		gbcchckbxHistory.gridx = 1;
		gbcchckbxHistory.gridy = 5;
		panelModule.add(chckbxHistory, gbcchckbxHistory);

		JCheckBox chckbxWallpaper = new JCheckBox(
				MTGControler.getInstance().getLangService().getCapitalize("WALLPAPER"));
		chckbxWallpaper.setSelected(MTGControler.getInstance().get("modules/wallpaper").equals("true"));
		chckbxWallpaper.addItemListener(
				ie -> MTGControler.getInstance().setProperty("modules/wallpaper", chckbxWallpaper.isSelected()));

		GridBagConstraints gbcchckbxWallpaper = new GridBagConstraints();
		gbcchckbxWallpaper.insets = new Insets(0, 0, 0, 5);
		gbcchckbxWallpaper.anchor = GridBagConstraints.WEST;
		gbcchckbxWallpaper.gridx = 3;
		gbcchckbxWallpaper.gridy = 5;
		panelModule.add(chckbxWallpaper, gbcchckbxWallpaper);

		GridBagConstraints gbclblLoading = new GridBagConstraints();
		gbclblLoading.gridwidth = 2;
		gbclblLoading.gridx = 0;
		gbclblLoading.gridy = 3;
		add(lblLoading, gbclblLoading);
		btnAdd.addActionListener(ae -> {
			try {
				InstallCert.installCert(txtWebSiteCertificate.getText());
			} catch (Exception e) {
				MTGControler.getInstance().notify(new MTGNotification(MTGControler.getInstance().getLangService().getError(),e));
			}
		});

		btnWebsiteSave.addActionListener(
				ae -> MTGControler.getInstance().setProperty("default-website-dir", txtdirWebsite.getText()));

		for (int i = 0; i < cboLogLevels.getItemCount(); i++) {
			if (cboLogLevels.getItemAt(i).toString().equals(MTGControler.getInstance().get("loglevel")))
				cboLogLevels.setSelectedIndex(i);

		}
		try {
			for (MagicEdition col : MTGControler.getInstance().getEnabledCardsProviders().loadEditions()) {
				cboEditionLands.addItem(col);
				if (col.getId().equalsIgnoreCase(MTGControler.getInstance().get("default-land-deck"))) {
					cboEditionLands.setSelectedItem(col);
				}
			}

		} catch (Exception e1) {
			logger.error(e1);
		}
	}

	private void loadIcon() {
		try {
			lblIconAvatar.setIcon(new ImageIcon(ImageUtils.resize(
					ImageIO.read(new File(MTGControler.getInstance().get("/game/player-profil/avatar"))), 100, 100)));

		} catch (Exception e) {
			lblIconAvatar.setIcon(null);
		}

	}

}
