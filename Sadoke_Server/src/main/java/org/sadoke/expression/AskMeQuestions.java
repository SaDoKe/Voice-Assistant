package org.sadoke.expression;

import org.apache.commons.text.StringEscapeUtils;
import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.VoiceUtil;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.LoggerFactory;

public class AskMeQuestions implements Expression {
	private final String url = "https://opentdb.com/api.php?amount=1";

	@Override
	public void run(Object[] args) {
		final String json = RestUtil.callGetRestService(url);
		LoggerFactory.getLogger(getClass()).info(json);
		VoiceUtil.say(StringEscapeUtils.unescapeHtml4(
				JsonUtil.getTagContent(json, "results", "question")));
		try {
			Thread.sleep(5000);
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		VoiceUtil
				.say(JsonUtil.getTagContent(json, "results", "correct_answer"));

	}

}
