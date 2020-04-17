package org.sadoke.expression;

import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.VoiceUtil;
import org.sadoke.util.rest.RestUtil;

public class RandomFacts implements Expression {

	/**
	 * Example answer of the api: {"id":"de56113d-e1da-4be8-a438-8553a316f6a4",
	 * "text":"Women blink nearly twice as much as men.", "source":"djtech.net",
	 * "source_url":"http:\/\/www.djtech.net\/humor\/useless_facts.htm",
	 * "language":"en",
	 * "permalink":"https:\/\/uselessfacts.jsph.pl\/de56113d-e1da-4be8-a438-8553a316f6a4"}
	 * 
	 */
	private static final String URL = "https://uselessfacts.jsph.pl/random.json?language=en";
	@Override
	public void run(Object[] args) {
		final String json = RestUtil.callGetRestService(RandomFacts.URL);
		VoiceUtil.say(JsonUtil.getTagContent(json, "text"));
	}

}
