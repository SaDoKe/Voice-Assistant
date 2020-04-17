package org.sadoke.expression;

import java.io.IOException;

import org.sadoke.util.general.ConfigRetrivalUtil;
import org.sadoke.util.general.Util;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Change the Color of the Lamp thats integrated in OpenHAB
 * 
 * @author Kevin Kupsch
 *
 */

public class LampBrightnessChangeBath implements Expression {
	private static final String URL = ConfigRetrivalUtil.getUrlByName("openhab")
			.concat("Sadoke_Color");
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run(Object[] args) {

		try {
			final String state = RestUtil.callGetRestService(
					LampBrightnessChangeBath.URL + "/state");
			final String[] stateParams = state.split(",");
			final String bright = Util.trycast(args[0], String.class);
			RestUtil.callPostRestServiceWithPlainContent(
					LampBrightnessChangeBath.URL, stateParams[0],
					stateParams[1], bright);
		} catch (final IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
}
