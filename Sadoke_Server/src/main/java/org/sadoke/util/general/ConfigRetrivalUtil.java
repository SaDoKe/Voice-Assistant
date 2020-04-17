package org.sadoke.util.general;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigRetrivalUtil {

	private static final String TOKEN_MARK = "token.";

	private static final String URL_MARK = "url.";
	private ConfigRetrivalUtil() {
	}
	public static final String TOKEN_NOT_FOUND_VALUE = "Not found";
	private static final Logger log = LoggerFactory
			.getLogger(ConfigRetrivalUtil.class);
	/**
	 * Fetches a token from the config by its name.
	 * 
	 * 
	 * @param name
	 *            , name of the token which you want to fetch from the
	 *            configuration
	 * @return token content or a "Not found"
	 */
	public static String getTokenByName(String name) {
		final Properties props = new Properties();
		try {
			props.load(
					new FileInputStream(ConfigRetrivalUtil.getPropertyPath()));
			String token = props.getProperty(name, "");
			if (token.isEmpty())
				token = props.getProperty(
						ConfigRetrivalUtil.TOKEN_MARK.concat(name), "");

			if (token.isEmpty())
				token = props.getProperty(
						ConfigRetrivalUtil.TOKEN_MARK.concat(name), "");
			if (token.isEmpty())
				token = props.getProperty("token.custom.".concat(name),
						ConfigRetrivalUtil.TOKEN_NOT_FOUND_VALUE);
			return token;
		} catch (final IOException e) {
			ConfigRetrivalUtil.log.error(e.getMessage());
		}
		return ConfigRetrivalUtil.TOKEN_NOT_FOUND_VALUE;
	}

	/**
	 * Fetches a url from the config by its name.
	 * 
	 * 
	 * @param name
	 *            , name of the URL which you want to fetch from the
	 *            configuration
	 * @return URL content or a "Not found"
	 */

	public static String getUrlByName(String name) {
		final Properties props = new Properties();
		try {
			props.load(
					new FileInputStream(ConfigRetrivalUtil.getPropertyPath()));
			String token = props.getProperty(name, "");
			if (token.isEmpty())
				token = props.getProperty(
						ConfigRetrivalUtil.URL_MARK.concat(name), "");

			if (token.isEmpty())
				token = props.getProperty(
						ConfigRetrivalUtil.URL_MARK.concat(name),
						ConfigRetrivalUtil.TOKEN_NOT_FOUND_VALUE);

			return token;
		} catch (final IOException e) {
			ConfigRetrivalUtil.log.error(e.getMessage());
		}
		return ConfigRetrivalUtil.TOKEN_NOT_FOUND_VALUE;
	}

	private static String getPropertyPath() {

		return System.getProperty("config");
	}
}
