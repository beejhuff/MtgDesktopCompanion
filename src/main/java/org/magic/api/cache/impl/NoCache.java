package org.magic.api.cache.impl;

import java.awt.image.BufferedImage;

import org.magic.api.beans.MagicCard;
import org.magic.api.beans.MagicEdition;
import org.magic.api.interfaces.abstracts.AbstractMTGPicturesCache;

public class NoCache extends AbstractMTGPicturesCache {

	public NoCache() {
		super();
	}

	

	@Override
	public String getName() {
		return "No Cache";
	}

	@Override
	public BufferedImage getPic(MagicCard mc, MagicEdition ed) {
		return null;
	}

	@Override
	public void put(BufferedImage im, MagicCard mc, MagicEdition ed) {
		// Nothing to do

	}

	@Override
	public void clear() {
		// nothing to do

	}

	@Override
	public String getVersion() {
		return "1";
	}

}
