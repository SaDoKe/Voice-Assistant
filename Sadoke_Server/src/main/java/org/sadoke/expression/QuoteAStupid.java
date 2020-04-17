package org.sadoke.expression;

import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.VoiceUtil;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuoteAStupid implements Expression {

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void run(Object[] args) {
		final String url = "https://api.tronalddump.io/random/quote";
		try {
			final String json = RestUtil.callGetRestService(url);
			log.debug(json);
			VoiceUtil.say(JsonUtil.getTagContent(json, "value"));
		} catch (final Exception e) {
		}

	}
}
