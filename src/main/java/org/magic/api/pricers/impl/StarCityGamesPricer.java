package org.magic.api.pricers.impl;

import java.io.IOException;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.magic.api.beans.MagicCard;
import org.magic.api.beans.MagicEdition;
import org.magic.api.beans.MagicPrice;
import org.magic.api.interfaces.MTGCardsProvider.STATUT;
import org.magic.api.interfaces.abstracts.AbstractMagicPricesProvider;
import org.magic.services.MTGConstants;

public class StarCityGamesPricer extends AbstractMagicPricesProvider {

	
	NumberFormat format = NumberFormat.getCurrencyInstance();
	
	
	@Override
	public List<MagicPrice> getPrice(MagicEdition me, MagicCard card) throws IOException {
		String cardName = URLEncoder.encode(card.getName(), MTGConstants.DEFAULT_ENCODING);
		
		logger.debug(getName() + " looking for price " + getString("URL")+cardName);
		
		Document d = Jsoup.connect(getString("URL")+cardName).userAgent(MTGConstants.USER_AGENT).get();
		List<MagicPrice> ret = new ArrayList<>();
		Elements trs = d.getElementById("search_results_table").select("tr");
		trs.remove(0);//remove empty tr
		trs.remove(0);//remove header
		for(Element tr : trs)
		{
			
			if(!tr.select("td").get(7).text().equalsIgnoreCase("Out of Stock"))
			{
				MagicPrice mp = new MagicPrice();
				mp.setSite(getName());
				mp.setCountry("USA");
				mp.setCurrency("USD");
				mp.setUrl(tr.select("td").get(0).select("a").attr("href"));
				mp.setSeller(tr.select("td").get(1).text());
				mp.setQuality(tr.select("td").get(6).text());
				mp.setValue(Double.parseDouble(tr.select("td").get(8).text().replace('$', ' ').trim()));
				ret.add(mp);
			}
		}
		logger.debug(getName() + " found " + ret.size() + " items");
		
		
		return ret;
		
	}

	@Override
	public void alertDetected(List<MagicPrice> okz) {
		//do nothing

	}

	@Override
	public String getName() {
		return "StarCityGames";
	}

	@Override
	public STATUT getStatut() {
		return STATUT.DEV;
	}

	@Override
	public void initDefault() {
		setProperty("URL", "http://www.starcitygames.com/results?name=");

	}

	@Override
	public String getVersion() {
		return "0.5";
	}

}
