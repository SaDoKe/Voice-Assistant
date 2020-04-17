package org.sadoke.expression;

import org.sadoke.util.annotations.Express;
import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.VoiceUtil;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Express(express = {"Tell me a joke"}, type = TellAJoke.class)
public class TellAJoke implements Expression {

	Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void run(Object[] args) {

		String joke = RestUtil
				.callGetRestService("https://api.chucknorris.io/jokes/random");
		joke = JsonUtil.getTagContent(joke, "value");
		VoiceUtil.say(joke);
		log.info(joke);

	}

}
