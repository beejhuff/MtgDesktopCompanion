package org.magic.gui;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jdesktop.swingx.JXTreeTable;
import org.magic.api.interfaces.MTGCardsExport;
import org.magic.api.interfaces.MTGCardsProvider;
import org.magic.api.interfaces.MTGDao;
import org.magic.api.interfaces.MTGDashBoard;
import org.magic.api.interfaces.MTGDeckSniffer;
import org.magic.api.interfaces.MTGNewsProvider;
import org.magic.api.interfaces.MTGNotifier;
import org.magic.api.interfaces.MTGPictureProvider;
import org.magic.api.interfaces.MTGPicturesCache;
import org.magic.api.interfaces.MTGPricesProvider;
import org.magic.api.interfaces.MTGServer;
import org.magic.api.interfaces.MTGShopper;
import org.magic.api.interfaces.MTGWallpaperProvider;
import org.magic.gui.components.ConfigurationPanel;
import org.magic.gui.components.LoggerViewPanel;
import org.magic.gui.models.conf.ProviderTreeTableModel;
import org.magic.services.MTGConstants;
import org.magic.services.MTGControler;

public class ConfigurationPanelGUI extends JPanel {
	private JXTreeTable cardsProviderTable;
	private JXTreeTable priceProviderTable;
	private JXTreeTable daoProviderTable;
	private JXTreeTable shopperTreeTable;
	private JXTreeTable dashboardTreeTable;
	private JXTreeTable importTreeTable;
	private JXTreeTable exportsTable;
	private JXTreeTable picturesProviderTable;
	private JXTreeTable serversTreeTable;
	private JXTreeTable cachesTreeTable;
	private JXTreeTable wallpapersTreeTable;
	private JXTreeTable notificationsTreeTable;
	private LoggerViewPanel loggerViewPanel;
	private JXTreeTable newsTreeTable;

	public ConfigurationPanelGUI() {

		setLayout(new BorderLayout(0, 0));

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);

		JPanel providerConfigPanel = new JPanel();
		tabbedPane.addTab(MTGControler.getInstance().getLangService().getCapitalize("PROVIDERS"), MTGConstants.ICON_TAB_PLUGIN,
				providerConfigPanel, null);
		providerConfigPanel.setLayout(new BorderLayout(0, 0));

		JTabbedPane subTabbedProviders = new JTabbedPane(JTabbedPane.TOP);
		providerConfigPanel.add(subTabbedProviders);

