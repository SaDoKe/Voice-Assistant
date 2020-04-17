package org.sadoke.expression;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.ConfigRetrivalUtil;
import org.sadoke.util.general.VoiceUtil;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class News implements Expression {

	private static final String URL = "https://newsapi.org/v2/top-headlines";
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	@Override
	public void run(Object[] args) {
		final String token = ConfigRetrivalUtil.getTokenByName("news");
		final Map<String, Object> params = new HashMap<>();
		params.put("country", "us");
		params.put("apiKey", token);
		try {

			final String json = RestUtil.callGetRestService(News.URL, null,
					params);
			log.info(json);
			final String results = JsonUtil.getTagContent(json, "totalResults");
			final int resultNumb = Integer.parseInt(results);

			VoiceUtil.say(JsonUtil.getTagContent(json,
					new Random().nextInt(resultNumb), "articles",
					"description"));

		} catch (IOException | URISyntaxException e) {

		}
	}

}
