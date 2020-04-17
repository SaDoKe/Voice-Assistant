package org.sadoke.expression;

import org.sadoke.util.general.DaoUtil;
import org.sadoke.util.general.Util;
import org.sadoke.util.general.VoiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Renames the Sadeko machine so it reacts on a different name.
 * @author Saied Sadegh
 *
 */
public class Rename implements Expression{

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void run(Object[] args) {
		
		String newName = Util.trycast(args[0],String.class);	
		log.info("name changed to {}",newName);
		VoiceUtil.say("name changed to "+newName);
		DaoUtil.changeName(newName);
	}

}