		JScrollPane cardsProvidersScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("CARDS"), MTGConstants.ICON_BACK,cardsProvidersScrollPane, null);

		cardsProviderTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGCardsProvider>(false, MTGControler.getInstance().getCardsProviders()));
		cardsProvidersScrollPane.setViewportView(cardsProviderTable);
		cardsProviderTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) cardsProviderTable.getTreeTableModel())
						.setSelectedNode((MTGCardsProvider) e.getNewLeadSelectionPath().getPathComponent(1));
		});

		JScrollPane picturesScollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("PICTURES"), MTGConstants.ICON_TAB_PICTURE,
				picturesScollPane, null);

		picturesProviderTable = new JXTreeTable(new ProviderTreeTableModel<MTGPictureProvider>(false,
				MTGControler.getInstance().getPicturesProviders()));
		picturesScollPane.setViewportView(picturesProviderTable);
		picturesProviderTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) picturesProviderTable.getTreeTableModel())
						.setSelectedNode((MTGPictureProvider) e.getNewLeadSelectionPath().getPathComponent(1));
		});

		JScrollPane priceProviderScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("PRICERS"), MTGConstants.ICON_TAB_PRICES,
				priceProviderScrollPane, null);
		priceProviderTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGPricesProvider>(true, MTGControler.getInstance().getPricerProviders()));
		priceProviderTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) priceProviderTable.getTreeTableModel())
						.setSelectedNode((MTGPricesProvider) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		priceProviderScrollPane.setViewportView(priceProviderTable);

		JScrollPane daoProviderScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("DATABASES"), MTGConstants.ICON_TAB_DAO,
				daoProviderScrollPane, null);

		daoProviderTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGDao>(false, MTGControler.getInstance().getDaoProviders()));
		daoProviderTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) daoProviderTable.getTreeTableModel())
						.setSelectedNode((MTGDao) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		daoProviderScrollPane.setViewportView(daoProviderTable);

		JScrollPane shopperScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("SHOPPERS"), MTGConstants.ICON_TAB_SHOP,
				shopperScrollPane, null);

		shopperTreeTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGShopper>(true, MTGControler.getInstance().getShoppersProviders()));
		shopperTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) shopperTreeTable.getTreeTableModel())
						.setSelectedNode((MTGShopper) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		shopperScrollPane.setViewportView(shopperTreeTable);

		JScrollPane exportsScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("CARDS_IMPORT_EXPORT"),MTGConstants.ICON_TAB_IMPORT_EXPORT, exportsScrollPane, null);
		exportsTable = new JXTreeTable(new ProviderTreeTableModel<MTGCardsExport>(true,
				MTGControler.getInstance().getImportExportProviders()));
		exportsTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) exportsTable.getTreeTableModel())
						.setSelectedNode((MTGCardsExport) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		exportsScrollPane.setViewportView(exportsTable);

		JScrollPane importScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("DECKS_IMPORTER"), MTGConstants.ICON_TAB_DECK,
				importScrollPane, null);

		importTreeTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGDeckSniffer>(true, MTGControler.getInstance().getDeckSnifferProviders()));
		importScrollPane.setViewportView(importTreeTable);
		importTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) importTreeTable.getTreeTableModel())
						.setSelectedNode((MTGDeckSniffer) e.getNewLeadSelectionPath().getPathComponent(1));
		});

		JScrollPane dashboardScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("DASHBOARD_MODULE"), MTGConstants.ICON_TAB_VARIATIONS,
				dashboardScrollPane, null);

		dashboardTreeTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGDashBoard>(false, MTGControler.getInstance().getDashboardsProviders()));
		dashboardTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) dashboardTreeTable.getTreeTableModel())
						.setSelectedNode((MTGDashBoard) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		dashboardScrollPane.setViewportView(dashboardTreeTable);

		JScrollPane serversScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("SERVERS"), MTGConstants.ICON_TAB_SERVER,
				serversScrollPane, null);
		serversTreeTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGServer>(true, MTGControler.getInstance().getServers()));
		serversTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) serversTreeTable.getTreeTableModel())
						.setSelectedNode((MTGServer) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		serversScrollPane.setViewportView(serversTreeTable);

		JScrollPane notificationScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("NOTIFICATION"), MTGConstants.ICON_TAB_NOTIFICATION,notificationScrollPane, null);
		notificationsTreeTable = new JXTreeTable(new ProviderTreeTableModel<MTGNotifier>(true,
				MTGControler.getInstance().getNotifierProviders()));
		notificationsTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) notificationsTreeTable.getTreeTableModel())
						.setSelectedNode((MTGNotifier) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		notificationScrollPane.setViewportView(notificationsTreeTable);
		
		
		JScrollPane cachesScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("CACHES"), MTGConstants.ICON_TAB_CACHE,
				cachesScrollPane, null);
		cachesTreeTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGPicturesCache>(false, MTGControler.getInstance().getCachesProviders()));
		cachesTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) cachesTreeTable.getTreeTableModel())
						.setSelectedNode((MTGPicturesCache) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		cachesScrollPane.setViewportView(cachesTreeTable);

		JScrollPane newsScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("RSS_MODULE"), MTGConstants.ICON_TAB_NEWS,
				newsScrollPane, null);
		newsTreeTable = new JXTreeTable(
				new ProviderTreeTableModel<MTGNewsProvider>(true, MTGControler.getInstance().getNewsProviders()));
		newsTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) newsTreeTable.getTreeTableModel())
						.setSelectedNode((MTGNewsProvider) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		newsScrollPane.setViewportView(newsTreeTable);

		JScrollPane wallpaperScrollPane = new JScrollPane();
		subTabbedProviders.addTab(MTGControler.getInstance().getLangService().getCapitalize("WALLPAPER"), MTGConstants.ICON_TAB_WALLPAPER,
				wallpaperScrollPane, null);
		wallpapersTreeTable = new JXTreeTable(new ProviderTreeTableModel<MTGWallpaperProvider>(true,
				MTGControler.getInstance().getWallpaperProviders()));
		wallpapersTreeTable.addTreeSelectionListener(e -> {
			if (e.getNewLeadSelectionPath() != null && e.getNewLeadSelectionPath().getPathCount() > 1)
				((ProviderTreeTableModel) wallpapersTreeTable.getTreeTableModel())
						.setSelectedNode((MTGWallpaperProvider) e.getNewLeadSelectionPath().getPathComponent(1));
		});
		wallpaperScrollPane.setViewportView(wallpapersTreeTable);
		
		
		
		

		ConfigurationPanel configurationPanel = new ConfigurationPanel();
		tabbedPane.addTab(MTGControler.getInstance().getLangService().getCapitalize("CONFIGURATION"), MTGConstants.ICON_TAB_ADMIN,
				configurationPanel, null);

		ServersGUI serversGUI = new ServersGUI();
		tabbedPane.addTab(MTGControler.getInstance().getLangService().getCapitalize("ACTIVE_SERVERS"), MTGConstants.ICON_TAB_ACTIVESERVER, serversGUI,
				null);

		loggerViewPanel = new LoggerViewPanel();
		tabbedPane.addTab(MTGControler.getInstance().getLangService().getCapitalize("LOGS"), MTGConstants.ICON_TAB_RULES, loggerViewPanel,
				null);

	}

}
