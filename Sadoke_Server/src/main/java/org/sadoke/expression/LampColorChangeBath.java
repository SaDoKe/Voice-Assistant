package org.sadoke.expression;

import java.io.IOException;

import org.sadoke.util.general.ConfigRetrivalUtil;
import org.sadoke.util.general.Util;
import org.sadoke.util.rest.RestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LampColorChangeBath implements Expression {
	private static final String URL = ConfigRetrivalUtil.getUrlByName("openhab")
			.concat("Sadoke_Color");
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void run(Object[] args) {

		try {
			final String state = RestUtil
					.callGetRestService(LampColorChangeBath.URL + "/state");
			final String[] stateParams = state.split(",");
			switch (Util.trycast(args[0], String.class)) {
				case "green" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, "100", "100",
							stateParams[2]);
					break;
				case "blue" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, "240", "100",
							stateParams[2]);
					break;

				case "red" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, "0", "100",
							stateParams[2]);
					break;
				case "lightblue" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, "166", "100",
							stateParams[2]);
					break;
				case "purple" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, "274", "100",
							stateParams[2]);
					break;
				case "off" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, stateParams[0],
							stateParams[1], "0");
					break;
				case "on" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, stateParams[0],
							stateParams[1], "100");
					break;
				case "white" :
					RestUtil.callPostRestServiceWithPlainContent(
							LampColorChangeBath.URL, stateParams[0], "0",
							stateParams[2]);
					break;

			}
		} catch (final IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}
}
