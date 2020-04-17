package org.sadoke.main;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.sadoke.util.general.VoiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * This is the starter for the whole software and initializes the whole software
 * which means that the Datasource, EvaluationEngine, ExpressionRunner and
 * Listener are created and started
 * 
 * @author Saied Sadegh
 *
 */

class Launcher {
	private static Logger log = LoggerFactory.getLogger(Launcher.class);

	public static void main(String[] args) throws IOException {
		Launcher.init();

	}

	private static void init() throws IOException {

		final Config config = Launcher.loadConfig();
		SadokeInterface sadoke;
		if (!config.isServer())
			config.setUrl("localhost");
		sadoke = new ServerSadoke(config);
		final Listener listener = new Listener(sadoke);
		try {
			VoiceUtil.say("Welcome");
		} catch (final Exception e) {
			Launcher.log.error(e.getLocalizedMessage());
		}
		listener.start();
	}

	private static Config loadConfig() {
		try (InputStream input = Launcher.class.getClassLoader()
				.getResourceAsStream("application.properties")) {
			final Properties prop = new Properties();
			// load a properties file
			prop.load(input);
			Launcher.log.info(prop.getProperty("server.usage"));
			return new Config(prop.getProperty("server.usage", "false"),
					prop.getProperty("server.port"),
					prop.getProperty("server.url"));
		} catch (final IOException ex) {
			Launcher.log.error("Config could not be initialized");
		}
		return null;
	}

}
