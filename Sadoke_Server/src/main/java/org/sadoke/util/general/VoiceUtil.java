package org.sadoke.util.general;

import org.sadoke.main.Datasource;
/**
 * EN: //TODO: description in english DE: Ist die Schnittstelle um Text in
 * MarryTTS(TextToSpeech) auszugeben.
 * 
 * @author kvn
 *
 */
public class VoiceUtil {

	public static final void say(String spoken) {
		Datasource.speech.add(spoken);

	}

	public static final void sayWith(String spoken, String voice) {
		Datasource.speech.add(spoken);
	}
	/**
	 * EN: //TODO: description in english DE: Funktion bei der man entscheiden
	 * kann welcher Text und welche Stimme gesprochen wird und ob es als demeaon
	 * gestartet werden soll
	 * 
	 * @param spoken
	 *            String that being spoken
	 * @param voice
	 *            the chosen Voice
	 * @param daemon
	 *            True: The thread that will start the text to speech Player
	 *            will be a daemon Thread False: The thread that will start the
	 *            text to speech Player will be a normal non daemon Thread
	 */
	public static final void sayControled(String spoken, String voice,
			boolean daemon) {
		Datasource.speech.add(spoken);
	}

	/**
	 * EN: //TODO: description in english DE: //TODO: Funktion die das
	 * Gesprochene mit allen fasseten einstellbar ausgibt
	 * 
	 * @param spoken
	 *            String that being spoken
	 * @param voice
	 *            the chosen Voice
	 * @param daemon
	 *            True: The thread that will start the text to speech Player
	 *            will be a daemon Thread False: The thread that will start the
	 *            text to speech Player will be a normal non daemon Thread
	 * @param join
	 *            True: The current Thread calling this method will
	 *            wait(blocked) until the Thread which is playing the Speech
	 *            finish False: The current Thread calling this method will
	 *            continue freely after calling this method
	 */
	public static final void sayFullControled(String spoken, String voice,
			boolean daemon, boolean join) {
		Datasource.speech.add(spoken);
	}
	/**
	 * EN: Returns voices in console DE: Gibt die Stimmen in der Console aus
	 */
}