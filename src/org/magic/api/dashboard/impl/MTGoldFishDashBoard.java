package org.magic.api.dashboard.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.magic.api.beans.CardShake;
import org.magic.api.beans.MagicEdition;
import org.magic.api.interfaces.abstracts.AbstractDashBoard;

public class MTGoldFishDashBoard extends AbstractDashBoard{

	static final Logger logger = LogManager.getLogger(MTGoldFishDashBoard.class.getName());

	private Date updateTime;
	
	public MTGoldFishDashBoard() 
	{
		super();

		if(!new File(confdir, getName()+".conf").exists()){
		props.put("URL", "http://www.mtggoldfish.com/movers-details/");
		props.put("WEBSITE", "http://www.mtggoldfish.com/");
		props.put("USER_AGENT", "Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6");
		props.put("FORMAT", "paper");
		props.put("TIMEOUT", "0");
		save();
		}
	}
	

	public List<CardShake> getShakerFor(String gameFormat,String weekordaly) throws IOException
	{
		Document doc = Jsoup.connect(props.getProperty("URL")+props.getProperty("FORMAT")+"/"+gameFormat.toString()+"/winners/"+weekordaly)
							.userAgent(props.getProperty("USER_AGENT"))
							.timeout(Integer.parseInt(props.get("TIMEOUT").toString()))
							.get();
		
		Document doc2 = Jsoup.connect(props.getProperty("URL")+props.getProperty("FORMAT")+"/"+gameFormat+"/losers/"+weekordaly)
				.userAgent(props.getProperty("USER_AGENT"))
				.timeout(Integer.parseInt(props.get("TIMEOUT").toString()))
				.get();
		
		
		try {
			System.out.println(doc.getElementsByClass("timeago").get(0).attr("title"));
			updateTime= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").parse(doc.getElementsByClass("timeago").get(0).attr("title"));
		
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		logger.debug("Parsing dashboard "+getName()+" http://www.mtggoldfish.com/movers-details/"+props.getProperty("FORMAT")+"/"+gameFormat+"/losers/"+weekordaly);
		
		Element table =null;
		try{
		
		table = doc.select("table").get(0).getElementsByTag("tbody").get(0).appendChild(doc2.select("table").get(0).getElementsByTag("tbody").get(0));//combine 2 results
		
		List<CardShake> list = new ArrayList<CardShake>();
		
		
		for(Element e : table.getElementsByTag("tr"))
		{
			
			CardShake cs = new CardShake();
			cs.setName(e.getElementsByTag("TD").get(3).text().replaceAll("\\(RL\\)", "").trim());
			cs.setImg(new URL(e.getElementsByTag("TD").get(3).getElementsByTag("a").get(0).attr("data-full-image")));
			cs.setPrice(parseDouble(e.getElementsByTag("TD").get(4).text()));
			cs.setPriceDayChange(parseDouble(e.getElementsByTag("TD").get(1).text()));
			cs.setPercentDChange(parseDouble(e.getElementsByTag("TD").get(5).text()));
			cs.setEd(e.getElementsByTag("TD").get(2).getElementsByTag("img").get(0).attr("alt"));
			
			list.add(cs);
			
		}
		return list;
		
		
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.error(e);
		}
		return null;
		
		
		
	}
	

	public List<CardShake> getShakeForEdition(MagicEdition edition) throws IOException
	{
		String urlEditionChecker = "http://www.mtggoldfish.com/index/"+edition.getSet()+"#"+props.getProperty("FORMAT");
		
		Document doc = Jsoup.connect(urlEditionChecker)
							.userAgent(props.getProperty("USER_AGENT"))
							.timeout(Integer.parseInt(props.get("TIMEOUT").toString()))
							.get();
		
		Element table =null;
		try{
			List<CardShake> list = new ArrayList<CardShake>();
			
		table = doc.select("table").get(1).getElementsByTag("tbody").get(0);
		
		for(Element e : table.getElementsByTag("tr"))
		{
			CardShake cs = new CardShake();
				
				cs.setName(e.getElementsByTag("TD").get(0).text().replaceAll("\\(RL\\)", "").trim());
				cs.setImg(new URL(e.getElementsByTag("TD").get(0).getElementsByTag("a").get(0).attr("data-full-image")));
				cs.setRarity(e.getElementsByTag("TD").get(2).text());
				cs.setPrice(parseDouble(e.getElementsByTag("TD").get(3).text()));
				cs.setPriceDayChange(parseDouble(e.getElementsByTag("TD").get(4).text()));
				cs.setPercentDChange(parseDouble(e.getElementsByTag("TD").get(5).text()));
				cs.setPriceWeekChange(parseDouble(e.getElementsByTag("TD").get(6).text()));
				cs.setPercentWeekChange(parseDouble(e.getElementsByTag("TD").get(7).text()));
				cs.setEd(e.getElementsByTag("TD").get(1).text());
			
			list.add(cs);
		}
		return list;
		
		
		}
		catch(IndexOutOfBoundsException e)
		{
			logger.error(e);
		}
		return null;
		
		
	}
	
	private double parseDouble(String number)
	{
		return Double.parseDouble(number.replaceAll(",","").replaceAll("%", ""));
	}
		
	@Override
	public String getName() {
		return "MTGoldFish";
	}

	@Override
	public Date getUpdatedDate() {
		return updateTime;
	}


	
}