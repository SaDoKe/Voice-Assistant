package org.sadoke.expression;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.ConfigRetrivalUtil;
import org.sadoke.util.general.VoiceUtil;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * Uses open weather map to get the current temperature and description.
 * 
 * @author Saied Sadegh
 *
 */
public class Weather implements Expression {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private static final String KEY = ConfigRetrivalUtil
			.getTokenByName("weather");
	@Override
	public void run(Object[] args) {
		try {

			final String url = "https://api.openweathermap.org/data/2.5/weather";
			final Map<String, Object> params = new HashMap<>();
			params.put("q", args[0].toString());
			params.put("units", "metric");
			params.put("appid", Weather.KEY);
			final String json = RestUtil.callGetRestService(url, null, params);
			final String temp = JsonUtil.getTagContent(json, "main", "temp");
			final String desc = JsonUtil.getTagContent(json, "weather",
					"description");
			log.info("The temperature equals {} °Celsius and there is {}.",
					temp, desc);
			VoiceUtil.say("The temperature equals+ " + temp
					+ " ° Celsius and there is " + desc + ".");

		} catch (final IOException | URISyntaxException e) {
			log.info(e.getMessage());
		}
	}

}
