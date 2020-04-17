package org.sadoke.main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.sadoke.util.general.JsonUtil;
import org.sadoke.util.general.VoiceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.eclipsesource.json.Json;

//Imports
import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;

/**
 * Is initialized by the Launcher and started. Listens constantly to the the
 * outside and sends input towards the Evaluation Engine.
 *
 * @author Saied Sadegh
 *
 */
class Listener extends Thread {
	private static Logger log = LoggerFactory.getLogger(Listener.class);

	private final Configuration configuration;
	private final LiveSpeechRecognizer recognize;
	private final SadokeInterface sadoke;
	private boolean listen;
	Listener(SadokeInterface sadoke) throws IOException {
		this.sadoke = sadoke;
		configuration = new Configuration();
		configuration.setAcousticModelPath(
				"resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath(Thread.currentThread()
				.getContextClassLoader().getResource("main.dic").toString());
		configuration.setLanguageModelPath(Thread.currentThread()
				.getContextClassLoader().getResource("main.lm").toString());
		recognize = new LiveSpeechRecognizer(configuration);
		listen = true;
	}

	/**
	 * Starts the CMU recognition and delivers resutls to the evaluationEngine.
	 */
	@Override
	public void run() {
		recognize.startRecognition(true);
		SpeechResult result;
		while (true)
			while (listen)
				while ((result = recognize.getResult()) != null) {
					final List<String> command = new ArrayList<>(
							result.getNbest(5));
					try {
						final String answer = sadoke.recieveHypothesis(command);
						if (answer != null && !answer.isEmpty()) {
							Listener.log.info(answer);
							VoiceUtil.say(Json.parse(JsonUtil.getTagContent(answer, "speech"))
									.asArray().get(0).asString());
						}
					} catch (final Exception e) {
						Listener.log.debug(e.getMessage());
					}
				}
	}

	public boolean isListen() {
		return listen;
	}
	public void setListen(boolean listen) {
		this.listen = listen;
	}

}