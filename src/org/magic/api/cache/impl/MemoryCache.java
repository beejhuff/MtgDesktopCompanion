package org.magic.api.cache.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.magic.api.beans.MagicCard;
import org.magic.api.beans.MagicEdition;
import org.magic.api.interfaces.abstracts.AbstractMTGPicturesCache;

public class MemoryCache extends AbstractMTGPicturesCache {

	
	Map<String,BufferedImage> cache;
	
	
	public MemoryCache() {
		super();
		if(!new File(confdir, getName()+".conf").exists()){
		
		save();
		}
		cache=new HashMap<String,BufferedImage>();
	}
	
	
	@Override
	public String getName() {
		return "Memory Cache";
	}

	@Override
	public BufferedImage getPic(MagicCard mc,MagicEdition ed) {
		if(ed!=null)
			return cache.get(ed.getMultiverse_id());
		else
			return cache.get(mc.getEditions().get(0).getMultiverse_id());
	}

	@Override
	public void put(BufferedImage im, MagicCard mc,MagicEdition ed) throws Exception{
		logger.debug("put " + mc + " in cache");
		if(ed==null)
			ed=mc.getEditions().get(0);
		
		cache.put(ed.getMultiverse_id(), im);
			
	
		
	}

}
