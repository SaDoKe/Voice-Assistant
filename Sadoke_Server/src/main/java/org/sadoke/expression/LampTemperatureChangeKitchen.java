package org.sadoke.expression;

import java.io.IOException;

import org.sadoke.util.general.Util;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Change the Temperature of the Lamp thats integrated in OpenHAB
 * 
 * @author Kevin Kupsch
 *
 */

public class LampTemperatureChangeKitchen implements Expression {
	private static final String URL = "http://192.168.137.108:8080/rest/items/Sadoke_Color";
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run(Object[] args) {

		try {
			final String temp = Util.trycast(args[0], String.class);
			RestUtil.callPostRestServiceWithPlainContent(
					LampTemperatureChangeKitchen.URL, temp);
		} catch (final IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
}
